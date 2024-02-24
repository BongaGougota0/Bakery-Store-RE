package za.co.bakery.backend.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
public class PickUpLocation extends AbstractDataEntity{
    @Size(max = 200)
    @NotBlank
    @Column(unique = true)
    private String name;
}
