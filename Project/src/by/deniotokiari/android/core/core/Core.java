package by.deniotokiari.android.core.core;

import android.content.Context;
import android.content.Intent;
import by.deniotokiari.android.core.request.Request;
import by.deniotokiari.android.core.request.RequestQueue;
import by.deniotokiari.android.core.service.CoreService;

public class Core {

    public static void execute(Context context, Request.IRequestBuilder request) {
        execute(context, new Request(request));
    }

    public static void execute(Context context, Request request) {
        boolean isEmpty = RequestQueue.isEmpty(context);
        RequestQueue.addRequest(context, request);

        if (isEmpty) {
            Intent intent = new Intent(context, CoreService.class);
            context.startService(intent);
        }
    }

}
