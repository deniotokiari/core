package by.deniotokiari.android.core.service;

import android.app.IntentService;
import android.content.Intent;
import by.deniotokiari.android.core.request.Request;
import by.deniotokiari.android.core.request.RequestExecutor;
import by.deniotokiari.android.core.request.RequestQueue;

public class CoreService extends IntentService {

    public CoreService() {
        super(CoreService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Request request = RequestQueue.nextRequest(this);
        while (request != null) {
            RequestExecutor.execute(this, request);
            request = RequestQueue.nextRequest(this);
        }
    }

}
