package pl.gotowanko.android.views;

import pl.gotowanko.android.IngredientsActivity;
import pl.gotowanko.android.R;
import pl.gotowanko.android.db.entity.Ingredient;
import pl.gotowanko.android.db.entity.IngredientCategory;
import pl.gotowanko.android.tools.PhotoUtils;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IngredientView extends LinearLayout {

	private ImageView img;
	private Ingredient ingredient;
	private TextView name;

	public IngredientView(IngredientsActivity ingredientsActivity, Ingredient i) {
		super(ingredientsActivity.getApplicationContext());
		this.ingredient = i;
		Context ctx = ingredientsActivity.getApplicationContext();
		inflate(ctx, R.layout.ingredient_view, this);
		img = (ImageView) findViewById(R.id.ingredientImage);

		PhotoUtils photUtils = new PhotoUtils(ctx);
		img.setImageDrawable(photUtils.getDrawableByKeyOrPath(i.getImage()));

		name = (TextView) findViewById(R.id.ingredientName);
		name.setText(i.getName());
		setTag(i);
		adjustLayoutProperties();
	}

	public IngredientView(IngredientsActivity ingredientsActivity, IngredientCategory ic) {
		super(ingredientsActivity.getApplicationContext());
		Context ctx = ingredientsActivity.getApplicationContext();
		inflate(ctx, R.layout.ingredient_view, this);
		img = (ImageView) findViewById(R.id.ingredientImage);

		PhotoUtils photUtils = new PhotoUtils(ctx);
		img.setImageDrawable(photUtils.getDrawableByKeyOrPath(ic.getImage()));

		TextView name = (TextView) findViewById(R.id.ingredientName);
		name.setText(ic.getName());
		setTag(ic);
		adjustLayoutProperties();
	}

	protected void adjustLayoutProperties() {
		setOrientation(LinearLayout.VERTICAL);
		setGravity(Gravity.CENTER_HORIZONTAL);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(5, 5, 5, 5);
		setLayoutParams(layoutParams);
	}

	public Drawable getIngredientDrawable() {
		return img.getDrawable();
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

}
