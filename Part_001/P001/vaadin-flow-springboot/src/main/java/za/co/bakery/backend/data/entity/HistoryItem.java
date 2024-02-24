package za.co.bakery.backend.data.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import za.co.bakery.backend.data.OrderState;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

}
