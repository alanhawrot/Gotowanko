package controllers.users.dto;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Size;

/**
 * Created by alanhawrot on 25.03.15.
 */
public class UpdateUserRequestDTO {

    @Email
    private String email;

    @Size(min = 6)
    private String password;

    public UpdateUserRequestDTO() {
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
