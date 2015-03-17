package pl.gotowanko.android.base;

import java.util.ArrayDeque;
import java.util.Deque;

import pl.gotowanko.android.CreateTrainingMainActivity;
import pl.gotowanko.android.MainActivity;
import pl.gotowanko.android.R;
import pl.gotowanko.android.views.IngredientView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class GotowankoActivity extends Activity {

	public static final int MENU_GROUP_ID = 1;
	public static final int MENU_SEARCH_ID = Menu.FIRST;
	public static final int MENU_ADD_TRAINING_ID = Menu.FIRST + 1;
	public static final int MENU_LAST_ID = MENU_ADD_TRAINING_ID;

	private void startCreateTrainingActivity() {
		Intent intent = new Intent(this, CreateTrainingMainActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	@Override
	protected void onStart() {
		super.onStart();
		Deque<View> viewStack = new ArrayDeque<View>();
		viewStack.add(getWindow().getDecorView().findViewById(android.R.id.content)); // root
																						// view
		while (!viewStack.isEmpty()) { 
			View v = viewStack.pollFirst();
			if (v instanceof ViewGroup && !(v instanceof IngredientView)) {
				ViewGroup vGroup = (ViewGroup) v;
				for (int i = 0; i < vGroup.getChildCount(); i++) {
					viewStack.push(vGroup.getChildAt(i));
				}
			}
			if (v instanceof EditText) {
				Log.i("editText", v.getClass().getName() + " added onFocusChangeListener.");
				v.setOnFocusChangeListener(new OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (!hasFocus) {
							InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
							inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
						}
					}
				});
			}
			if (!v.isClickable() && !v.isFocusable() && !v.isFocusableInTouchMode() && !(v instanceof IngredientView)) {
				v.setClickable(true);
				v.setFocusableInTouchMode(true);
			}
		}

	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(MENU_GROUP_ID, MENU_ADD_TRAINING_ID, 0, getText(R.string.add_training))
				.setIcon(R.drawable.add)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		if (!(this instanceof MainActivity))
			menu.add(MENU_GROUP_ID, MENU_SEARCH_ID, 0, getText(R.string.search))
					.setIcon(R.drawable.search)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		menuAction(id);
		return super.onOptionsItemSelected(item);
	}

	public void menuAction(int id) {
		if (!(this instanceof MainActivity))
			finish();
		if (id == MENU_ADD_TRAINING_ID) {
			startCreateTrainingActivity();
		}
	}

	public GotowankoApplication getGotowankoApplcation() {
		return (GotowankoApplication) getApplication();
	}

}
