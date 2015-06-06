package com.cgn.android.database.utils;

import android.content.ContentValues;
import android.database.Cursor;

public abstract class BaseItem {

    public abstract String getTableName();

    public abstract <T extends BaseItem> T readFromCursor(Cursor cursor);

    public abstract ContentValues getContentValues();

    public abstract long insertInDb();

    public abstract int updateInDb();

    public abstract int deleteFromDb();

    public void insertOrUpdateInDb() {
        if (insertInDb() == -1) updateInDb();
    }

    public static class SubClassContainer<T> {
        public T createContents(Class<T> clazz) {
            try {
                return clazz.newInstance();
            } catch (InstantiationException exception) {
                exception.printStackTrace();
                return null;
            } catch (IllegalAccessException exception) {
                exception.printStackTrace();
                return null;
            }
        }
    }
}