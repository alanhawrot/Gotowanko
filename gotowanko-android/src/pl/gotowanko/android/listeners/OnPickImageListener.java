package pl.gotowanko.android.listeners;

import pl.gotowanko.android.CreateTrainingMainActivity;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;

public final class OnPickImageListener implements OnClickListener {
	private final CreateTrainingMainActivity createTrainingMainActivity;

	public OnPickImageListener(CreateTrainingMainActivity createTrainingMainActivity) {
		this.createTrainingMainActivity = createTrainingMainActivity;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 128);
		intent.putExtra("outputY", 128);
		intent.putExtra("scale", true);
		this.createTrainingMainActivity.startActivityForResult(intent, CreateTrainingMainActivity.CAMERA_REQUEST);
	}
}