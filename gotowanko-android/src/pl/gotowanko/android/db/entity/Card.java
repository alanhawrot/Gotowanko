package pl.gotowanko.android.db.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import pl.gotowanko.android.base.GotowankoApplication;

public class Card implements Serializable {
	private static final long serialVersionUID = 3294502018504805676L;
	private Long id;
	private String title;
	private String description;
	private Date timerDuration;
	private Date expectedTimeDuration;
	private List<IngredientAmmount> ingredients;

	public Card() {
		super();
	}

	public List<IngredientAmmount> getIngredients() {
		if (ingredients == null)
			ingredients = GotowankoApplication.getIngredientAmmountDAO().filter(this);
		return ingredients;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTimerDuration() {
		return timerDuration;
	}

	public void setTimerDuration(Date timerDuration) {
		this.timerDuration = timerDuration;
	}

	public Date getExpectedTimeDuration() {
		return expectedTimeDuration;
	}

	public void setExpectedTimeDuration(Date expectedTimeDuration) {
		this.expectedTimeDuration = expectedTimeDuration;
	}

}
