package za.co.bakery.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.router.Route;
import za.co.bakery.ui.views.storefront.StoreFrontView;
import java.util.ArrayList;
import java.util.List;

public class MainView extends AppLayout {
    @Autowired
     private AccessAnnotationChecker accessAnnotationChecker;
    private final ConfirmDialog confirmDialog = new ConfirmDialog();
    private Tabs tabsMenu;
    private static final String LOGOUT_ACCESS_URL = "/" + "";

    @PostConstruct
    public void init(){
        confirmDialog.setCancelable(true);
        confirmDialog.setConfirmButtonTheme("raised tertiary error.");
        confirmDialog.setCancelButtonTheme("raised tertiary");

        this.setDrawerOpened(false);
        Span appName = new Span("Bakery Store RE");
        //Add class name

        tabsMenu = createMenuTabs();
    }

    private Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);
        tabs.add(getAvailableTabs());
        return tabs;
    }

    private Tab[] getAvailableTabs(){
        final List<Tab> tabList = new ArrayList<>(4);
        tabList.add(createTab(VaadinIcon.EDIT, "BAKERY_TITLE", StoreFrontView.class));
//        tabList.add(createTab(VaadinIcon.CLOCK, "Dashboard_TITLE",));
        return tabList.toArray(new Tab[tabList.size()]);
    }

    private static Tab createTab(VaadinIcon icon, String title, Class<? extends Component> viewClass){
        return createTab(populateLink(new RouterLink("", viewClass),icon,title));
    }

    private static Tab createTab(Component component){
        final Tab tab = new Tab();
        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tab.add(component);
        return tab;
    }

    private static <T extends HasComponents> T populateLink(T a,VaadinIcon icon, String title){
        a.add(icon.create());
        a.add(title);
        return a;
    }

}
