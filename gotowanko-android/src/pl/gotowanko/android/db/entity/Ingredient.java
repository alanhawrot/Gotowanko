package pl.gotowanko.android.db.entity;

import java.io.Serializable;

public class Ingredient implements Serializable {
	private static final long serialVersionUID = 8776218807473348238L;
	private Long id;
	private String name;
	private String image;
	private Unit defaultUnit;

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

	public Unit getDefaultUnit() {
		return defaultUnit;
	}

	public void setDefaultUnit(Unit defaultUnit) {
		this.defaultUnit = defaultUnit;
	}

}
