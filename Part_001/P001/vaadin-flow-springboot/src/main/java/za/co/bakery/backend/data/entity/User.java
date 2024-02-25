package za.co.bakery.backend.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity(name = "UserInfo")
public class User extends AbstractDataEntity{
    @NotBlank
    @Size(max = 255)
    @Column(unique = true)
    private String email;
    @Size(min = 4)
    @Size(max = 255)
    private String hashedPassword;
    @Size(max = 255)
    @NotBlank
    private String firstName;
    @NotBlank
    @Size(max =255)
    private String lastName;
    @NotBlank
    @Size(max = 20)
    private String role;

    private boolean locked = false;

    public User() {
        //Blank constructor is required by default.
    }
}
