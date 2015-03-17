package pl.gotowanko.android.support;

import java.util.List;

import pl.gotowanko.android.db.entity.Ingredient;
import pl.gotowanko.android.db.entity.IngredientAmmount;
import pl.gotowanko.android.tools.PhotoUtils;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IngredientsListAdapter extends BaseAdapter {

	private List<IngredientAmmount> ingredients;

	public IngredientsListAdapter(List<IngredientAmmount> ingredients) {
		this.ingredients = ingredients;
	}

	@Override
	public int getCount() {
		return ingredients.size();
	}

	@Override
	public Object getItem(int position) {
		return ingredients.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Context context = parent.getContext();
		IngredientAmmount io = ingredients.get(position);
		LinearLayout linearLayout = new LinearLayout(context);
		// linearLayout.setLayoutParams(new
		// LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
		// LayoutParams.WRAP_CONTENT, 1f));
		linearLayout.setOrientation(LinearLayout.HORIZONTAL);
		linearLayout.setPadding(10, 10, 10, 10);
		linearLayout.setGravity(Gravity.CENTER_VERTICAL);
		linearLayout.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				ViewGroup parent = (ViewGroup) v.getParent();
				parent.removeView(v);
				return true;
			}
		});

		ImageView v = new ImageView(context);
		Ingredient ingredient = io.getIngredient();
		PhotoUtils photUtils = new PhotoUtils(context);
		v.setImageDrawable(photUtils.getDrawableByKeyOrPath(ingredient.getImage()));
		linearLayout.addView(v);
		TextView textView = new TextView(context);
		textView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
		textView.setTag(io.getAmmount());
		textView.setPadding(10, 10, 10, 10);
		textView.setText(String.valueOf(ingredient.getName() + ", " + io.getAmmount()) + " " + io.getUnit().toString());
		textView.setTextColor(Color.BLACK);
		linearLayout.addView(textView);
		return linearLayout;
	}
}
