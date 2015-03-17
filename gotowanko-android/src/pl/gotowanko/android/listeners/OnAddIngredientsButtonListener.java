package pl.gotowanko.android.listeners;

import pl.gotowanko.android.CreateTrainingCardActivity;
import pl.gotowanko.android.IngredientsActivity;
import pl.gotowanko.android.support.IngredientAmmountLinearLayoutAdapter;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public final class OnAddIngredientsButtonListener implements OnClickListener {
	private final CreateTrainingCardActivity createTrainingCardActivity;
	private LinearLayout ingredientsLayout;

	public OnAddIngredientsButtonListener(CreateTrainingCardActivity createTrainingCardActivity, LinearLayout ingredientsLayout) {
		this.createTrainingCardActivity = createTrainingCardActivity;
		this.ingredientsLayout = ingredientsLayout;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this.createTrainingCardActivity, IngredientsActivity.class);
		createTrainingCardActivity.startActivityForResult(intent, IngredientsActivity.CHOOSE_INGREDIENTS);
		//TODO  is refreshing layout reallysupposed to be here?
		IngredientAmmountLinearLayoutAdapter adapter = new IngredientAmmountLinearLayoutAdapter(createTrainingCardActivity.getCard().getIngredients());
		createTrainingCardActivity.setAdapter(adapter);
		adapter.updateIngredients(ingredientsLayout);

	}
}