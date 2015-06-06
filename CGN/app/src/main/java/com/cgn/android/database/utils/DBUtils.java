package com.cgn.android.database.utils;

import java.util.List;

/**
 * Created by Anil on 9/2/15.
 */

public class DBUtils {

    public static class CreateTableStringCreator {

        private String mTableName;

        private String mColumnCreationStr = "";

        public CreateTableStringCreator(String tableName) {
            mTableName = tableName;
        }

        public void addColumn(String name, String type, boolean primaryKey) {
            // Adding comma if columns already present
            if (mColumnCreationStr.length() != 0) {
                mColumnCreationStr += ", ";
            }

            // Adding column
            mColumnCreationStr += name + " ";
            mColumnCreationStr += type + " ";
            if (primaryKey) {
                mColumnCreationStr += "PRIMARY KEY ";
            }
        }

        /**
         * Use this method after adding localColumnName in this table and after creating foreign table
         *
         * @param localColumnName
         * @param foreignTableName
         * @param foreignColumnName
         */
        public void addForeignKey(String localColumnName, String foreignTableName, String foreignColumnName, boolean onDeleteCascade) {
            if (mColumnCreationStr.length() != 0) {
                mColumnCreationStr += ", ";
            }
            mColumnCreationStr += "FOREIGN KEY(" + localColumnName + ") ";
            mColumnCreationStr += "REFERENCES " + foreignTableName + "(" + foreignColumnName + ")";

            if (onDeleteCascade) {
                mColumnCreationStr += " ON DELETE CASCADE";
            }
        }

        @Override
        public String toString() {
            String createString = "CREATE TABLE " + mTableName;
            createString += " (" + mColumnCreationStr + ");";
            return createString;
        }
    }

    public interface DbSaveCallback {
        void done();
    }

    public interface DbGetCallback<T extends List<BaseItem>> {
        void done(T items);
    }

}
