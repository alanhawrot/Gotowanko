package pl.gotowanko.android.dialogfragments;

import pl.gotowanko.android.R;
import pl.gotowanko.android.TrainingActivity;
import pl.gotowanko.android.base.GotowankoApplication;
import pl.gotowanko.android.db.dao.TrainingDAO;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

public class ConfirmTrainingRemovalDialogFragment extends DialogFragment {
	private String trainingName;

	public ConfirmTrainingRemovalDialogFragment(String trainingName) {
		this.trainingName = trainingName;
	}

	public ConfirmTrainingRemovalDialogFragment() {
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("trainingName", trainingName);
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			trainingName = savedInstanceState.getString("trainingName");
		}
	};

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final TrainingActivity ta = (TrainingActivity) getActivity();
		return new AlertDialog.Builder(ta)
				.setTitle(R.string.recipe_deletation)
				.setIcon(R.drawable.warning)
				.setMessage(R.string.do_you_realy_want_remove_recipe)
				.setPositiveButton(R.string.yes, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						TrainingDAO trainingDAO = GotowankoApplication.getTrainingDAO();
						trainingDAO.delete(trainingDAO.get(trainingName));
						ta.finish();
					}
				})
				.setNeutralButton(R.string.cancel, null)
				.create();
	}
}