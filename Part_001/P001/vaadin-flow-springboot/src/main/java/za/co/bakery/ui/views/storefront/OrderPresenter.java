package za.co.bakery.ui.views.storefront;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import za.co.bakery.app.security.CurrentUser;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class OrderPresenter {
    private StoreFrontView storeFrontView;
    private final CurrentUser currentUser;

    public OrderPresenter(CurrentUser user){
        this.currentUser = user;
    }
}
