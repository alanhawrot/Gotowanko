package pl.gotowanko.android.views;

import pl.gotowanko.android.CreateTrainingCardActivity;
import pl.gotowanko.android.CreateTrainingMainActivity;
import pl.gotowanko.android.listeners.OnClickMainNavButtonFromTrainingCardActivity;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;

public class MainNavButton extends Button {

	public MainNavButton(CreateTrainingCardActivity card) {
		super(card);
		setId(Integer.MAX_VALUE);
		setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		setOnClickListener(new OnClickMainNavButtonFromTrainingCardActivity(card));
		setText("M");
	}

	public MainNavButton(final CreateTrainingMainActivity trainingMain) {
		super(trainingMain);
		setId(Integer.MAX_VALUE);
		setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		setText("M");
	}
}
