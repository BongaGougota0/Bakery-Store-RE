package za.co.bakery.backend.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "pickupLocation")
@Setter
@Getter
public class PickUpLocation extends AbstractDataEntity{
    @Size(max = 200)
    @NotBlank
    @Column(unique = true)
    private String name;
}
