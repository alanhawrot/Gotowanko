package pl.gotowanko.android.listeners;

import pl.gotowanko.android.CardLoader;
import android.view.View;
import android.view.View.OnClickListener;

public final class OnClickLeftArrowButtonListener implements OnClickListener {

	private final CardLoader createTrainingCardActivity;

	public OnClickLeftArrowButtonListener(CardLoader createTrainingCardActivity) {
		this.createTrainingCardActivity = createTrainingCardActivity;
	}

	@Override
	public void onClick(View v) {
		this.createTrainingCardActivity.moveToleftCard();

	}
}