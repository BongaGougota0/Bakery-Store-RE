package za.co.bakery.backend.data.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class OrderItem extends AbstractDataEntity{
    private Product product;
    @Min(1)
    @NotNull
    private Integer quantity = Integer.valueOf(1);
    @Size(max = 255)
    private String comment;

    public int getTotalPrice(){return quantity == null || product == null ? 0 : quantity * product.getPrice();}
}
