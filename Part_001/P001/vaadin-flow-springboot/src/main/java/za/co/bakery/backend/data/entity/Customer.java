package za.co.bakery.backend.data.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;



@Entity
@Getter
@Setter
public class Customer extends AbstractDataEntity{
    @NotBlank
    @Size(max=255)
    private String fullName;
    @Pattern(regexp = "^(\\+\\d+)?([-]?\\d+){4,14}$", message = "Invalid phone number")
    private String phoneNumber;
    @Size(max=255)
    private String details;

}
