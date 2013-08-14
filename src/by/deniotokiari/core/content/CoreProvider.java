package by.deniotokiari.core.content;

import by.deniotokiari.core.utils.ContractUtils;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

abstract public class CoreProvider extends ContentProvider {

	private CoreDataBase mDataBase;

	protected abstract Class<?> getContract();

	@Override
	public boolean onCreate() {
		mDataBase = new CoreDataBase(getContext());
		return true;
	}

	@Override
	public String getType(Uri uri) {
		return ContractUtils.getType(getContract());
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int result = mDataBase.deleteItems(getContract(), selection,
				selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return result;
	}

	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
		int inserted = mDataBase.addItems(getContract(), values);
		getContext().getContentResolver().notifyChange(uri, null);
		return inserted;
	}

	@Override
	public Uri insert(Uri uri, ContentValues value) {
		long id = mDataBase.addItem(getContract(), value);
		Uri itemUri = Uri.parse(uri + "/" + id);
		if (id > 0) {
			getContext().getContentResolver().notifyChange(itemUri, null);
		}
		return itemUri;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Cursor items = mDataBase.getItems(getContract(), selection,
				selectionArgs, sortOrder);
		items.setNotificationUri(getContext().getContentResolver(), uri);
		return items;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		return 0;
	}

	public Cursor rawQuery(Uri uri, String sql, String[] selectionArgs) {
		Cursor items = mDataBase.rawQuery(getContract(), sql, selectionArgs);
		items.setNotificationUri(getContext().getContentResolver(), uri);
		return items;
	}

}
