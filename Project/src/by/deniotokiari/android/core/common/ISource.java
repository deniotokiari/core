package by.deniotokiari.android.core.common;

import android.content.Context;
import by.deniotokiari.android.core.request.Request;

public interface ISource<Response> extends IAppService {

    public Response get(Context context, Request request) throws Exception;

}
