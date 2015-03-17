package pl.gotowanko.android.db.dao;

import java.util.List;

import pl.gotowanko.android.db.entity.Training;

public interface TrainingDAO {
	void insert(Training training);

	void update(Training training);

	void delete(Training training);

	List<Training> filter(String name);
	
	Training get(String name);

	List<Training> getAll();

	Training newTraining();

	Training getById(long id);
}
