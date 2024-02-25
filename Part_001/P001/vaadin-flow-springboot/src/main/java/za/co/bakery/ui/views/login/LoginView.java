package za.co.bakery.ui.views.login;

import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.*;
import za.co.bakery.app.security.SecurityUtils;
import za.co.bakery.ui.views.storefront.StoreFrontView;

@Route
@PageTitle("Bakery Store RE")
public class LoginView extends LoginOverlay implements BeforeEnterObserver, AfterNavigationObserver {


    public LoginView(){
        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("Bakery Store RE");
        i18n.getHeader().setDescription(
                "this demo description."
        );
        i18n.setAdditionalInformation(null);
        i18n.setForm(new LoginI18n.Form());
        i18n.getForm().setSubmit("Sign In");
        i18n.getForm().setTitle("Sign In");
        i18n.getForm().setUsername("Email");
        i18n.getForm().setPassword("Password");
        setI18n(i18n);
        setForgotPasswordButtonVisible(false);
        setAction("login");
    }


    //Override implemeted classes Methods.
    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
            setError(
                    afterNavigationEvent.getLocation().getQueryParameters().getParameters().containsKey("error")
            );
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(SecurityUtils.isUserLoggedIn()){
            beforeEnterEvent.forwardTo(StoreFrontView.class);
        }else {
            setOpened(true);
        }
    }
}
