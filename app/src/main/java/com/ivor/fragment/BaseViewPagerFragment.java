package com.ivor.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivor.util.PagerSlidingTabStrip;
import com.ivor.ui.R;
import com.ivor.adapter.ViewPagerFragmentAdapter;

/**
 * Description: 带有导航条的 Fragment 基类
 * * @author  Ivor
 */

public abstract class BaseViewPagerFragment extends Fragment {

    protected PagerSlidingTabStrip mTabStrip;
    protected ViewPager mViewPager;
    protected ViewPagerFragmentAdapter mTabsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ivor_viewpage_fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mTabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.ivor_pager_ts);
        mViewPager = (ViewPager) view.findViewById(R.id.ivor_pager_vp);

        // 绑定TabStrip和ViewPager
        mTabsAdapter = new ViewPagerFragmentAdapter(getChildFragmentManager(), mTabStrip, mViewPager);
        setScreenPageLimit();
        onSetupTabAdapter(mTabsAdapter);
    }
    
    protected void setScreenPageLimit() {

    }

    protected abstract void onSetupTabAdapter(ViewPagerFragmentAdapter adapter);

}