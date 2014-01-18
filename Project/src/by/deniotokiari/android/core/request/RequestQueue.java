package by.deniotokiari.android.core.request;

import android.content.Context;
import by.deniotokiari.android.core.common.IAppService;
import by.deniotokiari.android.core.utils.AppUtils;
import by.deniotokiari.android.core.utils.HashUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RequestQueue implements IAppService {

    public static final String KEY = "service:request_queue";

    private Map<String, Request> mRequestMap;
    private Stack<Request> mStack;


    public RequestQueue() {
        mRequestMap = new ConcurrentHashMap<String, Request>();
        mStack = new Stack<Request>();
    }

    private void add(Request request) {
        String hash = request.getHash();
        if (!mRequestMap.containsKey(hash)) {
            mRequestMap.put(hash, request);
            mStack.push(request);
        }
    }

    private void remove(Request request) {
        String hash = request.getHash();
        mRequestMap.remove(hash);
        if (mStack.contains(request)) {
            mStack.remove(request);
        }
    }

    private Request next() {
        if (mStack.isEmpty()) {
            return null;
        }
        Request request = mStack.pop();
        remove(request);
        return request;
    }

    private boolean isEmpty() {
        return mStack.isEmpty();
    }

    @Override
    public String getKey() {
        return KEY;
    }

    public static boolean isEmpty(Context context) {
        return ((RequestQueue)AppUtils.get(context, KEY)).isEmpty();
    }

    public static void addRequest(Context context, Request request) {
        ((RequestQueue)AppUtils.get(context, KEY)).add(request);
    }

    public static void removeRequest(Context context, Request request) {
        ((RequestQueue) AppUtils.get(context, KEY)).remove(request);
    }

    public static Request nextRequest(Context context) {
        return ((RequestQueue) AppUtils.get(context, KEY)).next();
    }

}
