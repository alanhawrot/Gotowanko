package pl.edu.uj.gotowanko.controllers.users.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by alanhawrot on 25.03.15.
 */
public class UpdateUserRequestDTO {

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 6)
    private String password;

    @NotBlank
    private String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
