package pl.edu.uj.gotowanko.controllers.users.dto;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by alanhawrot on 19.04.15.
 */
public class GetCurrentlyLoggedUserResponseDTO {

    private long id;
    private String email;
    private String name;
    private Collection<GetUserRecipeResponseDTO> recipes = new HashSet<>();
    private Collection<GetUserCommentResponseDTO> comments = new HashSet<>();
    private Calendar registrationDate;
    private Calendar lastLogged;
    private Collection<Long> recipeLikes = new HashSet<>();

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

    public Collection<GetUserRecipeResponseDTO> getRecipes() {
        return recipes;
    }

    public void setRecipes(Collection<GetUserRecipeResponseDTO> recipes) {
        this.recipes = recipes;
    }

    public Collection<GetUserCommentResponseDTO> getComments() {
        return comments;
    }

    public void setComments(Collection<GetUserCommentResponseDTO> comments) {
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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Collection<Long> getRecipeLikes() {
        return recipeLikes;
    }

    public void setRecipeLikes(Collection<Long> recipeLikes) {
        this.recipeLikes = recipeLikes;
    }
}
