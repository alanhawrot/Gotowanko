package entities;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Collection;

/**
 * Created by alanhawrot on 18.03.15.
 */
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @ColumnDefault(value = "'/images/recipe/noImage.png'")
    private String photoUrl;

    @Column
    private int cookingTimeInMinutes;

    @Column
    private int approximateCost;

    @Column
    private int rating;

    @Column
    @Temporal(value = TemporalType.DATE)
    private Calendar dateAdded;

    @Column
    @Temporal(value = TemporalType.TIMESTAMP)
    private Calendar lastEdited;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private Collection<RecipeStep> recipeSteps;

    @ManyToOne(optional = false)
    private User user;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private Collection<Comment> comments;

    public Recipe() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getCookingTimeInMinutes() {
        return cookingTimeInMinutes;
    }

    public void setCookingTimeInMinutes(int cookingTimeInMinutes) {
        this.cookingTimeInMinutes = cookingTimeInMinutes;
    }

    public int getApproximateCost() {
        return approximateCost;
    }

    public void setApproximateCost(int approximateCost) {
        this.approximateCost = approximateCost;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Calendar getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Calendar dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Calendar getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(Calendar lastEdited) {
        this.lastEdited = lastEdited;
    }

    public Collection<RecipeStep> getRecipeSteps() {
        return recipeSteps;
    }

    public void setRecipeSteps(Collection<RecipeStep> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

}
