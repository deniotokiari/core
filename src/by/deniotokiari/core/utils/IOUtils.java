package by.deniotokiari.core.utils;

import java.io.Closeable;
import java.io.IOException;

import android.util.Log;

public class IOUtils {
	
	public static final String LOG_TAG = IOUtils.class.getSimpleName();

	public static void closeStream(Closeable stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				Log.e(LOG_TAG, "Could not close stream");
			}
		}
	}
	
}
