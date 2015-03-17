package pl.gotowanko.android.dialogfragments;

import pl.gotowanko.android.CreateTrainingBaseActivity;
import pl.gotowanko.android.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

public class WorkNotChangedFragmentDialog extends DialogFragment {

	private int itemId = -1;

	public WorkNotChangedFragmentDialog(int itemId) {
		this.itemId = itemId;

	}

	public WorkNotChangedFragmentDialog() {
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("itemId", itemId);
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null)
			itemId = savedInstanceState.getInt("itemId");
	};

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final CreateTrainingBaseActivity activity = (CreateTrainingBaseActivity) getActivity();
		return new AlertDialog.Builder(activity)
				.setPositiveButton(R.string.yes, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (activity.saveTraining(activity.getTraining())) {
							activity.finish();
							if (itemId != -1)
								activity.menuAction(itemId);
						}
					}
				})
				.setNegativeButton(R.string.no, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						activity.finish();
						if (itemId != -1)
							activity.menuAction(itemId);
					}
				})
				.setNeutralButton(R.string.cancel, null)
				.setIcon(R.drawable.warning)
				.setTitle(R.string.recipe_not_saved)
				.setMessage(R.string.do_you_wish_to_save)
				.setCancelable(true)
				.create();
	}
}
