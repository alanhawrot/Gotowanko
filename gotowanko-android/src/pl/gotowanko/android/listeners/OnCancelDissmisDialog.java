package pl.gotowanko.android.listeners;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

public final class OnCancelDissmisDialog implements OnCancelListener {
	@Override
	public void onCancel(DialogInterface dialog) {
		dialog.dismiss();
	}
}