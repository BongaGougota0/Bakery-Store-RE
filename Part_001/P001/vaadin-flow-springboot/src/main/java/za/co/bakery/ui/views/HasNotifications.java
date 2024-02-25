package za.co.bakery.ui.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;

public interface HasNotifications {

    default void showNotification(String message){showNotification(message, false);}

    default void showNotification(String message, boolean isPersistent){
        if(isPersistent){
            Button close = new Button("Close");
            //**missing set element
            Notification notification = new Notification(new Text(message), close);
            //**missing set position
            notification.setDuration(0);
            close.addClickListener(event -> notification.close());
            notification.open();
        }
        else {
            Notification.show(message);
        }
    }

    //Notification getNotification();
}
