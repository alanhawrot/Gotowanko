package pl.gotowanko.android.dialogfragments;

import pl.gotowanko.android.IngredientsActivity;
import pl.gotowanko.android.db.entity.Ingredient;
import pl.gotowanko.android.db.entity.IngredientAmmount;
import pl.gotowanko.android.db.entity.Unit;
import pl.gotowanko.android.listeners.OnLongClickRemoveFromParent;
import pl.gotowanko.android.views.IngredientAmmountPicker;
import pl.gotowanko.android.views.IngredientAmmountView;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;

public class IngredientAmmountPickerDialogFragment extends DialogFragment {
	Ingredient ingredient;
	int ammount = 0;
	private IngredientAmmountPicker picker;

	public IngredientAmmountPickerDialogFragment(Ingredient ingredient) {
		super();
		this.ingredient = ingredient;
	}

	public IngredientAmmountPickerDialogFragment() {
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			ingredient = (Ingredient) savedInstanceState.getSerializable("ingredient");
			ammount = savedInstanceState.getInt("ammount");
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("ingredient", ingredient);
		outState.putInt("ammount", picker.getAmmount());
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final IngredientsActivity ingredientsActivity = (IngredientsActivity) getActivity();
		picker = new IngredientAmmountPicker(ingredientsActivity, ingredient);
		// picker.setOnCancelListener(new OnCancelDissmisDialog());
		picker.setAmmount(ammount);
		picker.setOnOkButtonListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int ammount = picker.getAmmount();
				picker.dismiss();
				if (ammount != 0) {

					Unit unit = picker.getIngredient().getDefaultUnit();
					IngredientAmmount ia = new IngredientAmmount();
					ia.setAmmount((double) ammount);
					ia.setUnit(unit);
					ia.setIngredient(picker.getIngredient());

					IngredientAmmountView iaView = new IngredientAmmountView(ingredientsActivity, ia);
					iaView.setOnLongClickListener(new OnLongClickRemoveFromParent());
					ingredientsActivity.getChoosenIngredients().addView(iaView);
				}

			}
		});

		return picker;
	}
}
