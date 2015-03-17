package pl.gotowanko.android;

import java.util.ArrayList;
import java.util.List;

import pl.gotowanko.android.base.GotowankoActivity;
import pl.gotowanko.android.base.GotowankoApplication;
import pl.gotowanko.android.db.entity.Card;
import pl.gotowanko.android.db.entity.IngredientAmmount;
import pl.gotowanko.android.db.entity.Training;
import pl.gotowanko.android.dialogfragments.ConfirmTrainingRemovalDialogFragment;
import pl.gotowanko.android.support.IngredientsListAdapter;
import pl.gotowanko.android.tools.PhotoUtils;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class TrainingActivity extends GotowankoActivity {

	public static final int MENU_DELETE_ID = GotowankoActivity.MENU_LAST_ID + 1;
	public static final int MENU_EDIT_ID = GotowankoActivity.MENU_LAST_ID + 2;
	private String trainingName;
	private Training training;
	private TextView modificationDate;
	private ListView ingredientsList;
	private ImageView image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_training);
		trainingName = getIntent().getStringExtra("TrainingName");
		if (trainingName != null)
			training = GotowankoApplication.getTrainingDAO().get(trainingName);
		if (training != null) {
			TextView title = (TextView) findViewById(R.id.titleTextView);
			title.setText(training.getName());
			modificationDate = (TextView) findViewById(R.id.modificationDate);
			modificationDate.setText(getResources().getString(R.string.last_change) + ": " + training.getModificationDate());
			image = (ImageView) findViewById(R.id.image);
			if (training.getImage() != null) {
				PhotoUtils photoUtils = new PhotoUtils(this);
				image.setImageDrawable(photoUtils.getDrawableByKeyOrPath(training.getImage()));
			}
			ingredientsList = (ListView) findViewById(R.id.ingredientsList2);
			List<IngredientAmmount> ingredients = new ArrayList<IngredientAmmount>();
			for (Card c : training.getCards()) {
				ingredients.addAll(c.getIngredients());
			}
			IngredientsListAdapter adapter = new IngredientsListAdapter(ingredients);
			ingredientsList.setAdapter(adapter);

		}
	}

	public void startTraining(View v) {
		Intent intent = new Intent(this, TrainingCardActivity.class);
		intent.putExtra("TrainingName", trainingName);
		intent.putExtra("TrainingCardNumber", 0);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(MENU_GROUP_ID, MENU_DELETE_ID, 0, getText(R.string.delete))
				.setIcon(R.drawable.delete)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		menu.add(MENU_GROUP_ID, MENU_EDIT_ID, 0, getText(R.string.edit))
				.setIcon(R.drawable.edit)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		super.onCreateOptionsMenu(menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == MENU_EDIT_ID) {
			Intent intent = new Intent(this, CreateTrainingMainActivity.class);
			intent.putExtra(CreateTrainingMainActivity.TRAINING, training);
			startActivity(intent);
			finish();
			return true;
		}
		if (item.getItemId() == MENU_DELETE_ID) {
			new ConfirmTrainingRemovalDialogFragment(trainingName)
					.show(getFragmentManager(), "ConfirmTrainingRemovalDialogFragment");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
