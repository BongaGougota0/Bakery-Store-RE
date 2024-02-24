package za.co.bakery.backend.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Data
@Entity
public class Product extends AbstractDataEntity{
    @NotBlank(message = "Product name cannot be null")
    @Size(max = 255)
    @Column(unique = true)
    private String name;
    @Min(value = 0, message = "Limit amount")
    @Max(value = 10000, message = "Maximum value")
    private Integer price;
}
