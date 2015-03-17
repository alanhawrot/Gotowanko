package pl.gotowanko.android.listeners;

import pl.gotowanko.android.CreateTrainingCardActivity;
import android.text.Editable;
import android.text.TextWatcher;

public final class OnChangeCardTitleListener implements TextWatcher {
	private final CreateTrainingCardActivity createTrainingCardActivity;

	public OnChangeCardTitleListener(CreateTrainingCardActivity createTrainingCardActivity) {
		this.createTrainingCardActivity = createTrainingCardActivity;
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	}

	@Override
	public void afterTextChanged(Editable s) {
		String title = createTrainingCardActivity.getTitleEditText().getText().toString();
		if (!title.trim().isEmpty())
			createTrainingCardActivity.getRightArrowButton().setEnabled(true);
		createTrainingCardActivity.getCard().setTitle(title);
	}
}