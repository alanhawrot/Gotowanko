package pl.gotowanko.android.listeners;

import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;

public final class OnLongClickRemoveFromParent implements OnLongClickListener {
	public OnLongClickRemoveFromParent() {
	}

	@Override
	public boolean onLongClick(View v) {
		ViewGroup parent = (ViewGroup) v.getParent();
		parent.removeView(v);
		return true;
	}
}