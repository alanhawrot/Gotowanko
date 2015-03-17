package pl.gotowanko.android;

import java.util.List;

import pl.gotowanko.android.db.entity.Card;
import pl.gotowanko.android.db.entity.IngredientAmmount;
import pl.gotowanko.android.db.entity.Training;
import pl.gotowanko.android.listeners.OnAddIngredientsButtonListener;
import pl.gotowanko.android.listeners.OnChangeCardTitleListener;
import pl.gotowanko.android.listeners.OnChangeDescriptionListener;
import pl.gotowanko.android.listeners.OnClickLeftArrowButtonListener;
import pl.gotowanko.android.listeners.OnClickRightArrowButtonListener;
import pl.gotowanko.android.support.IngredientAmmountLinearLayoutAdapter;
import pl.gotowanko.android.views.CardNavButton;
import pl.gotowanko.android.views.MainNavButton;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class CreateTrainingCardActivity extends CreateTrainingBaseActivity implements CardLoader {
	public static final String TRAINING = "training";
	public static final String CARD_INDEX = "cardNumber";

	private Training training;
	private Card card;
	private int cardIndex = -1;
	private EditText titleEditText;
	private EditText descriptionEditText;
	private ImageButton leftArrowButton;
	private ImageButton rightArrowButton;
	private Button addIngredientsButton;
	private LinearLayout ingredientsLayout;
	private IngredientAmmountLinearLayoutAdapter adapter;
	private LinearLayout navItems;

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putSerializable(TRAINING, training);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			training = (Training) savedInstanceState.getSerializable(TRAINING);
		} else {
			training = (Training) getIntent().getSerializableExtra(CreateTrainingMainActivity.TRAINING);
		}

		setContentView(R.layout.activity_create_training_card);
		titleEditText = (EditText) findViewById(R.id.titleEditText);
		descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
		leftArrowButton = (ImageButton) findViewById(R.id.leftArrowButton);
		addIngredientsButton = (Button) findViewById(R.id.addIngredient);
		rightArrowButton = (ImageButton) findViewById(R.id.rightArrowButton);
		ingredientsLayout = (LinearLayout) findViewById(R.id.ingredientsLayout);
		navItems = (LinearLayout) findViewById(R.id.navItems);

		cardIndex = getIntent().getIntExtra(CARD_INDEX, -1);

		if (cardIndex < 0 || cardIndex >= training.getCards(false).size()) {
			moveToNewCard();
			createNavButtons();
			refreshNavBar();
		} else {
			createNavButtons();
			loadCard(cardIndex);
		}

		titleEditText.addTextChangedListener(new OnChangeCardTitleListener(this));
		descriptionEditText.addTextChangedListener(new OnChangeDescriptionListener(this));
		leftArrowButton.setOnClickListener(new OnClickLeftArrowButtonListener(this));
		rightArrowButton.setOnClickListener(new OnClickRightArrowButtonListener(this));
		addIngredientsButton.setOnClickListener(new OnAddIngredientsButtonListener(this, ingredientsLayout));

	}

	private void createNavButtons() {
		navItems.removeAllViews();
		navItems.addView(new MainNavButton(this));
		for (int i = 0; i < training.getCards(false).size(); i++) {
			navItems.addView(new CardNavButton(this, i));
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		refreshNavBar();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == IngredientsActivity.CHOOSE_INGREDIENTS && resultCode == RESULT_OK) {
			@SuppressWarnings("unchecked")
			List<IngredientAmmount> i = (List<IngredientAmmount>) data.getSerializableExtra(IngredientsActivity.INGREDIENTS);
			card.getIngredients().clear();
			card.getIngredients().addAll(i);
			adapter = new IngredientAmmountLinearLayoutAdapter(card.getIngredients());
			adapter.updateIngredients(ingredientsLayout);
		}
		super.onActivityResult(requestCode, resultCode, data);

	}

	@Override
	public void loadCard(int cardIndex) {
		this.cardIndex = cardIndex;
		card = training.getCards(false).get(cardIndex);
		adapter = new IngredientAmmountLinearLayoutAdapter(card.getIngredients());
		adapter.updateIngredients(ingredientsLayout);
		if (card.getTitle() != null) {
			titleEditText.setText(card.getTitle());
		} else {
			titleEditText.setText("");
		}

		if (card.getDescription() != null) {
			descriptionEditText.setText(card.getDescription());
		} else {
			descriptionEditText.setText("");
		}
		refreshNavBar();
	}

	@Override
	public void moveToleftCard() {
		if (--cardIndex >= 0) {
			loadCard(cardIndex);
			refreshNavBar();
		} else {
			moveToCreateTrainingMainActivity();
		}
	}

	@Override
	public void moveToRightCard() {
		int cardsNumber = training.getCards(false).size();
		if (cardIndex + 1 < cardsNumber) {
			loadCard(++cardIndex);
		} else {
			if (rightArrowButton.isEnabled()) {
				moveToNewCard();
				navItems.addView(new CardNavButton(this, cardIndex));
				rightArrowButton.setEnabled(false);
			}
		}
		refreshNavBar();

	}

	protected void refreshNavBar() {
		int cardsNumber = training.getCards(false).size();
		for (int i = 0; i < cardsNumber; i++) {
			navItems.getChildAt(i + 1).setEnabled(true);
		}
		View item = navItems.getChildAt(cardIndex + 1);
		item.setEnabled(false);
		item.requestFocusFromTouch();
		item.clearFocus();

		if (cardIndex == cardsNumber - 1 && titleEditText.getText().toString().trim().isEmpty())
			rightArrowButton.setEnabled(false);
	}

	private void moveToCreateTrainingMainActivity() {
		Intent intent = new Intent(this, CreateTrainingMainActivity.class);
		intent.putExtra(CreateTrainingCardActivity.TRAINING, training);
		startActivity(intent);
		finish();
	}

	private void moveToNewCard() {

		card = new Card();
		adapter = new IngredientAmmountLinearLayoutAdapter(card.getIngredients());
		adapter.updateIngredients(ingredientsLayout);
		cardIndex = training.getCards(false).size();
		training.getCards(false).add(card);
		titleEditText.setText("");
		descriptionEditText.setText("");

	}

	@Override
	public Training getTraining() {
		return training;
	}

	public void setTraining(Training training) {
		this.training = training;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public int getCardIndex() {
		return cardIndex;
	}

	public void setCardIndex(int cardIndex) {
		this.cardIndex = cardIndex;
	}

	public EditText getTitleEditText() {
		return titleEditText;
	}

	public void setTitleEditText(EditText titleEditText) {
		this.titleEditText = titleEditText;
	}

	public Button getAddIngredientsButton() {
		return addIngredientsButton;
	}

	public void setAddIngredientsButton(Button addIngredientsButton) {
		this.addIngredientsButton = addIngredientsButton;
	}

	public IngredientAmmountLinearLayoutAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(IngredientAmmountLinearLayoutAdapter adapter) {
		this.adapter = adapter;
	}

	public ImageButton getLeftArrowButton() {
		return leftArrowButton;
	}

	public void setLeftArrowButton(ImageButton leftArrowButton) {
		this.leftArrowButton = leftArrowButton;
	}

	public ImageButton getRightArrowButton() {
		return rightArrowButton;
	}

	public void setRightArrowButton(ImageButton rightArrowButton) {
		this.rightArrowButton = rightArrowButton;
	}

	public EditText getDescriptionEditText() {
		return descriptionEditText;
	}

	public void setDescriptionEditText(EditText descriptionEditText) {
		this.descriptionEditText = descriptionEditText;
	}

	public LinearLayout getNavItems() {
		return navItems;
	}

	public void setNavItems(LinearLayout navItems) {
		this.navItems = navItems;
	}

}
