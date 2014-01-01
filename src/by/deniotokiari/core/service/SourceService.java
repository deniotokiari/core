package by.deniotokiari.core.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.ResultReceiver;

public class SourceService extends IntentService {

    public static final String SERVICE_NAME = SourceService.class
            .getSimpleName();
    public static final String KEY_RESULT_RECEIVER = "resultReceiver";

    public SourceService() {
        super(SERVICE_NAME);
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected void onHandleIntent(Intent intent) {
        Request_DEPRICATED request = new Request_DEPRICATED(intent);
        ResultReceiver receiver = intent
                .getParcelableExtra(KEY_RESULT_RECEIVER);
        request.executeRequest(getApplicationContext(), receiver);
    }

    public static void execute(Context context, Request_DEPRICATED<?, ?, ?> request) {
        execute(context, request, null);
    }

    public static void execute(Context context, Request_DEPRICATED<?, ?, ?> request,
                               ResultReceiver receiver) {
        Intent intent = new Intent(context, SourceService.class);
        request.setBundleToIntent(intent);
        intent.putExtra(KEY_RESULT_RECEIVER, receiver);
        context.startService(intent);
    }

}
