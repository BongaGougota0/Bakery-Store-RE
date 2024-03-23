package za.co.bakery.backend.data.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import za.co.bakery.backend.data.OrderState;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class HistoryItem extends AbstractDataEntity{
    private OrderState orderState;
    @NotBlank
    @Size(max = 255)
    private String message;
    @NotBlank
    private LocalDateTime timeStamp;

    public HistoryItem(User createdBy,String message) {
        this.message = message;
        this.createdBy = createdBy;
    }

    @NotNull
    private User createdBy;

    public HistoryItem(OrderState orderState, String message, LocalDateTime timeStamp, User createdBy) {
        this.orderState = orderState;
        this.message = message;
        this.timeStamp = timeStamp;
        this.createdBy = createdBy;
    }

    public HistoryItem() {
    }

    public  void setNewState(OrderState orderState){
        this.orderState = orderState;
    }

}
