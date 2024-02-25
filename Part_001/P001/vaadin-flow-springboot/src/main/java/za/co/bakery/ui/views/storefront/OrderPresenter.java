package za.co.bakery.ui.views.storefront;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import za.co.bakery.app.security.CurrentUser;
import za.co.bakery.backend.data.entity.Order;
import za.co.bakery.ui.crud.EntityPresenter;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class OrderPresenter {
//    private final OrderCardHeaderGenerator headerGenerator;
    //Data packages
    private EntityPresenter<Order, StoreFrontView> entityPresenter;
//    private final OrdersGridDataProvider dataProvider;
    private StoreFrontView view;
    private final CurrentUser currentUser;


    public OrderPresenter(CurrentUser user){
        this.currentUser = user;
    }

    public void cancel() {
    }

    public Object getHeaderByOrderById(Long id) {
        return null;
    }

    public void filterChanged(boolean checkboxChecked) {
    }

    public void createNewOrder() {
    }

    public void init(StoreFrontView view, EntityPresenter<Order,StoreFrontView> entityPresenter) {
        this.view = view;
        this.entityPresenter = entityPresenter;
    }
}
