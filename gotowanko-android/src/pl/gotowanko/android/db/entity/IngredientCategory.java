package pl.gotowanko.android.db.entity;

import java.util.List;

import pl.gotowanko.android.db.dao.IngredientCategoryDAO;
import pl.gotowanko.android.db.dao.IngredientDAO;

public class IngredientCategory {
	private IngredientCategoryDAO icDAO;
	private IngredientDAO iDAO;
	private List<IngredientCategory> categories;
	private List<Ingredient> ingredients;
	private Long id;
	private String name;
	private String image;

	public IngredientCategory(IngredientCategoryDAO icDAO, IngredientDAO iDAO) {
		super();
		this.icDAO = icDAO;
		this.iDAO = iDAO;
	}

	public List<IngredientCategory> getCategories() {
		if (categories == null) {
			categories = icDAO.filter(this);
		}
		return categories;
	}

	public List<Ingredient> getIngredients() {
		if (ingredients == null)
			ingredients = iDAO.filter(this);
		return ingredients;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
