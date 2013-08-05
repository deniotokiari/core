package by.deniotokiari.core.helpers;

import by.deniotokiari.core.task.CommonAsynkTask;
import by.deniotokiari.core.task.callback.ParamCallback;
import android.content.Context;
import android.widget.Toast;

public class ToastHelper {

	public static void show(Context context, String text, int duration) {
		Toast.makeText(context, text, duration).show();
	}

	public static void showFromThread(final Context context, final String text,
			final int duration) {
		new CommonAsynkTask<Object, Object>(
				new ParamCallback<Object, Object>() {

					@Override
					public void onError(Exception e) {

					}

					@Override
					public void onSuccess(Object result) {
						show(context, text, duration);
					}

					@Override
					public Object onProcess(Object... arg) {
						// nothing to do
						return null;
					}

				}).start();
	}

}
