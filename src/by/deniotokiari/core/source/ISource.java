package by.deniotokiari.core.source;

import by.deniotokiari.core.helpers.CoreHelper.IAppServiceKey;

public interface ISource<Source> extends IAppServiceKey {

	Source getSource(String uri) throws Exception;
	
}
