package pl.gotowanko.android.dialogfragments;

import pl.gotowanko.android.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class TitleNotSetWhenSavingTrainingFragmentDialog extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new AlertDialog.Builder(getActivity())
				.setTitle(R.string.title_not_set)
				.setIcon(R.drawable.info)
				.setMessage(R.string.training_need_title)
				.setPositiveButton(R.string.ok, null)
				.create();
	}
}
