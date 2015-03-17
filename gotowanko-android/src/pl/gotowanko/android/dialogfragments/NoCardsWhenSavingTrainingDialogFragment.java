package pl.gotowanko.android.dialogfragments;

import pl.gotowanko.android.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class NoCardsWhenSavingTrainingDialogFragment extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new AlertDialog.Builder(getActivity())
				.setTitle(R.string.cards_missing)
				.setIcon(R.drawable.info)
				.setMessage(R.string.atleast_one_card_needed)
				.setPositiveButton(R.string.ok, null)
				.create();
	}
}
