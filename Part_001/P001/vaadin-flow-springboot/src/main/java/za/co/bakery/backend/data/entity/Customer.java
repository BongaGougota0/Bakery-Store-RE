package za.co.bakery.backend.data.entity;

import jakarta.persistence.Entity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Data
public class Customer extends AbstractDataEntity{
    @NotBlank
    @Size(max=255)
    private String fullName;
    @Pattern(regexp = "^(\\+\\d+)?([-]?\\d+){4,14}$", message = "Invalid phone number")
    private String phoneNumber;
    @Size(max=255)
    private String details;

}
