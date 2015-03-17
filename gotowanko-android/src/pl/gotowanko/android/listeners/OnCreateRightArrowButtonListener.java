package pl.gotowanko.android.listeners;

import pl.gotowanko.android.CreateTrainingMainActivity;
import android.view.View;
import android.view.View.OnClickListener;

public final class OnCreateRightArrowButtonListener implements OnClickListener {
	private final CreateTrainingMainActivity createTrainingMainActivity;

	public OnCreateRightArrowButtonListener(CreateTrainingMainActivity createTrainingMainActivity) {
		this.createTrainingMainActivity = createTrainingMainActivity;
	}

	@Override
	public void onClick(View v) {
		this.createTrainingMainActivity.moveToFirstCard();
	}
}