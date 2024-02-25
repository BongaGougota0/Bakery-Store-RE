package za.co.bakery.ui.views.storefront;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;
import za.co.bakery.app.HasLogger;
import za.co.bakery.backend.data.entity.Order;
import za.co.bakery.ui.MainView;
import za.co.bakery.ui.components.SearchBar;
import za.co.bakery.ui.utils.BakeryConstants;
import za.co.bakery.ui.views.EntityView;
import za.co.bakery.ui.views.orderedit.OrderDetails;
import za.co.bakery.ui.views.orderedit.OrderEditor;

import javax.swing.*;

@Tag("storefront-view")
@PageTitle("Bakery Store View")
@JsModule("./src/views/storefront/storefront-view.js")
@Route(value = BakeryConstants.PAGE_STOREFRONT_ORDER_TEMPLATE, layout = MainView.class)
@RouteAlias(value = BakeryConstants.PAGE_STOREFRONT_ORDER_EDIT_TEMPLATE, layout = MainView.class)
@RouteAlias(value = BakeryConstants.PAGE_ROOT, layout = MainView.class)
@PermitAll
public class StoreFrontView extends LitTemplate implements BeforeEnterObserver, HasLogger, EntityView<Order> {

    @Id("search")
    private SearchBar searchBar;
    @Id("dialog")
    private Dialog dialog;
    private ConfirmDialog confirmDialog;
    @Id("grid")
    private Grid<Order> grid;

    private final OrderEditor orderEditor;
    private final OrderDetails orderDetails = new OrderDetails();
    private final OrderPresenter orderPresenter;

    public StoreFrontView(OrderPresenter orderPresenter, OrderEditor orderEditor){
        this.orderPresenter = orderPresenter;
        this.orderEditor = orderEditor;

        searchBar.setActionText("New Order");
        searchBar.setCheckBoxText("Show Past Orders");
        searchBar.setPlaceHolder("Search");

        grid.setSelectionMode(Grid.SelectionMode.NONE);

        grid.addColumn(OrderCard.getTemplate()
                .withProperty("orderCard", OrderCard::create)
                .withProperty("header",
                        order -> orderPresenter.getHeaderByOrderById(order.getId()))
                .withFunction("cardClick",
                        order -> UI.getCurrent().navigate(
                                BakeryConstants.PAGE_STORE_FRONT+"/"+order.getId())));

        getSearchBar().addFilterChangedListener(e -> orderPresenter.filterChanged(getSearchBar().isCheckboxChecked()));

        getSearchBar().addActionClickListener(e -> orderPresenter.createNewOrder());
//        orderPresenter.init(this);

        dialog.addDialogCloseActionListener(e -> orderPresenter.cancel());
    }

    private SearchBar getSearchBar() {
        return searchBar;
    }

    //two missing classess to implement EntityView<T extends AbstractEntity>
    //two missing classes to implement HasLogger --Functional Interface--
    //Now this is done.
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

    }

    @Override
    public void setConfirmation(ConfirmDialog confirmation) {

    }

    @Override
    public ConfirmDialog getConfirmDialog() {
        return null;
    }

    @Override
    public boolean isDirty() {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public void write(Order entity) throws ValidationException {

    }

    @Override
    public String getEntityName() {
        return null;
    }
}
