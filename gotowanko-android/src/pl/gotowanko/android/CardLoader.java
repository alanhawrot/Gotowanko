package pl.gotowanko.android;

public interface CardLoader {

	public abstract void loadCard(int cardNumber);

	public abstract void moveToleftCard();

	public abstract void moveToRightCard();

}