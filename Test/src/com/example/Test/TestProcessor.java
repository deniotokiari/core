package com.example.Test;

import android.content.ContentValues;
import android.content.Context;
import by.deniotokiari.android.core.common.IProcessor;
import by.deniotokiari.android.core.helper.ContentHelper;
import by.deniotokiari.android.core.request.Request;
import by.deniotokiari.android.core.utils.StringUtils;

public class TestProcessor implements IProcessor<String[], String> {

    public static final String KEY = "key:test_processor";

    @Override
    public String process(Context context, Request request, String[] strings) throws Exception {
        return StringUtils.join(strings, ", ");
    }

    @Override
    public boolean cache(Context context, Request request, String s) throws Exception {
        ContentValues values = new ContentValues();
        values.put(TestEntity.TITLE, "Test " + System.currentTimeMillis());
        values.put(TestEntity.DESCRIPTION, s);

        ContentHelper.add(context, TestEntity.class, values, false);

        return true;
    }

    @Override
    public String getKey() {
        return KEY;
    }

}
