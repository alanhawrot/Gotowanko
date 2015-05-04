package pl.edu.uj.gotowanko.controllers.users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Calendar;

/**
 * Created by alanhawrot on 23.03.15. */
public class CreateUserResponseDTO {

    private long id;
    private String email;
    private Calendar registrationDate;
    private String name;

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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
