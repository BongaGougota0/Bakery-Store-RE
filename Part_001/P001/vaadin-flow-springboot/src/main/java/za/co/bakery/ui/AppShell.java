package za.co.bakery.ui;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.server.PWA;

//@Viewport()
@PWA(name ="RE Baker Store", shortName = "RE Baker",
        startPath = "login", offlineResources = {"images/download.jfif"})
public class AppShell implements AppShellConfigurator {
}
