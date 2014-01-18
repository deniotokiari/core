package by.deniotokiari.android.core.common;

import android.content.Context;
import by.deniotokiari.android.core.request.Request;

public interface IProcessor<Response, Result> extends IAppService {

    public Result process(Context context, Request request, Response response) throws Exception;

    public boolean cache(Context context, Request request, Result result) throws Exception;

}
