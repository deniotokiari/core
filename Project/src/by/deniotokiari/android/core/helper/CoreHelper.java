package by.deniotokiari.android.core.helper;

import by.deniotokiari.android.core.common.IAppService;
import by.deniotokiari.android.core.common.PluginWrapper;

import java.util.HashMap;
import java.util.Map;

public class CoreHelper implements IAppService {

    public static final String KEY = "core:service:core_helper";

    private Map<String, IAppService> mServices = new HashMap<String, IAppService>();

    public CoreHelper() {
        mServices.put(KEY, this);
    }

    public Object get(String key) {
        if (mServices.containsKey(key)) {
            Object o = mServices.get(key);
            if (o instanceof PluginWrapper) {
                return ((PluginWrapper) o).get();
            } else {
                return o;
            }
        } else {
            return null;
        }
    }

    public void register(IAppService service) {
        if (!mServices.containsKey(service.getKey())) {
            mServices.put(service.getKey(), service);
        }
    }

    @Override
    public String getKey() {
        return KEY;
    }

}
