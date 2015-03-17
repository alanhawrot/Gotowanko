package pl.gotowanko.android.listeners;

import pl.gotowanko.android.CardLoader;
import pl.gotowanko.android.tools.OnSwipeListener;

public final class OnSwipeCardListener implements OnSwipeListener {
	private final CardLoader cardLoader;

	public OnSwipeCardListener(CardLoader cardLoader) {
		this.cardLoader = cardLoader;
	}

	@Override
	public void onTopToBottomSwipe() {
	}

	@Override
	public void onRightToLeftSwipe() {
		this.cardLoader.moveToRightCard();
	}

	@Override
	public void onLeftToRightSwipe() {
		this.cardLoader.moveToleftCard();
	}

	@Override
	public void onBottomToTopSwipe() {
	}
}