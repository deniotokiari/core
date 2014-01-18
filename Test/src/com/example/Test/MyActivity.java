package com.example.Test;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteQuery;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.TextView;
import by.deniotokiari.android.core.core.Core;
import by.deniotokiari.android.core.helper.ContractHelper;
import by.deniotokiari.android.core.helper.L;
import by.deniotokiari.android.core.helper.UriHelper;
import by.deniotokiari.android.core.request.ISuccess;
import by.deniotokiari.android.core.request.Request;
import by.deniotokiari.android.core.request.RequestListener;
import by.deniotokiari.android.core.utils.SQLiteQueryBuilder;

public class MyActivity extends Activity implements ISuccess<Cursor> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Request request = new Request.Builder()
                .setProcessor(TestProcessor.KEY)
                .setSource(TestSource.KEY)
                .setIsNeedCache(true)
                .setRequestListener(new RequestListener() {
                    @Override
                    public void onStart(Bundle result, Request r) {

                    }

                    @Override
                    public void onDone(Bundle result, Request r) {

                    }
                })
                .setCacheExpiration(DateUtils.HOUR_IN_MILLIS)
                .setResultSql("SELECT * FROM " + ContractHelper.getTableName(TestEntity.class), null)
                .setSuccess(this)
                .build();

        Core.execute(this, request);
    }

    @Override
    public void OnSuccess(Cursor result) {
        int a = 4;
    }

}
