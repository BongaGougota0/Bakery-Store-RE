package za.co.bakery.backend.data.entity;

import jakarta.persistence.Entity;
import za.co.bakery.backend.data.OrderState;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
public class Order extends AbstractDataEntity implements OrderSummary{

    @NotNull
    private LocalTime dueTime;
    private LocalDate dueDate;
    private Customer customer;
    private PickUpLocation pickUpLocation;
    private List<OrderItem> itemList;
    private OrderState orderState;
    private List<HistoryItem> historyItems;

    public Order() {
        //Spring Data jpa blank constructor
    }

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public OrderState getState() {
        return null;
    }

    @Override
    public Customer getCustomer() {
        return null;
    }

    @Override
    public List<OrderItem> getOrderItems() {
        return null;
    }

    @Override
    public LocalDate getDueDate() {
        return null;
    }

    @Override
    public LocalTime getDueTime() {
        return null;
    }

    @Override
    public PickUpLocation getLocation() {
        return null;
    }

    @Override
    public Integer getTotalPrice() {
//        return itemList.stream().map(e -> (Integer) e.getTotalPrice()).reduce(0, Integer::sum);
        Integer totalPrice = null;
        for(OrderItem item : itemList){
            totalPrice += item.getTotalPrice();
        }
        return totalPrice;
    }
}
