package za.co.bakery.backend.data.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.hibernate.annotations.BatchSize;
import za.co.bakery.backend.data.OrderState;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity(name = "OrderInfo")
@NamedEntityGraphs({@NamedEntityGraph(name = Order.ENTITY_GRAPH_BRIEF, attributeNodes = {
        @NamedAttributeNode("customer"),
        @NamedAttributeNode("pickupLocation")
}), @NamedEntityGraph(name=Order.ENTITY_GRAPH_FULL, attributeNodes =
        {@NamedAttributeNode("customer"),
        @NamedAttributeNode("pickupLocation"),
        @NamedAttributeNode("itemList"),
        @NamedAttributeNode("historyItems")
        })})
@Table(indexes = @Index(columnList = "dueDate"))
public class Order extends AbstractDataEntity implements OrderSummary{

    public static final String ENTITY_GRAPH_BRIEF = "Order.brief";
    public static final String ENTITY_GRAPH_FULL = "Order.full";

    @NotNull(message = "Due time cannot be null.")
    private LocalTime dueTime;

    @NotNull(message = "Due date cant be null.")
    private LocalDate dueDate;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private Customer customer;

    @NotNull(message = "Pickup location is required.")
    @ManyToOne
    private PickupLocation pickupLocation;

    @Getter
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderColumn
    @JoinColumn
    @BatchSize(size = 1000)
    @NotEmpty
    @Valid
    private List<OrderItem> itemList;

    @Getter
    @NotNull(message = "Cant be null")
    private OrderState orderState;

    @Getter
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderColumn
    @JoinColumn
    private List<HistoryItem> historyItems;

    public Order() {
        //Spring Data jpa blank constructor
    }

    public Order(User createdBy) {
        this.orderState = OrderState.NEW;
        setCustomer(new Customer());
        addHistoryItem(createdBy,"Order placed");
        this.itemList = new ArrayList<>();
    }

    private void setCustomer(Customer customer){
        this.customer = customer;
    }

    public void setDueTime(LocalTime dueTime) {
        this.dueTime = dueTime;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setPickUpLocation(PickupLocation pickUpLocation) {
        this.pickupLocation = pickUpLocation;
    }

    public void setItemList(List<OrderItem> itemList) {
        this.itemList = itemList;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public void setHistoryItems(List<HistoryItem> historyItems) {
        this.historyItems = historyItems;
    }

    @Override
    public Long getId() {
        return getId();
    }

    @Override
    public OrderState getState() {
        return orderState;
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public List<OrderItem> getOrderItems() {
        return itemList;
    }

    @Override
    public LocalDate getDueDate() {
        return dueDate;
    }

    @Override
    public LocalTime getDueTime() {
        return dueTime;
    }

    @Override
    public PickupLocation getLocation() {
        return pickupLocation;
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

    public void addHistoryItem(User user, String comment){
        HistoryItem item = new HistoryItem(user,comment);
        item.setNewState(orderState);
        if(historyItems == null){
            historyItems = new LinkedList<>();
        }
        historyItems.add(item);
    }

    public void changeState(User user, OrderState orderState){
        boolean createHistory = this.orderState != orderState && this.orderState != null && orderState != null;
    }
}
