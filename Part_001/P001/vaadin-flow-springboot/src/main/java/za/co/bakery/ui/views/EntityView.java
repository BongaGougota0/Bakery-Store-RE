package za.co.bakery.ui.views;

import com.vaadin.flow.data.binder.ValidationException;

public interface EntityView<T> extends HasConfirmation, HasNotifications {
    default void showError(String message, boolean isPersistent){showNotification(message, isPersistent);}

    boolean isDirty();

    void clear();

    void write(T entity) throws ValidationException;

    String getEntityName();

    default void showCreatedNotification(){showNotification(getEntityName() + "was created.");}

    default void showUpdatedNotification(){showNotification(getEntityName() + "was updated:");}

    default void showDeleteNotification(){showNotification(getEntityName() + "was deleted.");}
}
