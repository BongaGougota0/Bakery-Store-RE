package za.co.bakery.backend.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
//@Getter
//@Setter
public class Product extends AbstractDataEntity{
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @NotBlank(message = "Product name cannot be null")
    @Size(max = 255)
    @Column(unique = true)
    private String name;

    @Min(value = 0, message = "Limit amount")
    @Max(value = 100000, message = "Maximum value")
    private Integer price;

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }
}
