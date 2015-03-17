package pl.gotowanko.android.listeners;

import pl.gotowanko.android.CardLoader;
import android.view.View;
import android.view.View.OnClickListener;

final public class OnClickCardButtonFromTrainingCardActivity implements OnClickListener {
	private final CardLoader createTrainingCardActivity;
	private int cardIndex;

	public OnClickCardButtonFromTrainingCardActivity(CardLoader createTrainingCardActivity, int cardIndex) {
		this.createTrainingCardActivity = createTrainingCardActivity;
		this.cardIndex = cardIndex;
	}

	@Override
	public void onClick(View v) {
		this.createTrainingCardActivity.loadCard(cardIndex);
	}
}