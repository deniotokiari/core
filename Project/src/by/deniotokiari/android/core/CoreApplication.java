package by.deniotokiari.android.core;

import android.app.Application;
import by.deniotokiari.android.core.common.IAppService;
import by.deniotokiari.android.core.context.ContextHolder;
import by.deniotokiari.android.core.helper.CoreHelper;
import by.deniotokiari.android.core.request.RequestQueue;

public abstract class CoreApplication extends Application {

    private volatile Object mLock = new Object();

    private CoreHelper mCoreHelper;

    public abstract void registerServices();

    @Override
    public void onCreate() {
        synchronized (mLock) {
            ContextHolder.set(this);
            mCoreHelper = new CoreHelper();

            registerService(new RequestQueue());

            registerServices();

            super.onCreate();
        }
    }

    public void registerService(IAppService service) {
        mCoreHelper.register(service);
    }

    @Override
    public Object getSystemService(String name) {
        synchronized (mLock) {
            if (mCoreHelper == null) {
                onCreate();
            }
        }
        Object service = mCoreHelper.get(name);
        if (service != null) {
            return service;
        } else {
            return super.getSystemService(name);
        }
    }

}
