package com.cgn.android.database.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cgn.android.database.utils.BaseItem;
import com.cgn.android.database.utils.DBUtils;
import com.cgn.android.database.utils.DataBaseHelper;
import com.cgn.android.utils.Logger;


/**
 * Created by anil on 1/6/15.
 */
public class StudentVo extends BaseItem {

    private static final String TAG = StudentVo.class.getSimpleName();

    /* ------------------------------ START DATABASE related ------------------------------ */
    public static final String TABLE_NAME = "Student";

    //Fields
    private int mRowId;
    private String mSName;
    private int mRollNo;
    private String mAddress;

    public StudentVo() {
    }

    public void setmSName(String mSName) {
        this.mSName = mSName;
    }

    public StudentVo(String mSName, int mRollNo, String mAddress) {
        this.mSName = mSName;
        this.mRollNo = mRollNo;
        this.mAddress = mAddress;
    }

    public static void createTable(SQLiteDatabase db) {
        DBUtils.CreateTableStringCreator creator = new DBUtils.CreateTableStringCreator(TABLE_NAME);
        creator.addColumn(Columns.ROW_ID.name, "INTEGER", true);
        creator.addColumn(Columns.NAME.name, "TEXT", false);
        creator.addColumn(Columns.ROLLNO.name, "INTEGER", false);
        creator.addColumn(Columns.ADDRESS.name, "TEXT", false);
        db.execSQL(creator.toString());
    }

    public static void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }


    public enum Columns {
        ROW_ID("row_id"),
        NAME("name"),
        ROLLNO("rollNo"),
        ADDRESS("address");

        private int index;
        public String name;

        Columns(String name) {
            this.index = ordinal();
            this.name = name;
        }
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public StudentVo readFromCursor(Cursor cursor) {
        StudentVo student = new StudentVo();
        student.mRowId = cursor.getInt(Columns.ROW_ID.index);
        student.mSName = cursor.getString(Columns.NAME.index);
        student.mRollNo = cursor.getInt(Columns.ROLLNO.index);
        student.mAddress = cursor.getString(Columns.ADDRESS.index);
        return student;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
//        values.put(Columns.ROW_ID.name, mRowId);
        values.put(Columns.NAME.name, mSName);
        values.put(Columns.ROLLNO.name, mRollNo);
        values.put(Columns.ADDRESS.name, mAddress);
        return values;
    }

    @Override
    public long insertInDb() {
        long rowId = DataBaseHelper.getInstance().insert(getTableName(), getContentValues());
        if (rowId != -1) {
            Logger.d(TAG, "Inserted rowId: " + rowId);
        }
        return rowId;
    }

    @Override
    public int updateInDb() {
        String whereClause = Columns.ROW_ID.name + "=" + mRowId;
        int rows = DataBaseHelper.getInstance().update(getTableName(), getContentValues(), whereClause);
        if (rows != 0) {
            Logger.d(TAG, "Updated no of rows: " + rows);
        }
        return rows;
    }

    @Override
    public int deleteFromDb() {
        String whereClause = Columns.ROW_ID.name + "=" + mRowId;
        return DataBaseHelper.getInstance().delete(getTableName(), whereClause);
    }

    public static StudentVo getRecord(int rowId) {
        String whereClause = Columns.ROW_ID.name + "=" + rowId;
        return DataBaseHelper.getInstance().selectUnique(TABLE_NAME, StudentVo.class, whereClause);
    }
    /* ------------------------------ END DATABASE related ------------------------------ */

    public int getmRowId() {
        return mRowId;
    }

    public String getmSName() {
        return mSName;
    }

    public int getmRollNo() {
        return mRollNo;
    }

    public String getmAddress() {
        return mAddress;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("RowID : "+ mRowId).append(", Name : " + mSName).append(", RollNo : "+ mRollNo).append(", Address : "+ mAddress);
        return sb.toString();
    }
}
