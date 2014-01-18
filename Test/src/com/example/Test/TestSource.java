package com.example.Test;

import android.content.Context;
import by.deniotokiari.android.core.common.ISource;
import by.deniotokiari.android.core.request.Request;

public class TestSource implements ISource<String[]> {

    public static final String KEY = "source:rest_source";

    @Override
    public String[] get(Context context, Request request) throws Exception {
        return new String[] {"1", "2", "3", "4", "5", "6"};
    }

    @Override
    public String getKey() {
        return KEY;
    }

}
