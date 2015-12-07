package com.ivor.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.ivor.custom.CustomDialog;
import com.ivor.fragment.DrawerFragment;
import com.ivor.tabhost.IvorMainTab;
import com.ivor.tabhost.IvorFragmentTabHost;
import com.ivor.tabhost.OnTabReselectListener;

/**
 * Description: TabHostFragment + DrawerLayout
 * * @author  Ivor
 */

public class IvorMain extends ActionBarActivity implements View.OnClickListener,
                        TabHost.OnTabChangeListener, View.OnTouchListener {

    public IvorFragmentTabHost mTabHost;
    private CharSequence mTitle;
    private ImageView mAddBt;
    private DrawerFragment mDrawerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ivor_main);
        initView();
        initListener();
        initTabs();
    }

    private void initView() {
        mTitle = getTitle();
        mAddBt = (ImageView) this.findViewById(R.id.ivor_quickoption_iv);
        mTabHost = (IvorFragmentTabHost) this.findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.ivor_mytab_fl);
        if (android.os.Build.VERSION.SDK_INT > 10) {
            mTabHost.getTabWidget().setShowDividers(0);
        }
        mDrawerFragment = (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.ivor_drawer_fr);
        mDrawerFragment.setUp((DrawerLayout) findViewById(R.id.ivor_drawer_dl));
    }

    private void initListener() {
        mAddBt.setOnClickListener(this);
        mTabHost.setCurrentTab(0);
        mTabHost.setOnTabChangedListener(this);

    }

    private void initTabs() {
        IvorMainTab[] tabs = IvorMainTab.values();
        final int size = tabs.length;
        for (int i = 0; i < size; i++) {
            IvorMainTab ivorMainTab = tabs[i];
            TabHost.TabSpec tab = mTabHost.newTabSpec(getString(ivorMainTab.getResName()));
            View indicator = LayoutInflater.from(getApplicationContext()).inflate(R.layout.ivor_indicator_tab, null);
            TextView title = (TextView) indicator.findViewById(R.id.ivor_tabtitle_tv);
            Drawable drawable = this.getResources().getDrawable(ivorMainTab.getResIcon());
            title.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            if (i == 2) {
                indicator.setVisibility(View.INVISIBLE);
                mTabHost.setNoTabChangedTag(getString(ivorMainTab.getResName()));
            }
            title.setText(getString(ivorMainTab.getResName()));
            tab.setIndicator(indicator);
            tab.setContent(new TabHost.TabContentFactory() {
                @Override
                public View createTabContent(String tag) {
                    return new View(IvorMain.this);
                }
            });
            mTabHost.addTab(tab, ivorMainTab.getClz(), null);
            mTabHost.getTabWidget().getChildAt(i).setOnTouchListener(this);
        }
    }

    @Override
    public void onTabChanged(String tabId) {
        final int size = mTabHost.getTabWidget().getTabCount();
        for (int i = 0; i < size; i++) {
            View v = mTabHost.getTabWidget().getChildAt(i);
            if (i == mTabHost.getCurrentTab()) {
                v.setSelected(true);
            } else {
                v.setSelected(false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            // 点击了快速操作按钮
            case R.id.ivor_quickoption_iv:
                CustomDialog dialog=new CustomDialog(this, R.style.customDialog, R.layout.ivor_custom_dialog);
                dialog.show();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        super.onTouchEvent(event);
        boolean consumed = false;
        if (event.getAction() == MotionEvent.ACTION_DOWN && v.equals(mTabHost.getCurrentTabView())) {
            Fragment currentFragment = getCurrentFragment();
            if (currentFragment != null && currentFragment instanceof OnTabReselectListener) {
                OnTabReselectListener listener = (OnTabReselectListener) currentFragment;
                listener.onTabReselect();
                consumed = true;
            }
        }
        return consumed;
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentByTag(mTabHost.getCurrentTabTag());
    }

    // 含actionbar的menu进行配置
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ivor_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ivor_actionbar_search:
                Toast.makeText(this.getApplicationContext(), "小帅哥你正在搜索！",
                        Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
