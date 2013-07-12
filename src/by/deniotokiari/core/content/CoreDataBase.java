package by.deniotokiari.core.content;

import by.deniotokiari.core.utils.ContractUtils;
import by.deniotokiari.core.utils.DBUtils;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CoreDataBase extends SQLiteOpenHelper {

	private static final String DB_NAME = "core.store.db";
	private static final int DB_VERSION = 1;
	private static final Object DB_OBJECT_LOCK = new Object();

	private Context mContext;
	private boolean isInTransaction = false;
	private Class<?> mContract;

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
			Class<?> contract = mContract.getDeclaringClass();
			ContractUtils.checkContractClass(contract);
			Class<?>[] tables = contract.getClasses();
			for (Class<?> table : tables) {
				db.execSQL(DBUtils.getCreateTableString(table));
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
		//onCreate(db);
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

}
