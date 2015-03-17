package pl.gotowanko.android;

import java.util.List;

import pl.gotowanko.android.base.GotowankoActivity;
import pl.gotowanko.android.base.GotowankoApplication;
import pl.gotowanko.android.db.entity.Card;
import pl.gotowanko.android.db.entity.IngredientAmmount;
import pl.gotowanko.android.db.entity.Training;
import pl.gotowanko.android.listeners.OnClickLeftArrowButtonListener;
import pl.gotowanko.android.listeners.OnClickRightArrowButtonListener;
import pl.gotowanko.android.listeners.OnSwipeCardListener;
import pl.gotowanko.android.tools.ActivitySwipeDetector;
import pl.gotowanko.android.views.IngredientAmmountView;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TrainingCardActivity extends GotowankoActivity implements CardLoader {
	public static final String CARD_INDEX = "CARD_INDEX";
	private Training training;
	private Card card;
	private int cardIndex = 0;
	private TextView titleTextView;
	private TextView descriptionTextView;
	private ImageButton leftArrowButton;
	private ImageButton rightArrowButton;
	private String trainingName;
	private TextView cardNumberTextView;
	private TextView cardsNumberTextView;
	private List<Card> cards;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			cardIndex = savedInstanceState.getInt(CARD_INDEX, 0);
		}
		setContentView(R.layout.activity_training_card);

		titleTextView = (TextView) findViewById(R.id.titleTextView);
		cardNumberTextView = (TextView) findViewById(R.id.cardNumberTextView);
		cardsNumberTextView = (TextView) findViewById(R.id.cardsNumberTextView);
		descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
		leftArrowButton = (ImageButton) findViewById(R.id.leftArrowButton);
		rightArrowButton = (ImageButton) findViewById(R.id.rightArrowButton);

		trainingName = getIntent().getStringExtra("TrainingName");

		training = GotowankoApplication.getTrainingDAO().get(trainingName);
		cards = training.getCards();

		if (cardIndex < cards.size()) {
			loadCard(cardIndex);
		} else
			throw new IllegalArgumentException("There is no such card " + cardIndex);

		leftArrowButton.setOnClickListener(new OnClickLeftArrowButtonListener(this));
		rightArrowButton.setOnClickListener(new OnClickRightArrowButtonListener(this));

		ActivitySwipeDetector detector = new ActivitySwipeDetector(findViewById(R.id.mainLayout));
		detector.addOnSwipeEventListener(new OnSwipeCardListener(this));

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(CARD_INDEX, cardIndex);
	};

	@Override
	public void loadCard(int cardIndex) {
		if (cardIndex >= 0 && cardIndex < cards.size()) {
			card = cards.get(cardIndex);

			if (card.getTitle() != null) {
				titleTextView.setText(card.getTitle());
			}
			if (card.getDescription() != null) {
				descriptionTextView.setText(card.getDescription());
			}
			cardNumberTextView.setText(String.valueOf(cardIndex + 1));
			cardsNumberTextView.setText(String.valueOf(cards.size()));
			displayIngredients();
			refreshNavBar();
		}
	}

	@Override
	public void moveToleftCard() {
		if (cardIndex - 1 >= 0) {
			loadCard(--cardIndex);
		}
	}

	@Override
	public void moveToRightCard() {
		if (cardIndex + 1 < cards.size()) {
			loadCard(++cardIndex);
		}
	}

	private void refreshNavBar() {

		leftArrowButton.setEnabled(cardIndex != 0);
		rightArrowButton.setEnabled(cardIndex != cards.size() - 1);
	}

	private void displayIngredients() {
		LinearLayout layout = cleanIngredients();
		for (IngredientAmmount io : card.getIngredients()) {
			IngredientAmmountView iaView = new IngredientAmmountView(this, io);
			layout.addView(iaView);
		}
	}

	private LinearLayout cleanIngredients() {
		LinearLayout layout = (LinearLayout) findViewById(R.id.ingredientsLinearLayout);
		layout.removeAllViews();
		return layout;
	};

}
