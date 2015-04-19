package pl.edu.uj.gotowanko.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by alanhawrot on 18.03.15.
 */
@Entity(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private Collection<Recipe> recipes = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Collection<Comment> comments = new HashSet<>();

    @Column(nullable = false)
    @Temporal(value = TemporalType.DATE)
    private Calendar registrationDate;

    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Calendar lastLogged;

    @ManyToMany(targetEntity = Recipe.class)
    private Collection<Recipe> recipeLikes = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    private void setRecipes(Collection<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    private void setComments(Collection<Comment> comments) {
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

    public Collection<Recipe> getRecipeLikes() {
        return recipeLikes;
    }

    private void setRecipeLikes(Collection<Recipe> recipeLikes) {
        this.recipeLikes = recipeLikes;
    }

    public void addRecipeLike(Recipe recipe) {
        getRecipeLikes().add(recipe);
    }

    public void removeRecipeLike(Recipe recipe) {
        getRecipeLikes().remove(recipe);
    }

    public boolean containsRecipeLike(Recipe recipe) {
        return getRecipeLikes().contains(recipe);
    }
}
