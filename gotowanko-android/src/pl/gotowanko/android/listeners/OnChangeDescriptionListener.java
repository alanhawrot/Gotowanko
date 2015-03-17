package pl.gotowanko.android.listeners;

import pl.gotowanko.android.CreateTrainingCardActivity;
import android.text.Editable;
import android.text.TextWatcher;

public final class OnChangeDescriptionListener implements TextWatcher {

	private CreateTrainingCardActivity cardActivity;

	public OnChangeDescriptionListener(CreateTrainingCardActivity cardActivity) {
		this.cardActivity = cardActivity;

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	}

	@Override
	public void afterTextChanged(Editable s) {
		String description = cardActivity.getDescriptionEditText().getText().toString();
		cardActivity.getCard().setDescription(description);
	}
}