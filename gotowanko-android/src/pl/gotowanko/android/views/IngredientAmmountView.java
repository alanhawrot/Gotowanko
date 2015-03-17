package pl.gotowanko.android.views;

import pl.gotowanko.android.R;
import pl.gotowanko.android.db.entity.IngredientAmmount;
import pl.gotowanko.android.tools.PhotoUtils;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IngredientAmmountView extends LinearLayout {

	private Context context;
	private IngredientAmmount ia;
	private ImageView ingredientImage;
	private TextView ingredientUnit;
	private TextView ingredientAmmount;
	private TextView ingredientName;

	public IngredientAmmountView(Context context, IngredientAmmount ia) {
		super(context);
		inflate(context, R.layout.ingredient_ammount_view, this);
		this.context = context;
		this.ia = ia;
		setTag(ia);

		ingredientImage = (ImageView) findViewById(R.id.ingredientImage);
		ingredientImage.setImageDrawable(getIngredientDrawable());

		ingredientName = (TextView) findViewById(R.id.ingredientName);
		ingredientName.setText(ia.getIngredient().getName());

		ingredientUnit = (TextView) findViewById(R.id.ingredientUnit);
		ingredientUnit.setText(ia.getUnit().toString());

		ingredientAmmount = (TextView) findViewById(R.id.ingredientAmmount);
		ingredientAmmount.setText(String.valueOf(ia.getAmmount()));

		setOrientation(LinearLayout.VERTICAL);
		setGravity(Gravity.CENTER_HORIZONTAL);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(5, 5, 5, 5);
		setLayoutParams(layoutParams);

	}

	protected Drawable getIngredientDrawable() {

		PhotoUtils photUtils = new PhotoUtils(context);
		return photUtils.getDrawableByKeyOrPath(ia.getIngredient().getImage());
	}
}
