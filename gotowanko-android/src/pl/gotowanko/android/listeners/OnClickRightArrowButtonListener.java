package pl.gotowanko.android.listeners;

import pl.gotowanko.android.CardLoader;
import android.view.View;
import android.view.View.OnClickListener;

public final class OnClickRightArrowButtonListener implements OnClickListener {
	private final CardLoader cardLoader;

	public OnClickRightArrowButtonListener(CardLoader cardLoader) {
		this.cardLoader = cardLoader;
	}

	@Override
	public void onClick(View v) {
		this.cardLoader.moveToRightCard();
	}
}