package za.co.bakery.backend.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
