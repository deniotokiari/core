package by.deniotokiari.android.core.db.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import by.deniotokiari.android.core.db.IDataBase;
import by.deniotokiari.android.core.helper.ContractHelper;
import by.deniotokiari.android.core.utils.DBUtils;

import java.sql.SQLException;

public class SQLite extends SQLiteOpenHelper implements IDataBase {

    private static final String DB_NAME = "core.store.db";
    private static final int DB_VERSION = 1;

    private static final Object sDbObjectLock = new Object();

    private Context mContext;
    private boolean isInTransaction = true;

    private interface Operation<T> {

        public T execute(SQLiteDatabase database, String tableName);

    }

    public SQLite(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        mContext = context;
    }

    private <Q> Q executeDbOperation(Operation<Q> operation, Class<?> contract, String errorMessage) {
        return executeDbOperation(operation, contract, errorMessage, false);
    }

    private <Q> Q executeDbOperation(Operation<Q> operation, Class<?> contract, String errorMessage, boolean withTableCreate) {
        syncTransaction();
        SQLiteDatabase database = getWritableDatabase();
        String tableName = null;
        if (contract != null) {
            tableName = ContractHelper.getTableName(contract);
            if (withTableCreate) {
                createTableIfNotExist(database, contract);
            }
        }

        Q q = null;

        try {
            setInTransaction(true);
            database.beginTransaction();
            q = operation.execute(database, tableName);
            if (q == null) {
                throw new SQLException(errorMessage);
            }
            database.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
            setInTransaction(false);
        }

        return q;
    }

    private void createTableIfNotExist(SQLiteDatabase database, final Class<?> contract) {
        if (!DBUtils.isTableExists(database, ContractHelper.getTableName(contract))) {
            executeDbOperation(new Operation<Object>() {
                @Override
                public Object execute(SQLiteDatabase database, String tableName) {
                    database.execSQL(DBUtils.getCreateTableString(contract));
                    return 1;
                }
            }, contract, null);
        }
    }

    private void deleteDataBase(final Context context) {
        executeDbOperation(new Operation<Object>() {
            @Override
            public Object execute(SQLiteDatabase database, String tableName) {
                context.deleteDatabase(DB_NAME);
                return 1;
            }
        }, null, null, false);
    }

    private void syncTransaction() {
        while (isInTransaction) {
            waitWhileTransaction();
        }
    }

    private void setInTransaction(boolean flag) {
        synchronized (sDbObjectLock) {
            isInTransaction = flag;
            if (!isInTransaction) {
                sDbObjectLock.notifyAll();
            }
        }
    }

    private static void waitWhileTransaction() {
        synchronized (sDbObjectLock) {
            try {
                sDbObjectLock.wait();
            } catch (InterruptedException ignored) {

            }
        }
    }

    @Override
    public Cursor query(Class<?> contract, final String[] projection, final String selection, final String[] selectionArgs, final String sortOrder) {
        return executeDbOperation(new Operation<Cursor>() {
            @Override
            public Cursor execute(SQLiteDatabase database, String tableName) {
                return database.query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
            }
        }, contract, "Failed to query row from " + ContractHelper.getTableName(contract));
    }

    @Override
    public Cursor rawQuery(Class<?> contract, final String sql, final String[] selectionArgs) {
        return executeDbOperation(new Operation<Cursor>() {
            @Override
            public Cursor execute(SQLiteDatabase database, String tableName) {
                return database.rawQuery(sql, selectionArgs);
            }
        }, contract, "Failed to query: " + sql);
    }

    @Override
    public long insert(Class<?> contract, final ContentValues values) {
        return executeDbOperation(new Operation<Long>() {
            @Override
            public Long execute(SQLiteDatabase database, String tableName) {
                long id = database.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                if (id <= 0) {
                    return null;
                } else {
                    return id;
                }
            }
        }, contract, "Failed to insert row into " + ContractHelper.getTableName(contract));
    }

    @Override
    public int bulkInsert(Class<?> contract, final ContentValues[] values) {
        return executeDbOperation(new Operation<Integer>() {
            @Override
            public Integer execute(SQLiteDatabase database, String tableName) {
                int inserted = 0;
                long id;
                for (ContentValues contentValues : values) {
                    id = database.insertWithOnConflict(tableName, null, contentValues,
                            SQLiteDatabase.CONFLICT_REPLACE);
                    if (id <= 0) {
                        return null;
                    } else {
                        inserted++;
                    }
                }
                return inserted;
            }
        }, contract, "Failed to insert row into " + ContractHelper.getTableName(contract));
    }

    @Override
    public int delete(Class<?> contract, final String selection, final String[] selectionArgs) {
        return executeDbOperation(new Operation<Integer>() {
            @Override
            public Integer execute(SQLiteDatabase database, String tableName) {
                int deleted = database.delete(tableName, selection, selectionArgs);
                if (deleted <= 0) {
                    return null;
                } else {
                    return deleted;
                }
            }
        }, contract, "Failed to delete row into " + ContractHelper.getTableName(contract));
    }

    @Override
    public int update(Class<?> contract, ContentValues values, String selection, String[] selectionArgs) {
        // TODO implement update
        return 0;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            deleteDataBase(mContext);
            db.setVersion(newVersion);
        }
    }

}
