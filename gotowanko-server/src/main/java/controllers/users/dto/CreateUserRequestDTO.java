package controllers.users.dto;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Size;

/**
 * Created by alanhawrot on 22.03.15.
 */
public class CreateUserRequestDTO {

    @Email
    private String email;

    @Size(min = 6)
    private String password;

    public CreateUserRequestDTO() {
    }

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
}
