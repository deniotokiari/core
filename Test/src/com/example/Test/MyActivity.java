package com.example.Test;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import by.deniotokiari.android.core.helper.ContractHelper;

public class MyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ContentValues values = new ContentValues();
        getContentResolver().insert(ContractHelper.getUri(TestEntity.class), values);

        Cursor cursor = getContentResolver().query(ContractHelper.getUri(TestEntity.class), null, null, null, null);

        cursor.close();
    }

}
