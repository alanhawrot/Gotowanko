package entities;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by alanhawrot on 18.03.15.
 */
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Temporal(value = TemporalType.DATE)
    private Calendar dateAdded;

    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Calendar lastEdited;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Recipe recipe;

    public Comment() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
