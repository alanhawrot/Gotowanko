package pl.gotowanko.android.support;

import java.util.List;

import pl.gotowanko.android.R;
import pl.gotowanko.android.db.entity.IngredientAmmount;
import pl.gotowanko.android.tools.PhotoUtils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IngredientAmmountLinearLayoutAdapter {

	private List<IngredientAmmount> ingredients;

	public IngredientAmmountLinearLayoutAdapter(List<IngredientAmmount> ingredients) {
		super();
		this.ingredients = ingredients;
	}

	public void updateIngredients(LinearLayout l) {
		l.removeAllViews();
		for (IngredientAmmount io : ingredients) {
			l.addView(getView(io, l));
		}
	}

	private View getView(IngredientAmmount ingredient, ViewGroup parent) {
		Context context = parent.getContext();
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.ingredient_ammount_list_element, parent, false);
		view.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				ViewGroup parent = (ViewGroup) v.getParent();
				parent.removeView(v);
				return true;
			}
		});

		view.setTag(ingredient);

		TextView ingredientName = (TextView) view.findViewById(R.id.ingredientName);
		ingredientName.setText(ingredient.getIngredient().getName());

		TextView ingredientAmmount = (TextView) view.findViewById(R.id.ingredientAmmount);
		ingredientAmmount.setText(String.valueOf(ingredient.getAmmount()));

		TextView ingredientUnit = (TextView) view.findViewById(R.id.ingredientUnit);
		ingredientUnit.setText(ingredient.getUnit().toString());

		ImageView ingredientImage = (ImageView) view.findViewById(R.id.ingredientImage);

		PhotoUtils photUtils = new PhotoUtils(context);
		ingredientImage.setImageDrawable(photUtils.getDrawableByKeyOrPath(ingredient.getIngredient().getImage()));

		return view;
	}
}
