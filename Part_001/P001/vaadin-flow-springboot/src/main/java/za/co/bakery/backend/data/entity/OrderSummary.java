package za.co.bakery.backend.data.entity;

import za.co.bakery.backend.data.OrderState;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface OrderSummary {
    Long getId();
    OrderState getState();
    Customer getCustomer();
    List<OrderItem> getOrderItems();
    LocalDate getDueDate();
    LocalTime getDueTime();
    PickUpLocation getLocation();
    Integer getTotalPrice();
}
