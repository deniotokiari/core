package by.deniotokiari.core.content;

import by.deniotokiari.core.utils.ContractUtils;
import by.deniotokiari.core.utils.DBUtils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

abstract public class CoreDataBase extends SQLiteOpenHelper {

	private static final String DB_NAME = "core.store.db";
	private static final int DB_VERSION = 1;
	private static final Object DB_OBJECT_LOCK = new Object();

	private Context mContext;
	private SQLiteDatabase mDatabase;
	private boolean isInTransaction = false;

	abstract public Class<?>[] getDeclaringContracts();

	public CoreDataBase(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		syncTransaction();
		try {
			setInTransaction(true);
			db.beginTransaction();
			Class<?>[] contracts = getDeclaringContracts();
			for (Class<?> contract : contracts) {
				ContractUtils.checkContractClass(contract);
				Class<?>[] tables = contract.getClasses();
				for (Class<?> table : tables) {
					db.execSQL(DBUtils.getCreateTableString(table));
				}
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
			setInTransaction(false);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion < newVersion) {
			deleteDataBase();
			db.setVersion(newVersion);
		}
		onCreate(mDatabase);
	}

	protected void deleteDataBase() {
		syncTransaction();
		try {
			setInTransaction(true);
			mContext.deleteDatabase(DB_NAME);
		} finally {
			setInTransaction(false);
		}
	}

	private void syncTransaction() {
		while (isInTransaction) {
			waitWhileTransaction();
		}
	}

	private void setInTransaction(boolean flag) {
		synchronized (DB_OBJECT_LOCK) {
			isInTransaction = flag;
			if (!isInTransaction) {
				DB_OBJECT_LOCK.notifyAll();
			}
		}
	}

	private static void waitWhileTransaction() {
		synchronized (DB_OBJECT_LOCK) {
			try {
				DB_OBJECT_LOCK.wait();
			} catch (InterruptedException e) {

			}
		}
	}

	/** Add and Get item(s) **/

	protected long addItem(Class<?> contract, ContentValues value)
			throws SQLException {
		syncTransaction();
		mDatabase = getWritableDatabase();
		long added;
		try {
			setInTransaction(true);
			mDatabase.beginTransaction();
			added = mDatabase.insertWithOnConflict(
					ContractUtils.getTableName(contract), null, value,
					SQLiteDatabase.CONFLICT_REPLACE);
			if (added <= 0) {
				throw new SQLException("Failed to insert row into "
						+ ContractUtils.getTableName(contract));
			}
			mDatabase.setTransactionSuccessful();
		} finally {
			mDatabase.endTransaction();
			setInTransaction(false);
		}
		return added;
	}

	protected int addItems(Class<?> contract, ContentValues[] values)
			throws SQLException {
		syncTransaction();
		mDatabase = getWritableDatabase();
		long added;
		int inserted = 0;
		try {
			setInTransaction(true);
			mDatabase.beginTransaction();
			for (ContentValues value : values) {
				added = mDatabase.insertWithOnConflict(
						ContractUtils.getTableName(contract), null, value,
						SQLiteDatabase.CONFLICT_REPLACE);
				if (added <= 0) {
					throw new SQLException("Failed to insert row into "
							+ ContractUtils.getTableName(contract));
				} else {
					inserted++;
				}
			}
			mDatabase.setTransactionSuccessful();
		} finally {
			mDatabase.endTransaction();
			setInTransaction(false);
		}
		return inserted;
	}

	/*protected Cursor getItems(Class<?> contract, String selection, String[] selectionArgs, String orderBy) {
		syncTransaction();
		mDatabase = getWritableDatabase();
		
	}*/
	
}
