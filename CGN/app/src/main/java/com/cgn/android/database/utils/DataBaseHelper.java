package com.cgn.android.database.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.cgn.android.database.models.StudentVo;
import com.cgn.android.utils.AppConstants;
import com.cgn.android.utils.CGNApplication;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DataBaseHelper.class.getSimpleName();
    //    private static final String NEXT_SELECT = "SELECT * FROM ";
    private static final int DB_VERSION = 7;
    private static final String DB_PATH = CGNApplication.getAppContext().getFilesDir().getPath()
            + "/data/com.tws.android/databases/";
//        private static final String DB_PATH = Environment.getExternalStorageDirectory() + "/";
    private static final String DB_NAME = "TWS.db";
    private static boolean sIsLoggedIn = true;
    // Single thread executor for Database insertion.
    private static ExecutorService SINGLE_THREAD_EXECUTOR = Executors
            .newFixedThreadPool(1);
    private static SQLiteDatabase mDataBase = null;
    private static DataBaseHelper mDataBaseHelper = null;
    // Cache of table name to get-next-id compiled statements
//    private Map<String, SQLiteStatement> tableNameToNextIdStatementsMap = new HashMap<String, SQLiteStatement>();

    /**
     * Constructor takes and keeps a reference of the passed context in order to
     * access application assets and resources.
     */
    private DataBaseHelper(Context context) {
        // TODO remove DB_PATH when releasing app
//        super(context, DB_PATH + DB_NAME, null, DB_VERSION);
        super(context, DB_NAME, null, DB_VERSION);
        mDataBase = getWritableDatabase();
    }

    /**
     * Singleton for DataBase
     *
     * @return singleton instance
     */
    public static synchronized DataBaseHelper getInstance() {
        if (mDataBaseHelper == null) {
            mDataBaseHelper = new DataBaseHelper(CGNApplication.getAppContext());
        }
        if (SINGLE_THREAD_EXECUTOR.isShutdown()) {
            SINGLE_THREAD_EXECUTOR = Executors.newFixedThreadPool(1);
        }
        return mDataBaseHelper;
    }

    public static synchronized DataBaseHelper createInstance() {
        if (mDataBase == null || !mDataBase.isOpen()) {
            mDataBaseHelper = new DataBaseHelper(CGNApplication.getAppContext());
        }
        return mDataBaseHelper;
    }

    public static void deleteInstance() {
        if (mDataBaseHelper != null) {
            DataBaseHelper.sIsLoggedIn = false;
            SINGLE_THREAD_EXECUTOR.shutdown();
            try {
                // Wait a while for existing tasks to terminate
                boolean terminated = SINGLE_THREAD_EXECUTOR.awaitTermination(2,
                        TimeUnit.SECONDS);
                if (terminated) {
                    // Have terminated. Go ahead and delete database.
                    if (mDataBaseHelper != null) {
                        mDataBaseHelper.close();
                        // mDataBaseHelper = null;
                    }
                    CGNApplication.getAppContext().deleteDatabase(DB_NAME);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, e.getMessage());
            }
        }
    }

    /**
     * Copies database from local assets-folder to the just created empty
     * database in the system folder, from where it can be accessed and handled.
     *
     * @throws IOException io exception
     */
    public void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = CGNApplication.getAppContext().getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];

        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    /**
     * Select method
     *
     * @param query select query
     * @return - Cursor with the results
     * @throws SQLException sql exception
     */
    public Cursor rawQuery(final String query, final String[] selectionArgs)
            throws SQLException {
        return (sIsLoggedIn ? mDataBase.rawQuery(query, selectionArgs) : null);
    }

    public Cursor select(String tableName) {
        return rawQuery("SELECT * FROM " + tableName, null);
    }

    public Cursor select(String tableName, String whereClause,
                         String[] whereArgs) {
        return rawQuery("SELECT * FROM " + tableName + " WHERE " + whereClause,
                whereArgs);
    }

    public Cursor select(String tableName, String whereClause,
                         String[] whereArgs, String orderBy) {
        return rawQuery("SELECT * FROM " + tableName + " WHERE " + whereClause
                + " ORDER BY " + orderBy, whereArgs);
    }

    public <T extends BaseItem> List<T> selectAllList(String tableName,
                                                      Class<T> type, String whereClause, String[] whereArgs,
                                                      String orderBy) {
        Cursor cursor = rawQuery("SELECT * FROM " + tableName + " ORDER BY "
                + orderBy, new String[]{});
        BaseItem.SubClassContainer<T> container = new BaseItem.SubClassContainer<T>();
        return cursor == null ? null : loadAllFromCursor(cursor,
                container.createContents(type));
    }

    public <T extends BaseItem> List<T> selectList(String tableName,
                                                   Class<T> type) {
        Cursor cursor = rawQuery("SELECT * FROM " + tableName, null);
        BaseItem.SubClassContainer<T> container = new BaseItem.SubClassContainer<T>();
        return cursor == null ? null : loadAllFromCursor(cursor,
                container.createContents(type));
    }

    public <T extends BaseItem> List<T> selectList(String tableName, String columnName,
                                                   Class<T> type) {
        Cursor cursor = rawQuery("SELECT " + columnName + " FROM " + tableName, null);
        BaseItem.SubClassContainer<T> container = new BaseItem.SubClassContainer<T>();
        return cursor == null ? null : loadAllFromCursor(cursor,
                container.createContents(type));
    }

    public <T extends BaseItem> List<T> selectList(String tableName,
                                                   Class<T> type, String whereClause, String... whereArgs) {
        Cursor cursor = rawQuery("SELECT * FROM " + tableName + " WHERE "
                + whereClause, whereArgs);
        BaseItem.SubClassContainer<T> container = new BaseItem.SubClassContainer<T>();
        return cursor == null ? null : loadAllFromCursor(cursor,
                container.createContents(type));
    }

    public <T extends BaseItem> List<T> selectList(String tableName, String columnName,
                                                   Class<T> type, String whereClause, String... whereArgs) {
        Cursor cursor = rawQuery("SELECT " + columnName + " FROM " + tableName + " WHERE "
                + whereClause, whereArgs);
        BaseItem.SubClassContainer<T> container = new BaseItem.SubClassContainer<T>();
        return cursor == null ? null : loadAllFromCursor(cursor,
                container.createContents(type));
    }

    public <T extends BaseItem> List<T> selectListWithGroupBy(String tableName,
                                                              Class<T> type, String groupBy) {
        Cursor cursor = rawQuery("SELECT * FROM " + tableName + " GROUP BY "
                + groupBy, new String[]{});
        BaseItem.SubClassContainer<T> container = new BaseItem.SubClassContainer<T>();
        return cursor == null ? null : loadAllFromCursor(cursor,
                container.createContents(type));
    }

    public <T extends BaseItem> List<T> selectListWithGroupBy(String tableName,
                                                              Class<T> type, String groupBy, String whereClause,
                                                              String... whereArgs) {
        Cursor cursor = rawQuery("SELECT * FROM " + tableName + " WHERE "
                + whereClause + " GROUP BY " + groupBy, whereArgs);
        BaseItem.SubClassContainer<T> container = new BaseItem.SubClassContainer<T>();
        return cursor == null ? null : loadAllFromCursor(cursor,
                container.createContents(type));
    }

    public <T extends BaseItem> List<T> selectListWithOrder(String tableName,
                                                            Class<T> type, String orderBy) {
        Cursor cursor = rawQuery("SELECT * FROM " + tableName + " ORDER BY "
                + orderBy, new String[]{});
        BaseItem.SubClassContainer<T> container = new BaseItem.SubClassContainer<T>();
        return cursor == null ? null : loadAllFromCursor(cursor,
                container.createContents(type));
    }

    public <T extends BaseItem> List<T> selectListWithOrder(String tableName,
                                                            Class<T> type, String orderBy, String whereClause,
                                                            String... whereArgs) {
        Cursor cursor = rawQuery("SELECT * FROM " + tableName + " WHERE "
                + whereClause + " ORDER BY " + orderBy, whereArgs);
        BaseItem.SubClassContainer<T> container = new BaseItem.SubClassContainer<T>();
        return cursor == null ? null : loadAllFromCursor(cursor,
                container.createContents(type));
    }

    public <T extends BaseItem> T selectUnique(String tableName, Class<T> type,
                                               String whereClause, String... whereArgs) {
        Cursor cursor = rawQuery("SELECT * FROM " + tableName + " WHERE "
                + whereClause + " LIMIT 1", whereArgs);
        BaseItem.SubClassContainer<T> container = new BaseItem.SubClassContainer<T>();
        return cursor == null ? null : loadUnique(cursor,
                container.createContents(type));
    }

    public <T extends BaseItem> T loadUnique(Cursor cursor, T clazz) {
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            }
            return clazz.readFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }

    public <T extends BaseItem> List<T> loadAllFromCursor(Cursor cursor, T clazz) {
        return loadAllFromCursor(cursor, clazz, 0);
    }

    @SuppressWarnings("unchecked")
    public <T extends BaseItem> List<T> loadAllFromCursor(Cursor cursor,
                                                          T clazz, int offset) {
        try {
            int count = cursor.getCount();
            List<T> list = new ArrayList<T>(count);

            if (cursor.moveToFirst()) {

                do {
                    list.add((T) clazz.readFromCursor(cursor));
                } while (cursor.moveToNext());

            }
            return list;
        } finally {
            cursor.close();
        }
    }

    /**
     * Insert method
     *
     * @param table  - name of the table
     * @param values values to insert
     * @throws SQLException sql exception
     */
    public long insert(String table, ContentValues values) {
        if (mDataBase != null && sIsLoggedIn && mDataBase.isOpen()) {
            mDataBase.beginTransaction();
            long rowId = -1;
            try {
                rowId = mDataBase.insertWithOnConflict(table, null, values, SQLiteDatabase.CONFLICT_IGNORE);
                mDataBase.setTransactionSuccessful();
            } finally {
                mDataBase.endTransaction();
            }
            return rowId;
        } else {
            return -1;
        }
    }

    public <T extends BaseItem> void insertInTx(List<T> entities, DataBaseWorker.DataBaseWorkerCallback callback) {
        mDataBase.beginTransaction();
        try {
            for (T entity : entities) {
                entity.insertInDb();
            }
            mDataBase.setTransactionSuccessful();
        } finally {
            mDataBase.endTransaction();
            if (callback != null) {
                callback.onFinished();
            }
        }
    }

    public long insertOrReplace(String table, String uniqueColumn,
                                String uniqueValue, ContentValues values) {
        if (mDataBase != null && sIsLoggedIn && mDataBase.isOpen()) {
            long rowId = -1;
            mDataBase.beginTransaction();
            try {
                if (TextUtils.isEmpty(uniqueValue)) {
                    rowId = mDataBase.insert(table, null, values);
                } else if (mDataBase.update(table, values, uniqueColumn + "=?",
                        new String[]{uniqueValue}) == 0) {
                    rowId = mDataBase.insert(table, null, values);
                } else {
                    Cursor cursor = mDataBase.rawQuery("SELECT _id FROM "
                                    + table + " WHERE " + uniqueColumn + "=?",
                            new String[]{uniqueValue});
                    try {
                        boolean available = cursor.moveToFirst();
                        if (available) {
                            rowId = cursor.isNull(0) ? -1 : cursor.getLong(0);
                        }
                    } finally {
                        cursor.close();
                    }
                }
                mDataBase.setTransactionSuccessful();
            } finally {
                mDataBase.endTransaction();
            }
            return rowId;
        } else {
            return -1;
        }
    }

    public <T extends BaseItem> void insertOrUpdateInTx(List<T> entities, DataBaseWorker.DataBaseWorkerCallback callback) {
        mDataBase.beginTransaction();
        try {
            for (T entity : entities) {
                entity.insertOrUpdateInDb();
            }
            mDataBase.setTransactionSuccessful();
        } finally {
            mDataBase.endTransaction();
            if (callback != null) {
                callback.onFinished();
            }
        }
    }

    public int update(String table, ContentValues contentValues, String whereClause, String... whereArgs) {
        return (sIsLoggedIn && mDataBase != null && mDataBase.isOpen()) ? mDataBase.update(table, contentValues, whereClause, whereArgs) : -1;
    }

    public int update(String table, ContentValues contentValues) {
        return update(table, contentValues, "", new String[]{});
    }

    public <T extends BaseItem> void updateInTx(List<T> entities, DataBaseWorker.DataBaseWorkerCallback callback) {
        if (mDataBase != null && mDataBase.isOpen()) {
            mDataBase.beginTransaction();
            try {
                for (T entity : entities) {
                    entity.updateInDb();
                }
                mDataBase.setTransactionSuccessful();
            } finally {
                mDataBase.endTransaction();
                if (callback != null) {
                    callback.onFinished();
                }
            }
        }
    }

    public <T extends BaseItem> void nullColumnInTx(String tablename, String columnName) {
        nullColumnInTx(tablename, columnName, "", new String[]{});
    }

    public <T extends BaseItem> void nullColumnInTx(String tablename, String columnName, String whereStmt, String... whereArgs) {
        if (mDataBase != null && mDataBase.isOpen()) {
            mDataBase.beginTransaction();
            try {
                ContentValues vals = new ContentValues();
                vals.putNull(columnName);
                mDataBase.update(tablename, vals, whereStmt, whereArgs);
                mDataBase.setTransactionSuccessful();
            } finally {
                mDataBase.endTransaction();
            }
        }
    }

    /**
     * Does a delete (after first Logger.the delete statement)
     *
     * @param table
     * @param whereClause
     * @param whereArgs
     */
    public int delete(String table, String whereClause, String... whereArgs) {
        if (sIsLoggedIn && mDataBase != null && mDataBase.isOpen()) {
            return mDataBase.delete(table, whereClause, whereArgs);
        }
        return 0;
    }

    public <T extends BaseItem> void deleteInTx(List<T> entities, DataBaseWorker.DataBaseWorkerCallback callback) {
        if (mDataBase != null && mDataBase.isOpen()) {
            mDataBase.beginTransaction();
            try {
                for (T entity : entities) {
                    entity.deleteFromDb();
//                    delete(entity.getTableName(), "_id=" + entity.get_Id(), new String[]{});
                }
                mDataBase.setTransactionSuccessful();
            } finally {
                mDataBase.endTransaction();
                if (callback != null) {
                    callback.onFinished();
                }
            }
        }
    }

    /**
     * Let you make a raw query
     *
     * @param command - the sql comand you want to run
     */
    public void sqlCommand(String command) {
        if (sIsLoggedIn && mDataBase != null && mDataBase.isOpen()) {
            mDataBase.execSQL(command);
        }
    }

    public void printTable(String tableName) {
        if (sIsLoggedIn && mDataBase != null && mDataBase.isOpen() && AppConstants.LOG) {
            Log.d(TAG, "---------- " + tableName + " ----------");
            Cursor c = mDataBase.rawQuery("SELECT * FROM " + tableName, null);
            c.moveToFirst();
            logCursorInfo(c);
            c.close();
        }
    }

    // This function iterates a Cursor and prints its contents
    // Note: Sqlite databases are not strongly typed, so you can pull everything
    // out as a string, or you can use the
    // appropriate get call to enforce type safety.
    // In this case, we are just Logger.so we treat all columns as strings
    // using getString() method
    public void logCursorInfo(Cursor c) {
        Log.d(TAG, "*** Cursor Begin *** " + " Results:" + c.getCount()
                + " Columns: " + c.getColumnCount());

        // Print column names
        String rowHeaders = "|| ";
        int length = c.getColumnCount();
        for (int i = 0; i < length; i++) {

            rowHeaders = rowHeaders.concat(c.getColumnName(i) + " || ");
        }
        Log.d(TAG, "COLUMNS " + rowHeaders);

        // Print records
        c.moveToFirst();
        while (!c.isAfterLast()) {
            String rowResults = "|| ";
            length = c.getColumnCount();
            for (int i = 0; i < length; i++) {
                rowResults = rowResults.concat(c.getString(i) + " || ");
            }
            Log.d(TAG, "Row " + c.getPosition() + ": " + rowResults);

            c.moveToNext();
        }
        Log.d(TAG, "*** Cursor End ***");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
            db.setMaxSqlCacheSize(SQLiteDatabase.MAX_SQL_CACHE_SIZE);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        sIsLoggedIn = true;

        //Testing
        StudentVo.createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        mUpgrading = true;
        if (newVersion > oldVersion) {
            dropAllTables(db);
            onCreate(db);
        }
    }

    private void dropAllTables(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=false;");


        //Testing
        StudentVo.dropTable(db);

        db.execSQL("PRAGMA foreign_keys=true;");
    }

    public void clearTables() {


        //Testing only
        delete(StudentVo.TABLE_NAME, null);
    }
}