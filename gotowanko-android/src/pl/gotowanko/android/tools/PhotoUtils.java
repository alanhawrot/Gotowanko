package pl.gotowanko.android.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

@SuppressLint("SimpleDateFormat")
public class PhotoUtils {
	private Context context;

	public PhotoUtils(Context context) {
		this.context = context;
	}

	public Drawable getDrawableByKeyOrPath(String imagePath) {
		if (imagePath.endsWith(".png")) {
			Bitmap imageBitmap = BitmapFactory.decodeFile(imagePath);
			BitmapDrawable drawable = new BitmapDrawable(context.getResources(), imageBitmap);
			return drawable;
		}
		return context.getResources().getDrawable(Integer.valueOf(imagePath));
	}

	public String storePhoto(Bitmap photo) {
		String imagePath;
		FileOutputStream out = null;
		try { // Environment.getExternalStorageDirectory()
			String currentDate = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			imagePath = String.format("%s/images/%s.png", context.getFilesDir(), currentDate);
			new File(String.format("%s/images", context.getFilesDir())).mkdir();
			File file = new File(imagePath);
			if (!file.createNewFile()) {
				Log.i("imgFile", "file couldn't be created");
			}
			out = new FileOutputStream(file);
			photo.compress(Bitmap.CompressFormat.PNG, 100, out);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return imagePath;
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}

		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);

		return bitmap;
	}

}
