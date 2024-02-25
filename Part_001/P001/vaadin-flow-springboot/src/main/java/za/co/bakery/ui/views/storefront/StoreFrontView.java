package za.co.bakery.ui.views.storefront;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import jakarta.annotation.security.PermitAll;
import za.co.bakery.app.HasLogger;
import za.co.bakery.backend.data.entity.Order;
import za.co.bakery.ui.components.SearchBar;
import za.co.bakery.ui.views.EntityView;
import za.co.bakery.ui.views.orderedit.OrderDetails;
import za.co.bakery.ui.views.orderedit.OrderEditor;

@Tag("storefront-view")
@PageTitle("Bakery Store View")
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
