package pl.gotowanko.android.listeners;

import pl.gotowanko.android.CreateTrainingMainActivity;
import pl.gotowanko.android.tools.OnSwipeListener;

public final class OnSwipeCreateTrainingListener implements OnSwipeListener {
	private final CreateTrainingMainActivity createTrainingMainActivity;

	public OnSwipeCreateTrainingListener(CreateTrainingMainActivity createTrainingMainActivity) {
		this.createTrainingMainActivity = createTrainingMainActivity;
	}

	@Override
	public void onTopToBottomSwipe() {
	}

	@Override
	public void onRightToLeftSwipe() {
		this.createTrainingMainActivity.moveToFirstCard();
	}

	@Override
	public void onLeftToRightSwipe() {
	}

	@Override
	public void onBottomToTopSwipe() {
	}
}