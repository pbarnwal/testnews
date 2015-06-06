package com.cgn.android.activities;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.cgn.android.R;
import com.cgn.android.adapters.MenuRvAdapter;
import com.cgn.android.fragments.ArchivesFragment;
import com.cgn.android.fragments.NewsFragment;
import com.cgn.android.fragments.ParentFragment;
import com.cgn.android.models.NavItem;
import com.cgn.android.utils.AppConstants;
import com.cgn.android.utils.UIUtils;
import com.cgn.android.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anil on 2/6/15.
 */
public class NewsActivity extends ParentActivity implements Handler.Callback,
        AdapterView.OnItemClickListener {

    private static final String TAG = NewsActivity.class.getSimpleName();

    private RecyclerView mLeftDrawerRV;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayList<NavItem> mNavItemList;
    private MenuRvAdapter mMenuAdapter;
    private int mCurrScreenId = AppConstants.AllSCREENS.LAUNCH_NEWS_SCREEN.ID;
    private int mNewScreenId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        mContainerID = R.id.news_container;

        initDrawerView();
        initDrawer();

        if (savedInstanceState == null) {
            displayFragment(AppConstants.AllSCREENS.getScreenName(mCurrScreenId));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initDrawerView() {
        mLeftDrawerRV = (RecyclerView) findViewById(R.id.left_drawer);
        mLeftDrawerRV.setHasFixedSize(false);
        mLeftDrawerRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        //initialize Navigation Items
        initializeNavItems();

//        mDrawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
//        mDrawerList = (ListView) view.findViewById(R.id.top_sectionlist);

        int width = getResources().getDisplayMetrics().widthPixels/2;
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) mLeftDrawerRV.getLayoutParams();
        params.width = width;
        mLeftDrawerRV.setLayoutParams(params);

        mMenuAdapter = new MenuRvAdapter(mNavItemList, this);
        mMenuAdapter.setCurrentItem(mCurrScreenId);
        mLeftDrawerRV.setAdapter(mMenuAdapter);
    }

    private void initializeNavItems() {
        mNavItemList = new ArrayList<NavItem>();
        TypedArray nav_item_icons = getResources().obtainTypedArray(R.array.Nav_item_Icons);
        String[] nav_item_names = getResources().getStringArray(R.array.Nav_item_names);
        TypedArray nav_item_bg = getResources().obtainTypedArray(R.array.Nav_item_BG);

        int length = nav_item_icons.length();
        for (int i = 0; i < length; i++) {
            NavItem item = new NavItem(nav_item_icons.getResourceId(i, 0), nav_item_names[i], nav_item_bg.getResourceId(i, 0));
            mNavItemList.add(item);
        }
        nav_item_icons.recycle();  //DEV-101: Recycling here
    }

    private void initDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                                confirmNavigate();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void confirmNavigate() {
        displayFragment(AppConstants.AllSCREENS.getScreenName(mNewScreenId));
        mCurrScreenId = mNewScreenId;
        mMenuAdapter.setCurrentItem(mCurrScreenId);
        mNewScreenId = -1;
    }

    private void displayFragment(AppConstants.AllSCREENS currScreen) {
        switch (currScreen) {
            case LAUNCH_NEWS_SCREEN:
                launchNewsScreen();
                break;

            case LAUNCH_ARCHIVES_SCREEN:
                launchArchivesScreen();
                break;

            case LAUNCH_SHARE_TO_FRIENDS_SCREEN:
                UIUtils.showDebugToast("Work in Progress");
                break;

            case LAUNCH_RATE_OUR_APP:
                UIUtils.showDebugToast("Work in Progress");
                break;

            default:
                break;
        }
    }

    private void launchNewsScreen() {
        updateTitleBar(true, false, false, false, false, Integer.MIN_VALUE, Integer.MIN_VALUE, false);
        replaceFragment(NewsFragment.newInstance(), NewsFragment.TAG);
    }

    private void launchArchivesScreen() {
        updateTitleBar(true, false, false, false, false, Integer.MIN_VALUE, Integer.MIN_VALUE, false);
        replaceFragment(ArchivesFragment.newInstance(), ArchivesFragment.TAG);
    }

    private void removeAllFragments() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }
    }

    @Override
    protected void addFragment(Fragment fragment, String tag) {
        removeAllFragments();
        super.addFragment(fragment, tag);
    }

    @Override
    protected void replaceFragment(Fragment fragment, String tag) {
        removeAllFragments();
        super.replaceFragment(fragment, tag);
    }

    @Override
    public boolean handleMessage(Message msg) {

        AppConstants.AllSCREENS currScreen = AppConstants.AllSCREENS.getScreenName(msg.what);
        switch (currScreen) {

            default:
                mDisplayedFragment = getSupportFragmentManager().findFragmentById(R.id.news_container);
                ((ParentFragment) mDisplayedFragment).handleMessage(Utilities.getMessage(msg.what, msg.getData()));
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mNewScreenId = position;
        mDrawerLayout.closeDrawer(mLeftDrawerRV);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}
