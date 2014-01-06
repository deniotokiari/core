package by.deniotokiari.android.core.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import by.deniotokiari.android.core.db.IDataBase;
import by.deniotokiari.android.core.db.impl.SQLite;
import by.deniotokiari.android.core.helper.ContractHelper;
import by.deniotokiari.android.core.helper.UriHelper;

import java.util.Collection;

public abstract class CoreProvider extends ContentProvider {

    private IDataBase mDataBase;
    private Collection<Class<?>> mContracts;

    public abstract Collection<Class<?>> getContracts();

    // for default init in SQLite db
    public IDataBase getDataBase(Context context) {
        return new SQLite(context);
    }

    public Class<?> getContract(Uri uri) {
        Uri localUri = UriHelper.getUriWithoutQuery(uri);
        Uri currentUri;

        for (Class<?> contract : mContracts) {
            currentUri = ContractHelper.getUri(contract);
            if (localUri.equals(currentUri)) {
                return contract;
            }
        }

        return null;
    }

    @Override
    public boolean onCreate() {
        mDataBase = getDataBase(getContext());
        mContracts = getContracts();
        return true;
    }

    @Override
    public String getType(Uri uri) {
        return ContractHelper.getType(getContract(uri));
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor result = mDataBase.query(getContract(uri), projection, selection, selectionArgs, sortOrder);
        setNotificationUri(uri, result);
        return result;
    }

    public Cursor rawQuery(Uri uri, String sql, String[] selectionArgs) {
        Cursor result = mDataBase.rawQuery(getContract(uri), sql, selectionArgs);
        setNotificationUri(uri, result);
        return result;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = mDataBase.insert(getContract(uri), values);
        Uri itemUri = Uri.parse(UriHelper.getUriWithoutQuery(uri) + "/" + id);
        if (id > 0) {
            notifyChange(uri);
        }
        return itemUri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        int inserted = mDataBase.bulkInsert(getContract(uri), values);
        if (inserted > 0) {
            notifyChange(uri);
        }
        return inserted;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int id = mDataBase.delete(getContract(uri), selection, selectionArgs);
        if (id > 0) {
            notifyChange(uri);
        }
        return id;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = mDataBase.update(getContract(uri), values, selection, selectionArgs);
        if (count > 0) {
            notifyChange(uri);
        }
        return count;
    }

    public void notifyChange(Uri uri) {
        if (isNotify(uri)) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
    }

    public void setNotificationUri(Uri uri, Cursor cursor) {
        if (isNotify(uri)) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
    }

    public boolean isNotify(Uri uri) {
        return UriHelper.isNotificationUri(uri);
    }

}
