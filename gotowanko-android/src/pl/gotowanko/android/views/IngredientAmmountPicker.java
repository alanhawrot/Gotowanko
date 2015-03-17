package pl.gotowanko.android.views;

import java.lang.reflect.Field;

import pl.gotowanko.android.IngredientsActivity;
import pl.gotowanko.android.R;
import pl.gotowanko.android.db.entity.Ingredient;
import pl.gotowanko.android.tools.PhotoUtils;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

public class IngredientAmmountPicker extends Dialog {

	private ImageView ingredientImageView;
	private NumberPicker picker;
	private TextView unitTextView;
	private IngredientsActivity ingredientsActivity;
	private Ingredient ingredient;
	private Button okButton;
	private TextView ingredientName;

	public IngredientAmmountPicker(final IngredientsActivity ingredientsActivity, final Ingredient ingredient) {
		super(ingredientsActivity);
		this.ingredientsActivity = ingredientsActivity;
		this.ingredient = ingredient;
		setContentView(R.layout.ingredient_ammount_picker_dialog);
		setCancelable(true);
		setTitle(R.string.choose_quantity);

		ingredientImageView = (ImageView) findViewById(R.id.ingredientImageView);
		ingredientImageView.setImageDrawable(getIngredientDrawable());
		ingredientName = (TextView) findViewById(R.id.ingredientName);
		ingredientName.setText(ingredient.getName());
		unitTextView = (TextView) findViewById(R.id.unit);
		unitTextView.setText(ingredient.getDefaultUnit().toString());
		picker = (NumberPicker) findViewById(R.id.numberPicker);
		picker.setMinValue(0);
		picker.setValue(1);
		picker.setMaxValue(20);
		picker.setWrapSelectorWheel(true);
		picker.setBackgroundColor(Color.LTGRAY);
		picker.setEnabled(true);
		setNumberPickerTextColor(picker, Color.BLACK);

		okButton = (Button) findViewById(R.id.okButton);

	}

	public void setOnOkButtonListener(View.OnClickListener okButtonListener) {
		okButton.setOnClickListener(okButtonListener);
	}

	protected Drawable getIngredientDrawable() {
		PhotoUtils photUtils = new PhotoUtils(ingredientsActivity);
		return photUtils.getDrawableByKeyOrPath(ingredient.getImage());
	}

	public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color) {
		final int count = numberPicker.getChildCount();
		for (int i = 0; i < count; i++) {
			View child = numberPicker.getChildAt(i);
			if (child instanceof EditText) {
				try {
					Field selectorWheelPaintField = numberPicker.getClass().getDeclaredField("mSelectorWheelPaint");
					selectorWheelPaintField.setAccessible(true);
					((Paint) selectorWheelPaintField.get(numberPicker)).setColor(color);
					((EditText) child).setTextColor(color);
					numberPicker.invalidate();
					return true;
				} catch (NoSuchFieldException e) {
					Log.w("setNumberPickerTextColor", e);
				} catch (IllegalAccessException e) {
					Log.w("setNumberPickerTextColor", e);
				} catch (IllegalArgumentException e) {
					Log.w("setNumberPickerTextColor", e);
				}
			}
		}
		return false;
	}

	public int getAmmount() {
		return picker.getValue();
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setAmmount(int ammount) {
		picker.setValue(ammount);

	}

}
