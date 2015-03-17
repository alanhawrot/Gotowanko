package pl.gotowanko.android.db.dao;

import java.util.List;

import pl.gotowanko.android.db.entity.Card;
import pl.gotowanko.android.db.entity.Training;

public interface CardDAO {
	void insert(Card card);

	void update(Card card);

	void delete(Card card);

	List<Card> filter(Training training);
}
