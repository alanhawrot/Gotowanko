package pl.gotowanko.android.listeners;

import pl.gotowanko.android.IngredientsActivity;
import pl.gotowanko.android.views.IngredientView;
import android.content.ClipData;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnTouchListener;

public final class OnDragIngredientListener implements OnTouchListener {
	private final IngredientsActivity ingredientsActivity;
	private final IngredientView view;

	public OnDragIngredientListener(IngredientsActivity ingredientsActivity, IngredientView view) {
		this.ingredientsActivity = ingredientsActivity;
		this.view = view;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		v.performClick();
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			ingredientsActivity.setDraggedIngredient(view.getIngredient());
			ClipData data = ClipData.newPlainText("", "");
			DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
			view.startDrag(data, shadowBuilder, view, 0);
			view.setVisibility(View.INVISIBLE);
			return true;
		} else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
			view.setVisibility(View.VISIBLE);
			return true;
		} else {
			return false;
		}
	}
}