package pl.gotowanko.android.db.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.gotowanko.android.R;
import pl.gotowanko.android.base.GotowankoApplication;

public class Training implements Serializable {
	private static final long serialVersionUID = 651370821996404167L;
	private Long id;
	private String name;
	private String image;
	private Date modificationDate;

	private List<Card> cards;

	public Training() {
		super();
		setImage(String.valueOf(R.drawable.noimage));
		setModificationDate(new Date());
		cards = new ArrayList<Card>();
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

	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public List<Card> getCards() {
		cards = GotowankoApplication.getCardDAO().filter(this);
		return cards;
	}

	public List<Card> getCards(boolean fetch) {
		if (!fetch)
			return cards;
		return getCards();
	}
}
