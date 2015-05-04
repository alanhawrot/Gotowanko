package pl.edu.uj.gotowanko.entities;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

/**
 * Created by michal on 18.03.15.
 */
@Entity(name = "Ingredients")
public class Ingredient {

    private static final String DEFAULT_INGREDIENT_IMAGE = "/images/ingredients/noImage.png";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @ColumnDefault("'" + DEFAULT_INGREDIENT_IMAGE + "'")
    private String iconUrl;

    public Ingredient() {
        this.iconUrl = DEFAULT_INGREDIENT_IMAGE;
    }

    public Ingredient(String name) {
        this.name = name;
        this.iconUrl = DEFAULT_INGREDIENT_IMAGE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        this.iconUrl = iconUrl == null ? DEFAULT_INGREDIENT_IMAGE : iconUrl;
    }
}
