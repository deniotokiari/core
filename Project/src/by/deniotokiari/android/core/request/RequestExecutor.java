package by.deniotokiari.android.core.request;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import by.deniotokiari.android.core.common.IProcessor;
import by.deniotokiari.android.core.common.ISource;
import by.deniotokiari.android.core.contract.CoreRequestContract;
import by.deniotokiari.android.core.helper.BundleHelper;
import by.deniotokiari.android.core.helper.ContentHelper;
import by.deniotokiari.android.core.utils.AppUtils;

public class RequestExecutor {

    private static final String ERROR_NULL_RESPONSE = "Null response from source: %s. Request: %s";
    private static final String ERROR_NULL_RESULT = "Null result from processor: %s. Request: %s";
    private static final String ERROR_FALSE_CACHED = "Caching is false from processor: %s. Request: %s";

    @SuppressWarnings("unchecked")
    public static void execute(Context context, Request request) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(RequestListener.KEY_REQUEST, request);

        RequestListener requestListener = request.getRequestListener();
        sendStatus(requestListener, RequestListener.STATUS.START, bundle);

        if (request.getCacheExpiration() > 0) {
            if (!isCacheExpired(context, request)) {
                ISuccess success = request.getSuccess();
                if (success != null) {
                    String sql = request.getResultSql();
                    if (sql != null) {
                        processSuccess(context, success, getCursor(context, sql, request));
                        sendStatus(requestListener, RequestListener.STATUS.DONE, bundle);
                    }
                }

                return;
            } else {
                updateCacheExpiration(context, request);
            }
        }

        ISource source = (ISource) AppUtils.get(context, request.getSource());

        Object response;

        try {
            response = source.get(context, request);
        } catch (Exception e) {
            bundle.putSerializable(RequestListener.KEY_ERROR, e);
            sendStatus(requestListener, RequestListener.STATUS.ERROR, bundle);
            return;
        }

        if (response == null) {
            bundle.putSerializable(RequestListener.KEY_ERROR, new NullPointerException(String.format(ERROR_NULL_RESPONSE, source.getClass(), request.getUri())));
            sendStatus(requestListener, RequestListener.STATUS.ERROR, bundle);
            return;
        }

        IProcessor processor = (IProcessor) AppUtils.get(context, request.getProcessor());

        Object result;

        try {
            result = processor.process(context, request, response);
        } catch (Exception e) {
            bundle.putSerializable(RequestListener.KEY_ERROR, e);
            sendStatus(requestListener, RequestListener.STATUS.ERROR, bundle);
            return;
        }

        if (result == null) {
            bundle.putSerializable(RequestListener.KEY_ERROR, new NullPointerException(String.format(ERROR_NULL_RESULT, processor.getClass(), request.getUri())));
            sendStatus(requestListener, RequestListener.STATUS.ERROR, bundle);
            return;
        }

        if (request.isIsNeedCache()) {
            boolean isCached;
            try {
                isCached = processor.cache(context, request, result);
            } catch (Exception e) {
                bundle.putSerializable(RequestListener.KEY_ERROR, e);
                sendStatus(requestListener, RequestListener.STATUS.ERROR, bundle);
                return;
            }

            if (!isCached) {
                bundle.putSerializable(RequestListener.KEY_ERROR, new NullPointerException(String.format(ERROR_FALSE_CACHED, processor.getClass(), request.getUri())));
                sendStatus(requestListener, RequestListener.STATUS.ERROR, bundle);
                return;
            }

            sendStatus(requestListener, RequestListener.STATUS.CACHED, bundle);
        }

        setResultToBundle(bundle, result);
        sendStatus(requestListener, RequestListener.STATUS.DONE, bundle);

        ISuccess success = request.getSuccess();
        if (success != null) {
            String sql = request.getResultSql();
            if (sql != null) {
                processSuccess(context, success, getCursor(context, sql, request));
            } else {
                processSuccess(context, success, result);
            }
        }
    }

    private static Cursor getCursor(Context context, String sql, Request request) {
        return ContentHelper.get(context, sql, null);
    }

    private static void updateCacheExpiration(Context context, Request request) {
        ContentValues values = new ContentValues();
        values.put(CoreRequestContract.TIME_END_CACHED, System.currentTimeMillis() + request.getCacheExpiration());

        ContentHelper.update(context, CoreRequestContract.class, values, CoreRequestContract.HASH + " = ?", new String[]{request.getHash()}, false);
    }

    private static boolean isCacheExpired(Context context, Request request) {
        ContentValues values = ContentHelper.get(context, CoreRequestContract.class, CoreRequestContract.HASH + " = ?", new String[]{request.getHash()});
        if (values == null) {
            ContentValues v = new ContentValues();
            v.put(CoreRequestContract.TIME_END_CACHED, System.currentTimeMillis() + request.getCacheExpiration());
            v.put(CoreRequestContract.HASH, request.getHash());

            ContentHelper.add(context, CoreRequestContract.class, v, false);

            return true;
        } else {
            return values.getAsLong(CoreRequestContract.TIME_END_CACHED) <= System.currentTimeMillis();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> void processSuccess(Context context, final ISuccess success, final T result) {
        new Handler(context.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                success.OnSuccess(result);
            }
        });
    }

    private static <Result> void setResultToBundle(Bundle bundle, Result result) {
        BundleHelper.put(RequestListener.KEY_RESULT, bundle, result);
    }

    private static void sendStatus(RequestListener requestListener, RequestListener.STATUS status, Bundle bundle) {
        if (requestListener != null) {
            requestListener.send(status.ordinal(), bundle);
        }
    }

}
