package controllers.users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Calendar;

/**
 * Created by alanhawrot on 23.03.15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateUserResponseDTO {

    private long id;
    private String email;
    private Calendar registrationDate;

    public CreateUserResponseDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Calendar getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Calendar registrationDate) {
        this.registrationDate = registrationDate;
    }
}
