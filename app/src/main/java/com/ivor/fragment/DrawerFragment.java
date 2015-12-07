package com.ivor.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.ivor.ui.R;

/**
 * Description：DrawerLayout
 * * @author  Ivor
 */

public class DrawerFragment extends Fragment implements OnClickListener {

    private LinearLayout mActionLL1;
    private LinearLayout mActionLL2;
    private LinearLayout mActionLL3;
    private LinearLayout mActionLL4;
    private LinearLayout mActionLL5;
    private LinearLayout mActionLL6;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View mDrawerV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mDrawerV = inflater.inflate(R.layout.ivor_drawer_fragment, container, false);
        mDrawerV.setOnClickListener(this);
        initView(mDrawerV);
        initListener();
        return mDrawerV;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 设置该fragment拥有自己的actionbar，actionitem
        setHasOptionsMenu(true);
    }

    private void initView(View v) {
        this.mActionLL1 = (LinearLayout) v.findViewById(R.id.ivor_drawer1_ll);
        this.mActionLL2 = (LinearLayout) v.findViewById(R.id.ivor_drawer2_ll);
        this.mActionLL3 = (LinearLayout) v.findViewById(R.id.ivor_drawer3_ll);
        this.mActionLL4 = (LinearLayout) v.findViewById(R.id.ivor_drawer4_ll);
        this.mActionLL5 = (LinearLayout) v.findViewById(R.id.ivor_drawer5_ll);
        this.mActionLL6 = (LinearLayout) v.findViewById(R.id.ivor_drawer6_ll);
    }

    private void initListener() {
        mActionLL1.setOnClickListener(this);
        mActionLL2.setOnClickListener(this);
        mActionLL3.setOnClickListener(this);
        mActionLL4.setOnClickListener(this);
        mActionLL5.setOnClickListener(this);
        mActionLL6.setOnClickListener(this);
    }

    // 定义DrawerLayout的开关状态
    public void setUp(DrawerLayout drawerLayout) {
        mDrawerLayout = drawerLayout;
        mDrawerLayout.setDrawerShadow(R.mipmap.ivor_shadow_drawer, GravityCompat.START);

        // ActionBar配置
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // null为ActionBar上的图标
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, null,
                R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActivity().invalidateOptionsMenu();
            }
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivor_drawer1_ll:
                messageShow();
                break;
            case R.id.ivor_drawer2_ll:
                messageShow();
                break;
            case R.id.ivor_drawer3_ll:
                messageShow();
                break;
            case R.id.ivor_drawer4_ll:
                messageShow();
                break;
            case R.id.ivor_drawer5_ll:
                messageShow();
                break;
            case R.id.ivor_drawer6_ll:
                messageShow();
                break;
        }
        mDrawerLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                mDrawerLayout.closeDrawers();
            }
        }, 1000);
    }

    // 自定义Toast方法
    private void messageShow() {
        Toast.makeText(getActivity().getApplicationContext(), "小帅哥你成功啦！",
                Toast.LENGTH_SHORT).show();
    }

    // 取得ActionBar与父Activity的绑定关系
    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    // 对mDrawerToggle的开关状态进行配置
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
