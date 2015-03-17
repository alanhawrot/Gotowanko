package pl.gotowanko.android.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.content.Context;
import android.util.Log;

public class StorageUtils {

	private Context context;

	public StorageUtils(Context context) {
		this.context = context;
	}

	public void store(String fileName, Serializable objectToStore) {
		final File file = getFileForName(fileName);

		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(objectToStore);
		} catch (Exception e) {
			file.delete();
			Log.e("MyAppName", "failed to suspend", e);
		} finally {
			try {
				if (oos != null)
					oos.close();
			} catch (Exception e) {
			}
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
			}
		}
	}

	private File getFileForName(String fileName) {
		return new File(context.getCacheDir().getAbsoluteFile() + "/" + fileName + ".cachedObject");
	}

	@SuppressWarnings({ "resource", "unchecked" })
	public <T> T restore(String fileName, Class<T> cls) {
		final File file = getFileForName(fileName);
		FileInputStream fos = null;
		ObjectInputStream oos = null;
		try {
			fos = new FileInputStream(file);
			try {
				oos = new ObjectInputStream(fos);
				return ((T) oos.readObject());

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		} catch (FileNotFoundException e) {
			return null;
		}

	}

	public boolean remove(String fileName) {
		File file = getFileForName(fileName);
		if (file.exists())
			return file.delete();
		return false;
	}
}
