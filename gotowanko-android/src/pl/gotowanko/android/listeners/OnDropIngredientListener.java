package pl.gotowanko.android.listeners;

import pl.gotowanko.android.IngredientsActivity;
import pl.gotowanko.android.dialogfragments.IngredientAmmountPickerDialogFragment;
import android.graphics.Color;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;

public final class OnDropIngredientListener implements OnDragListener {
	private final IngredientsActivity ingredientsActivity;

	public OnDropIngredientListener(IngredientsActivity ingredientsActivity) {
		this.ingredientsActivity = ingredientsActivity;
	}

	@Override
	public boolean onDrag(View v, DragEvent event) {
		switch (event.getAction()) {
		case DragEvent.ACTION_DRAG_STARTED: // gdy podniesiono element
			ingredientsActivity.getChoosenIngredients().setBackgroundColor(Color.GRAY);
			break;
		case DragEvent.ACTION_DRAG_ENTERED: // gdy przecielismy obraz
			break;
		case DragEvent.ACTION_DRAG_EXITED: // gdy było nad koszykiem,
											// ale wyszlo
			break;
		case DragEvent.ACTION_DROP: // gdy wpadło do koszyka.
			initializeAmmountPickingDialog();
			break;
		case DragEvent.ACTION_DRAG_ENDED: // gdy opuściliśmy
											// gdziekolwiek
			ingredientsActivity.getChoosenIngredients().setBackgroundColor(Color.LTGRAY);
			break;
		default:
			break;
		}
		return true;
	}

	private void initializeAmmountPickingDialog() {
		new IngredientAmmountPickerDialogFragment(ingredientsActivity.getDraggedIngredient())
				.show(ingredientsActivity.getFragmentManager(), "IngredientAmmountPickerDialogFragment");
	}
}