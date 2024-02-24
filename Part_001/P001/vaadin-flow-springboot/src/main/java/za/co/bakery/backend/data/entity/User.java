package za.co.bakery.backend.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
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
