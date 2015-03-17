package pl.gotowanko.android.listeners;

import pl.gotowanko.android.CreateTrainingCardActivity;
import pl.gotowanko.android.CreateTrainingMainActivity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public final class OnClickCardButtonFromTrainingMainActivity implements OnClickListener {
	private final CreateTrainingMainActivity trainingMain;
	private int cardIndex;

	public OnClickCardButtonFromTrainingMainActivity(CreateTrainingMainActivity trainingMain, int cardIndex) {
		this.trainingMain = trainingMain;
		this.cardIndex = cardIndex;
	}

	@Override
	public void onClick(View v) {
		Intent i = new Intent(trainingMain, CreateTrainingCardActivity.class);
		i.putExtra(CreateTrainingCardActivity.CARD_INDEX, cardIndex);
		i.putExtra(CreateTrainingCardActivity.TRAINING, trainingMain.getTraining());
		trainingMain.startActivity(i);
		trainingMain.finish();
	}
}