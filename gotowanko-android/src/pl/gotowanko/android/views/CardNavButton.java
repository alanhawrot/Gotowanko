package pl.gotowanko.android.views;

import pl.gotowanko.android.CreateTrainingCardActivity;
import pl.gotowanko.android.CreateTrainingMainActivity;
import pl.gotowanko.android.listeners.OnClickCardButtonFromTrainingCardActivity;
import pl.gotowanko.android.listeners.OnClickCardButtonFromTrainingMainActivity;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;

public class CardNavButton extends Button {

	public CardNavButton(CreateTrainingCardActivity card, int cardIndex) {
		super(card);
		setId(cardIndex);
		setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		setOnClickListener(new OnClickCardButtonFromTrainingCardActivity(card, cardIndex));
		setText(String.valueOf(cardIndex + 1));
	}

	public CardNavButton(final CreateTrainingMainActivity trainingMain, int cardIndex) {
		super(trainingMain);
		setId(cardIndex);
		setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		setOnClickListener(new OnClickCardButtonFromTrainingMainActivity(trainingMain, cardIndex));
		setText(String.valueOf(cardIndex + 1));
	}
}
