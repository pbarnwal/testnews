package com.cgn.android.fragments;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.cgn.android.R;


/**
 * Created by Anil on 2/2/15.
 */
public class ArchivesFragment extends ParentFragment implements AdapterView.OnItemClickListener {

    public static final String TAG = ArchivesFragment.class.getSimpleName();

    public static ArchivesFragment newInstance() {
        return new ArchivesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_archives, container, false);
    }

    @Override
    protected void initialiseView(View view, Bundle savedInstanceState) {

        dodummyStudentDB();
    }

    @Override
    protected void bindListeners(View view) {

    }

    @Override
    protected void callInitialApi() {
        super.callInitialApi();
        callApis();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
   private void callApis() {

    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {

        }
        return false;
    }

    @Override
    public void onClicked(View v) {
        switch (v.getId()) {

            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private void dodummyStudentDB() {

//        for(int i = 0; i < 10; i++){
//            final StudentVo student = new StudentVo("Anil", 100 + i , "Address1");
//            StoreHelper.insertOrUpdateInBackground(student, new DBUtils.DbSaveCallback() {
//                @Override
//                public void done() {
//                    Log.d(TAG, "Success insertion : " + student.getmRowId());
//                }
//            });
//        }

        //StoreHelper.insert() and StoreHelper.update();


//        DataBaseHelper.getInstance().printTable(StudentVo.TABLE_NAME);

        //Deleting record
//        StoreHelper.deleteStudent(105);

//        DataBaseHelper.getInstance().printTable(StudentVo.TABLE_NAME);

//        StudentVo studentVo = StoreHelper.getStudent(106);
//        studentVo.setmSName("Jay");
//        if(StoreHelper.getStudent(106) != null) {
//            StoreHelper.getStudent(106).toString();
//        }
//        studentVo.insertOrUpdateInDb();

//        StoreHelper.insertOrUpdateInBackground(studentVo, new DBUtils.DbSaveCallback() {
//            @Override
//            public void done() {
//                Log.d(TAG, "Success insertion");
//                DataBaseHelper.getInstance().printTable(StudentVo.TABLE_NAME);
//            }
//        });


//        StoreHelper.deleteAllStudents();
//        DataBaseHelper.getInstance().printTable(StudentVo.TABLE_NAME);


    }
}