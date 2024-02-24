package za.co.bakery.backend.data.entity;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
public class OrderItem extends AbstractDataEntity{
    private Product product;
    @Min(1)
    @NotNull
    private Integer quantity = Integer.valueOf(1);
    @Size(max = 255)
    private String comment;

    public int getTotalPrice(){return quantity == null || product == null ? 0 : quantity * product.getPrice();}
}
