package pl.edu.uj.gotowanko.controllers.recipes.dto;

/**
 * Created by michal on 03.05.15.
 */
public class GetRecipeStepIngredientResponseDTO {
    private String name;
    private Long id;
    private String iconUrl;
    private Double amount;
    private String unitName;
    private String unitShortName;
    private Long unitId;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public void setUnitShortName(String unitShortName) {
        this.unitShortName = unitShortName;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public Double getAmount() {
        return amount;
    }

    public String getUnitName() {
        return unitName;
    }

    public String getUnitShortName() {
        return unitShortName;
    }

    public Long getUnitId() {
        return unitId;
    }


}
