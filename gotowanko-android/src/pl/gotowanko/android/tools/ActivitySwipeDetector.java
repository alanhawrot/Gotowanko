package pl.gotowanko.android.tools;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

@SuppressLint("ClickableViewAccessibility")
public class ActivitySwipeDetector implements View.OnTouchListener {

	static final String logTag = "ActivitySwipeDetector";
	static final int MIN_DISTANCE = 20;
	private float downX, downY, upX, upY;

	private List<OnSwipeListener> listeners = new ArrayList<OnSwipeListener>();

	public ActivitySwipeDetector(View v) {
		Deque<View> viewStack = new ArrayDeque<View>();
		viewStack.add(v);
		while (!viewStack.isEmpty()) {
			v = viewStack.pollFirst();
			Log.i("viewName", v.getClass().getName() + " added touch listener.");
			v.setOnTouchListener(this);
			if (v instanceof ViewGroup) {
				ViewGroup vGroup = (ViewGroup) v;
				for (int i = 0; i < vGroup.getChildCount(); i++) {
					viewStack.push(vGroup.getChildAt(i));
				}
			}
			if (v instanceof ViewParent) {
				ViewParent sv = (ViewParent) v;
				sv.requestDisallowInterceptTouchEvent(true);
			}
		}
	}

	public void addOnSwipeEventListener(OnSwipeListener listener) {
		listeners.add(listener);
	}

	public void onRightToLeftSwipe() {
		for (OnSwipeListener l : listeners) {
			l.onRightToLeftSwipe();
		}
		Log.i(logTag, "RightToLeftSwipe!");

	}

	public void onLeftToRightSwipe() {
		for (OnSwipeListener l : listeners) {
			l.onLeftToRightSwipe();
		}
		Log.i(logTag, "LeftToRightSwipe!");
	}

	public void onTopToBottomSwipe() {
		for (OnSwipeListener l : listeners) {
			l.onTopToBottomSwipe();
		}
		Log.i(logTag, "onTopToBottomSwipe!");
	}

	public void onBottomToTopSwipe() {
		for (OnSwipeListener l : listeners) {
			l.onBottomToTopSwipe();
		}
		Log.i(logTag, "onBottomToTopSwipe!");
	}

	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			downX = event.getRawX();
			downY = event.getRawY();
			Log.i(logTag, String.format("down (%f, %f)", downX, downY));
			return false;
		}
		case MotionEvent.ACTION_UP: {
			upX = event.getRawX();
			upY = event.getRawY();

			Log.i(logTag, String.format("up (%f, %f)", upX, upY));
			float deltaX = downX - upX;
			float deltaY = downY - upY;
			Log.i(logTag, String.format("deltas (%f, %f)", deltaX, deltaY));
			// swipe horizontal?
			if (Math.abs(deltaX) >= Math.abs(deltaY))
			{
				if (Math.abs(deltaX) > MIN_DISTANCE) {
					// left or right
					if (deltaX < 0) {
						onLeftToRightSwipe();
						return true;
					}
					if (deltaX > 0) {
						onRightToLeftSwipe();
						return true;
					}
				}
				else {
					Log.i(logTag, "Horizontal Swipe was only " + Math.abs(deltaX) + " long, need at least " + MIN_DISTANCE);
					return false; // We don't consume the event
				}
			}
			// swipe vertical?
			else
			{
				if (Math.abs(deltaY) > MIN_DISTANCE) {
					// top or down
					if (deltaY < 0) {
						onTopToBottomSwipe();
						return false;
					}
					if (deltaY > 0) {
						onBottomToTopSwipe();
						return false;
					}
				}
				else {
					Log.i(logTag, "Vertical Swipe was only " + Math.abs(deltaX) + " long, need at least " + MIN_DISTANCE);
					return false; // We don't consume the event
				}
			}

			return false;
		}
		}
		return false;
	}

}