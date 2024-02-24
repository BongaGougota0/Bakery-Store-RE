package za.co.bakery.backend.data.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.BatchSize;
import za.co.bakery.backend.data.OrderState;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity(name = "OrderInfo")
@Table(indexes = @Index(columnList = "dueDate"))
public class Order extends AbstractDataEntity implements OrderSummary{

    @NotNull(message = "Due time cannot be null.")
    private LocalTime dueTime;
    @NotNull(message = "Due date cant be null.")
    private LocalDate dueDate;
    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer;
    @NotNull
    @ManyToOne
    private PickUpLocation pickUpLocation;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderColumn
    @JoinColumn
    @BatchSize(size = 1000)
    @NotEmpty
    @Valid
    private List<OrderItem> itemList;
    @NotNull(message = "Cant be null")
    private OrderState orderState;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderColumn
    @JoinColumn
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
