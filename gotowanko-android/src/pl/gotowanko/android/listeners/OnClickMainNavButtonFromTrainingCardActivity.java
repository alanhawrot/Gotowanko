package pl.gotowanko.android.listeners;

import pl.gotowanko.android.CreateTrainingCardActivity;
import pl.gotowanko.android.CreateTrainingMainActivity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class OnClickMainNavButtonFromTrainingCardActivity implements OnClickListener {

	private CreateTrainingCardActivity card;

	public OnClickMainNavButtonFromTrainingCardActivity(CreateTrainingCardActivity card) {
		this.card = card;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(card, CreateTrainingMainActivity.class);
		intent.putExtra(CreateTrainingMainActivity.TRAINING, card.getTraining());
		card.startActivity(intent);
		card.finish();
	}

}
