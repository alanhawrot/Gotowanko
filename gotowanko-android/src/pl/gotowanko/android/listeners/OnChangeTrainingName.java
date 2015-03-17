package pl.gotowanko.android.listeners;

import pl.gotowanko.android.CreateTrainingMainActivity;
import android.text.Editable;
import android.text.TextWatcher;

public final class OnChangeTrainingName implements TextWatcher {
	private final CreateTrainingMainActivity createTrainingMainActivity;

	public OnChangeTrainingName(CreateTrainingMainActivity createTrainingMainActivity) {
		this.createTrainingMainActivity = createTrainingMainActivity;
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	}

	@Override
	public void afterTextChanged(Editable s) {
		String title = createTrainingMainActivity.getTitleEditText().getText().toString();
		createTrainingMainActivity.getTraining().setName(title);
	}
}