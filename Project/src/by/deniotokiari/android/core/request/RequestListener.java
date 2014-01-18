package by.deniotokiari.android.core.request;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public abstract class RequestListener extends ResultReceiver {

    public static final String KEY_ERROR = "key:error";
    public static final String KEY_RESULT = "key:result";
    public static final String KEY_REQUEST = "key:request";

    public static enum STATUS {

        START, CACHED, DONE, ERROR

    }

    public abstract void onDone(Bundle result, Request r);

    public RequestListener() {
        super(new Handler());
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        STATUS status = STATUS.values()[resultCode];
        Request request = (Request) resultData.getSerializable(KEY_REQUEST);

        switch (status) {
            case START:
                onStart(resultData, request);
                break;
            case CACHED:
                onCache(resultData, request);
                break;
            case DONE:
                onDone(resultData, request);
                break;
            case ERROR:
                onError((Exception) resultData.getSerializable(KEY_ERROR), request);
                break;
        }

        super.onReceiveResult(resultCode, resultData);
    }

    public void onStart(Bundle result, Request r) {

    }

    public void onCache(Bundle result, Request r) {

    }

    public void onError(Exception e, Request r) {

    }

}
