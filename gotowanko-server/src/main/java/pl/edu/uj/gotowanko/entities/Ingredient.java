package pl.edu.uj.gotowanko.entities;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

/**
 * Created by michal on 18.03.15.
 */
@Entity(name = "Ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @ColumnDefault("'/images/ingredients/noImage.png'")
    private String iconUrl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
