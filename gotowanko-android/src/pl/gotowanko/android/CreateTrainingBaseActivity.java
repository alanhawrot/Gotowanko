package pl.gotowanko.android;

import pl.gotowanko.android.base.GotowankoActivity;
import pl.gotowanko.android.base.GotowankoApplication;
import pl.gotowanko.android.db.entity.Training;
import pl.gotowanko.android.dialogfragments.NoCardsWhenSavingTrainingDialogFragment;
import pl.gotowanko.android.dialogfragments.TitleNotSetWhenSavingTrainingFragmentDialog;
import pl.gotowanko.android.dialogfragments.WorkNotChangedFragmentDialog;
import android.view.Menu;
import android.view.MenuItem;

public abstract class CreateTrainingBaseActivity extends GotowankoActivity {

	public static final int MENU_SAVE_ID = GotowankoActivity.MENU_LAST_ID + 1;

	public abstract Training getTraining();
	
	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {

		if (item.getItemId() == MENU_SAVE_ID) {
			if (saveTraining(getTraining())) {
				finish();
			}
			return true;
		}
		new WorkNotChangedFragmentDialog(item.getItemId())
				.show(getFragmentManager(), "WorkNotSavedFragmentDialog");
		return true;
	}

	protected boolean notBlank(Training training) {
		return training.getName() != null && !training.getName().trim().isEmpty();
	}

	@Override
	public void onBackPressed() {
		new WorkNotChangedFragmentDialog()
				.show(getFragmentManager(), "WorkNotChangedFragmentDialog2");
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(MENU_GROUP_ID, MENU_SAVE_ID, 0, getText(R.string.save))
				.setIcon(R.drawable.accept)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean saveTraining(Training t) {
		if (t != null) {
			if (t.getName() == null || t.getName().trim().isEmpty()) {
				new TitleNotSetWhenSavingTrainingFragmentDialog()
						.show(getFragmentManager(), "TitleNotSetWhenSavingTrainingFragmentDialog");
				return false;
			} else if (t.getCards(false).isEmpty()) {
				new NoCardsWhenSavingTrainingDialogFragment()
						.show(getFragmentManager(), "NoCardsWhenSavingTrainingDialogFragment");
				return false;
			}
			if (t.getId() == null)
				GotowankoApplication.getTrainingDAO().insert(t);
			else
				GotowankoApplication.getTrainingDAO().update(t);
			return true;
		}
		return false;

	}

}
