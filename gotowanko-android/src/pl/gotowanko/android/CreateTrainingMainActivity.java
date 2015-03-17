package pl.gotowanko.android;

import pl.gotowanko.android.base.GotowankoApplication;
import pl.gotowanko.android.db.entity.Training;
import pl.gotowanko.android.listeners.OnChangeTrainingName;
import pl.gotowanko.android.listeners.OnCreateRightArrowButtonListener;
import pl.gotowanko.android.listeners.OnPickImageListener;
import pl.gotowanko.android.tools.PhotoUtils;
import pl.gotowanko.android.views.CardNavButton;
import pl.gotowanko.android.views.MainNavButton;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CreateTrainingMainActivity extends CreateTrainingBaseActivity {
	public static final String TRAINING = "training";
	public static final int CAMERA_REQUEST = 111575;
	private EditText titleEditText;
	private ImageButton leftArrowButton;
	private ImageButton rightArrowButton;
	private Training training;
	private LinearLayout navItems;
	private MainNavButton mainNavButton;
	private ImageView image;
	private PhotoUtils photoUtils;

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putSerializable(TRAINING, training);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_create_training_main);
		super.onCreate(savedInstanceState);

		if (savedInstanceState != null) {
			training = (Training) savedInstanceState.getSerializable(TRAINING);
		} else {
			training = (Training) getIntent().getSerializableExtra(TRAINING);
			if (training == null)
				training = GotowankoApplication.getTrainingDAO().newTraining();
		}

		titleEditText = (EditText) findViewById(R.id.titleEditText);
		leftArrowButton = (ImageButton) findViewById(R.id.leftArrowButton);
		rightArrowButton = (ImageButton) findViewById(R.id.rightArrowButton);
		navItems = (LinearLayout) findViewById(R.id.navItems);
		image = (ImageView) findViewById(R.id.image);
		leftArrowButton.setEnabled(false);

		if (training.getName() != null) {
			titleEditText.setText(training.getName());
		}
		photoUtils = new PhotoUtils(this);
		if (training.getImage() != null) {
			image.setImageDrawable(photoUtils.getDrawableByKeyOrPath(training.getImage()));
		}

		rightArrowButton.setOnClickListener(new OnCreateRightArrowButtonListener(this));
		titleEditText.addTextChangedListener(new OnChangeTrainingName(this));
		image.setOnClickListener(new OnPickImageListener(this));

		mainNavButton = new MainNavButton(this);
		navItems.addView(mainNavButton);
		Log.i("recreated", String.valueOf(training.getId()));
		for (int i = 0; i < training.getCards(false).size(); i++) {
			navItems.addView(new CardNavButton(this, i));
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
			Bitmap photo = (Bitmap) data.getExtras().get("data");
			String storePhoto = photoUtils.storePhoto(photo);
			training.setImage(storePhoto);
			image.setImageDrawable(photoUtils.getDrawableByKeyOrPath(storePhoto));
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		mainNavButton.setEnabled(false);
		mainNavButton.requestFocusFromTouch();
		mainNavButton.clearFocus();
		if (training.getImage() != null) {
			image.setImageDrawable(photoUtils.getDrawableByKeyOrPath(training.getImage()));
		}
	}

	public void moveToFirstCard() {
		Intent intent = new Intent(CreateTrainingMainActivity.this, CreateTrainingCardActivity.class);
		intent.putExtra(TRAINING, training);
		intent.putExtra(CreateTrainingCardActivity.CARD_INDEX, 0);
		startActivity(intent);
		finish();
	}

	public EditText getTitleEditText() {
		return titleEditText;
	}

	public void setTitleEditText(EditText titleEditText) {
		this.titleEditText = titleEditText;
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

	@Override
	public Training getTraining() {
		return training;
	}

	public void setTraining(Training training) {
		this.training = training;
	}

}
