package com.cgn.android.database.utils;

import java.util.ArrayList;
import java.util.List;

public class DataBaseWorker<T extends BaseItem> implements Runnable {

    public enum WorkerType {
        TypeInsert, TypeUpdate, TypeDelete, TypeInsertOrUpdate
    }

    private List<T> mEntities = null;
    private WorkerType mType = null;
    private DataBaseWorkerCallback mCallback = null;

    public interface DataBaseWorkerCallback {
        public void onFinished();
    }

    public DataBaseWorker(T entity) {
        this(entity, null, WorkerType.TypeInsert);
    }

    public DataBaseWorker(List<T> entities) {
        this(entities, null, WorkerType.TypeInsert);
    }

    public DataBaseWorker(T entity, DataBaseWorkerCallback callback) {
        this(entity, callback, WorkerType.TypeInsert);
    }

    public DataBaseWorker(List<T> entities, DataBaseWorkerCallback callback) {
        this(entities, callback, WorkerType.TypeInsert);
    }

    public DataBaseWorker(T entity, WorkerType type) {
        this.mEntities = new ArrayList<T>();
        this.mEntities.add(entity);
        this.mType = type;
    }

    public DataBaseWorker(List<T> entities, WorkerType type) {
        this.mEntities = entities;
        this.mType = type;
    }

    public DataBaseWorker(T entity, DataBaseWorkerCallback callback, WorkerType type) {
        this.mEntities = new ArrayList<T>();
        this.mEntities.add(entity);
        this.mType = type;
        this.mCallback = callback;
    }

    public DataBaseWorker(List<T> entities, DataBaseWorkerCallback callback, WorkerType type) {
        this.mEntities = entities;
        this.mType = type;
        this.mCallback = callback;
    }

    @Override
    public void run() {
        switch (this.mType) {
            case TypeInsert:
                DataBaseHelper.getInstance().insertInTx(mEntities, mCallback);
                break;

            case TypeUpdate:
                DataBaseHelper.getInstance().updateInTx(mEntities, mCallback);
                break;

            case TypeDelete:
                DataBaseHelper.getInstance().deleteInTx(mEntities, mCallback);
                break;

            case TypeInsertOrUpdate:
                DataBaseHelper.getInstance().insertOrUpdateInTx(mEntities, mCallback);
                break;

            default:
                break;
        }
    }

}
