package com.cgn.android.database.utils;

import com.cgn.android.database.models.StudentVo;

import java.util.List;


public class StoreHelper {

    public static final String NOT_NULL = " IS NOT NULL";
    private static final String TAG = StoreHelper.class.getSimpleName();

    public static <T extends BaseItem> void insertOrUpdateInBackground(List<T> entities, final DBUtils.DbSaveCallback callback) {
        DataBaseWorker<T> worker = new DataBaseWorker<T>(entities, new DataBaseWorker.DataBaseWorkerCallback() {
            @Override
            public void onFinished() {
                if (callback != null) callback.done();
            }
        }, DataBaseWorker.WorkerType.TypeInsertOrUpdate);

        worker.run();
    }

    public static <T extends BaseItem> void insertOrUpdateInBackground(T entity, final DBUtils.DbSaveCallback callback) {
        DataBaseWorker<T> worker = new DataBaseWorker<T>(entity, new DataBaseWorker.DataBaseWorkerCallback() {
            @Override
            public void onFinished() {
                if (callback != null) callback.done();
            }
        }, DataBaseWorker.WorkerType.TypeInsertOrUpdate);
        worker.run();
    }


    public static List<StudentVo> getStudents(){
        return DataBaseHelper.getInstance().selectList(StudentVo.TABLE_NAME, StudentVo.class);
    }

    public static StudentVo getStudent(int rollNo) {
        String whereClause = StudentVo.Columns.ROLLNO.name + "=" + rollNo;
        return DataBaseHelper.getInstance().selectUnique(StudentVo.TABLE_NAME, StudentVo.class, whereClause);
    }

    public static void deleteStudent(int rollNo) {
        String whereClause = StudentVo.Columns.ROLLNO + "=" + rollNo;
        DataBaseHelper.getInstance().delete(StudentVo.TABLE_NAME, whereClause);
    }

    public static void deleteAllStudents() {
        DataBaseHelper.getInstance().delete(StudentVo.TABLE_NAME, null);
    }
}