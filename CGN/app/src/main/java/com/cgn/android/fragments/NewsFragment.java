package com.cgn.android.fragments;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.cgn.android.R;
import com.cgn.android.adapters.SchedulesDashboardAdapter;
import com.cgn.android.models.NewsItem;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Anil on 2/2/15.
 */
public class NewsFragment extends ParentFragment implements AdapterView.OnItemClickListener {

    public static final String TAG = NewsFragment.class.getSimpleName();
    private RecyclerView mDashboardRcView;
    private List<NewsItem> mTotalEvents = new ArrayList<NewsItem>();
    private SchedulesDashboardAdapter mDashboardAdapter;
    private AdView mAdTopView, mAdBottomView;

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    protected void initialiseView(View view, Bundle savedInstanceState) {
        mAdTopView = (AdView) view.findViewById(R.id.ad_TopView);
        mAdBottomView = (AdView) view.findViewById(R.id.ad_BottomView);

        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        // Start loading the ad in the background.
        mAdBottomView.loadAd(adRequest);
        mAdTopView.loadAd(adRequest);

        initializedList();

        mDashboardRcView = (RecyclerView) view.findViewById(R.id.DASHBOARD_events_rc_view);
        mDashboardRcView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDashboardAdapter = new SchedulesDashboardAdapter(getCurrActivity(), mTotalEvents, NewsFragment.this);
        mDashboardRcView.setAdapter(mDashboardAdapter);
    }

    private void initializedList() {
        for(int i = 0; i < 40; i++){
            NewsItem item = new NewsItem("asfdasjdfzdf  aer fa feaf eaef aefae zsf zdf fz feef we e sg srgsergs se s s gsrf waeafr a fa lkdfad", i + 1+ " hour ago" , "DOPT");
            mTotalEvents.add(item);
        }
    }

    @Override
    protected void bindListeners(View view) {
        view.findViewById(R.id.tab1_btn).setOnClickListener(this);
        view.findViewById(R.id.tab2_btn).setOnClickListener(this);
        view.findViewById(R.id.tab3_btn).setOnClickListener(this);
        view.findViewById(R.id.tab4_btn).setOnClickListener(this);
        view.findViewById(R.id.tab5_btn).setOnClickListener(this);
    }

    @Override
    protected void callInitialApi() {
        super.callInitialApi();
        callApis();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mAdTopView != null && mAdBottomView != null) {
            mAdTopView.resume();
            mAdBottomView.resume();
        }
    }

    @Override
    public void onPause() {
        if (mAdTopView != null && mAdBottomView != null) {
            mAdTopView.pause();
            mAdBottomView.pause();
        }

        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (mAdTopView != null && mAdBottomView != null) {
            mAdTopView.destroy();
            mAdBottomView.destroy();
        }

        super.onDestroy();
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
            case R.id.tab1_btn:

                break;

            case R.id.tab2_btn:
                break;

            case R.id.tab3_btn:
                break;

            case R.id.tab4_btn:
                break;

            case R.id.tab5_btn:
                break;

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