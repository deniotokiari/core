package by.deniotokiari.core.content;

import android.content.Context;
import by.deniotokiari.core.content.db_implementation.SQLite;
import by.deniotokiari.core.helpers.UriHelper;
import by.deniotokiari.core.utils.ContractUtils;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.WeakHashMap;

abstract public class CoreProvider extends ContentProvider {

    private IDataBase<Cursor> mDataBase;
    private Class<?>[] mContracts;

    private WeakHashMap<Uri, Class<?>> mContractsHash;

    protected IDataBase<Cursor> getDataBase(Context context) {
        return new SQLite(context);
    }

    protected abstract Class<Cursor>[] getContracts();

    private Class<?> getContract(Uri uri) {
        Uri bufUri = UriHelper.getUriWithoutKeys(uri);
        if (mContractsHash.containsKey(bufUri)) {
            return mContractsHash.get(bufUri);
        } else {
            Class<?> aClass = ContractUtils.getContractByUri(bufUri, mContracts);
            if (aClass != null) {
                mContractsHash.put(bufUri, aClass);
                return aClass;
            } else {
                return null;
            }
        }
    }

    @Override
    public boolean onCreate() {
        mDataBase = getDataBase(getContext());
        mContracts = getContracts();
        mContractsHash = new WeakHashMap<Uri, Class<?>>();
        return true;
    }

    @Override
    public String getType(Uri uri) {
        return ContractUtils.getType(getContract(uri));
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int result = mDataBase.deleteItems(getContract(uri), selection,
                selectionArgs);
        if (UriHelper.isHasKey(uri, UriHelper.KEY_NOTIFICATION_URI)) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return result;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        int inserted = mDataBase.addItems(getContract(uri), values);
        if (UriHelper.isHasKey(uri, UriHelper.KEY_NOTIFICATION_URI)) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return inserted;
    }

    @Override
    public Uri insert(Uri uri, ContentValues value) {
        long id = mDataBase.addItem(getContract(uri), value);
        Uri itemUri = Uri.parse(uri + "/" + id);
        if (id > 0) {
            if (UriHelper.isHasKey(uri, UriHelper.KEY_NOTIFICATION_URI)) {
                getContext().getContentResolver().notifyChange(itemUri, null);
            }
        }
        return itemUri;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor items = mDataBase.getItems(getContract(uri), selection,
                selectionArgs, sortOrder);
        if (UriHelper.isHasKey(uri, UriHelper.KEY_NOTIFICATION_URI)) {
            items.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return items;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }

    public Cursor rawQuery(Uri uri, String sql, String[] selectionArgs) {
        Cursor items = mDataBase.rawQuery(getContract(uri), sql, selectionArgs);
        if (UriHelper.isHasKey(uri, UriHelper.KEY_NOTIFICATION_URI)) {
            items.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return items;
    }

}
