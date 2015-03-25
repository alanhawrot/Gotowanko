package controllers.users.dto;

import entities.Comment;
import entities.Recipe;

import java.util.Calendar;
import java.util.Collection;

/**
 * Created by alanhawrot on 25.03.15.
 */
public class GetUserResponseDTO {

    private long id;
    private String email;
    private Collection<Recipe> recipes;
    private Collection<Comment> comments;
    private Calendar registrationDate;
    private Calendar lastLogged;

    public GetUserResponseDTO() {
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

    public Collection<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Collection<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    public Calendar getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Calendar registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Calendar getLastLogged() {
        return lastLogged;
    }

    public void setLastLogged(Calendar lastLogged) {
        this.lastLogged = lastLogged;
    }
}
