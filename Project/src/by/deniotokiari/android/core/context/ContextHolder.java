package by.deniotokiari.android.core.context;

import android.content.Context;

public class ContextHolder {

    private static volatile ContextHolder instance;

    private static ContextHolder getInstance() {
        ContextHolder localInstance = instance;
        if (localInstance == null) {
            synchronized (ContextHolder.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ContextHolder();
                }
            }
        }
        return localInstance;
    }

    public static void set(Context context) {
        getInstance().mContext = context;
    }

    public static Context get() {
        return getInstance().mContext;
    }

    private Context mContext;


}
