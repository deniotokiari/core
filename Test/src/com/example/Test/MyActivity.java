package com.example.Test;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteQuery;
import android.net.Uri;
import android.os.Bundle;
import by.deniotokiari.android.core.helper.ContractHelper;
import by.deniotokiari.android.core.helper.UriHelper;
import by.deniotokiari.android.core.utils.SQLiteQueryBuilder;

public class MyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        String sql = builder
                .select("*")
                .from(ContractHelper.getTableName(TestEntity.class))
                .where(TestEntity.TITLE + " = ?", "LOOL")
                .and()
                .where(TestEntity.DESCRIPTION + " <> ?", "TROOL")
                .build();

        Uri uri = UriHelper.getUriWithSql(TestEntity.class, sql);

        Cursor cursor = getContentResolver().query(UriHelper.getUriWithSql(TestEntity.class, sql), null, null, null, null);
        cursor.close();
    }

}
