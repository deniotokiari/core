package by.deniotokiari.core.app;

import com.nostra13.universalimageloader.core.ImageLoader;

import by.deniotokiari.core.context.ContextHolder;
import by.deniotokiari.core.helpers.CoreHelper;
import by.deniotokiari.core.helpers.CoreHelper.IAppServiceKey;
import android.app.Application;

public abstract class CoreApplication extends Application {

	private CoreHelper mCoreHelper;

	public abstract void register();
	
	public static final class PLUGIN {
		
		public static final String UNIVERSAL_IMAGE_LOADER = "plugin:UniversalImageLoader";
		
	}

	@Override
	public void onCreate() {
		mCoreHelper = new CoreHelper();
		ContextHolder.getInstance().setContext(this);
		register();
		registerPlugins();
		super.onCreate();
	}

	@Override
	public Object getSystemService(String name) {
		Object service = mCoreHelper.getSystemService(name);
		if (service != null) {
			return service;
		} else {
			return super.getSystemService(name);
		}
	}

	public void registerService(IAppServiceKey service) {
		mCoreHelper.registerAppService(service);
	}

	private void registerPlugins() {
		registerService(new PluginWrapper(PLUGIN.UNIVERSAL_IMAGE_LOADER,
				ImageLoader.getInstance()));
	}

}
