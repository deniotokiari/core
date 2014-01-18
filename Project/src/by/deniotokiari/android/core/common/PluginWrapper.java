package by.deniotokiari.android.core.common;

public class PluginWrapper implements IAppService {

    private String mKey;
    private Object mPlugin;

    public PluginWrapper(String key, Object plugin) {
        mKey = key;
        mPlugin = plugin;
    }

    public Object get() {
        return mPlugin;
    }

    @Override
    public String getKey() {
        return mKey;
    }

}
