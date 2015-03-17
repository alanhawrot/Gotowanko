package pl.gotowanko.android;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import pl.gotowanko.android.base.GotowankoActivity;
import pl.gotowanko.android.base.GotowankoApplication;
import pl.gotowanko.android.db.entity.Ingredient;
import pl.gotowanko.android.db.entity.IngredientAmmount;
import pl.gotowanko.android.db.entity.IngredientCategory;
import pl.gotowanko.android.dialogfragments.BackFromSetIngredientsDialog;
import pl.gotowanko.android.layouts.FlowLayout;
import pl.gotowanko.android.listeners.OnDragIngredientListener;
import pl.gotowanko.android.listeners.OnDropIngredientListener;
import pl.gotowanko.android.views.IngredientAmmountView;
import pl.gotowanko.android.views.IngredientView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class IngredientsActivity extends GotowankoActivity {
	private static final String INGREDIENT_AMMOUNTS = "choosenIngredientAmmounts";
	public static final int CHOOSE_INGREDIENTS = 121231;
	public static final String INGREDIENTS = "INGREDIENTS";
	public static final int MENU_ACCEPTED_ID = GotowankoActivity.MENU_LAST_ID + 1;
	private Ingredient draggedIngredient;
	private List<Ingredient> possibleIngredients;
	private FlowLayout choosenIngredients;

	private FlowLayout possibleIngredientsLayout;
	public Deque<IngredientCategory> categoryStack = new ArrayDeque<IngredientCategory>();

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle inState) {
		Debug.startMethodTracing("gotowanko");
		super.onCreate(inState);
		setContentView(R.layout.activity_ingredients);

		possibleIngredientsLayout = (FlowLayout) findViewById(R.id.possibleIngredients);
		choosenIngredients = (FlowLayout) findViewById(R.id.choosenIngredients);
		choosenIngredients.setOnDragListener(new OnDropIngredientListener(this));
		categoryStack.push(GotowankoApplication.getIngredientCategoryDAO().getRoot());

		displayTopOfStack();
		Debug.stopMethodTracing();
		List<IngredientAmmount> choosenIngredientAmmounts = null;
		if (inState != null) {
			choosenIngredientAmmounts = (List<IngredientAmmount>) inState.getSerializable(INGREDIENT_AMMOUNTS);
		} else {
			choosenIngredientAmmounts = (List<IngredientAmmount>) getIntent().getSerializableExtra(INGREDIENT_AMMOUNTS);
		}
		if (choosenIngredientAmmounts != null)
			for (IngredientAmmount ia : choosenIngredientAmmounts) {
				choosenIngredients.addView(new IngredientAmmountView(this, ia));
			}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		List<IngredientAmmount> ia = new ArrayList<IngredientAmmount>(choosenIngredients.getChildCount());
		for (int i = 0; i < choosenIngredients.getChildCount(); i++) {
			View v = choosenIngredients.getChildAt(i);
			if (v instanceof LinearLayout) {
				ia.add((IngredientAmmount) v.getTag());
			}
		}
		outState.putSerializable(INGREDIENT_AMMOUNTS, (Serializable) ia);
		super.onSaveInstanceState(outState);
	};

	public void displayTopOfStack() {
		IngredientCategory topCategory = categoryStack.peek();
		possibleIngredientsLayout.removeAllViews();
		for (IngredientCategory ic : topCategory.getCategories()) {
			possibleIngredientsLayout.addView(getCategoryImageView(ic));
		}
		for (Ingredient ia : topCategory.getIngredients()) {
			possibleIngredientsLayout.addView(getDraggableIngredient(ia));
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	protected IngredientView getDraggableIngredient(Ingredient ia) {
		IngredientView i = new IngredientView(this, ia);
		i.setOnTouchListener(new OnDragIngredientListener(this, i));
		return i;
	}

	@SuppressLint("ClickableViewAccessibility")
	private View getCategoryImageView(IngredientCategory ic) {
		View view = new IngredientView(this, ic);
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				IngredientCategory ic = (IngredientCategory) v.getTag();
				categoryStack.push(ic);
				displayTopOfStack();
			}
		});
		return view;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(MENU_GROUP_ID, MENU_ACCEPTED_ID, 0, getText(R.string.done))
				.setIcon(R.drawable.accept)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == MENU_ACCEPTED_ID) {
			save();
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void save() {
		List<IngredientAmmount> ingredients = new ArrayList<IngredientAmmount>();
		for (int i = 0; i < choosenIngredients.getChildCount(); i++) {
			View v = choosenIngredients.getChildAt(i);
			if (v instanceof LinearLayout) {
				ingredients.add((IngredientAmmount) v.getTag());
			}
		}
		Intent i = new Intent();
		i.putExtra(INGREDIENTS, (Serializable) ingredients);
		setResult(RESULT_OK, i);
	}

	@Override
	public void onBackPressed() {
		if (!categoryStack.isEmpty()) {
			final IngredientCategory pop = categoryStack.pop();
			if (!categoryStack.isEmpty())
				displayTopOfStack();
			else {

				new BackFromSetIngredientsDialog().show(getFragmentManager(), "backDialog1212");
				categoryStack.push(pop);
				displayTopOfStack();
			}
		}
	}

	public Ingredient getDraggedIngredient() {
		if (draggedIngredient == null)
			return null;
		return (Ingredient) draggedIngredient;
	}

	public List<Ingredient> getPossibleIngredients() {
		return possibleIngredients;
	}

	public FlowLayout getChoosenIngredients() {
		return choosenIngredients;
	}

	public FlowLayout getPossibleIngredientsLayout() {
		return possibleIngredientsLayout;
	}

	public void setDraggedIngredient(Ingredient ingredient) {
		this.draggedIngredient = ingredient;

	}
}
