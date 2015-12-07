package com.ivor.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.ivor.tabhost.OnTabReselectListener;
import com.ivor.ui.R;
import com.ivor.adapter.ViewPagerFragmentAdapter;

/**
 * Description: ViewPager + Fragment：复用
 * * @author  Ivor
 */

public class AFragment extends BaseViewPagerFragment implements OnTabReselectListener {

	@Override
	protected void onSetupTabAdapter(ViewPagerFragmentAdapter adapter) {

		String[] title = getResources().getStringArray(R.array.ivor_pageA);
		adapter.addTab(title[0], "data0", ANewsFragment.class, getBundle(0));
		adapter.addTab(title[1], "data1", ANewsFragment.class, getBundle(1));
		adapter.addTab(title[2], "data2", ANewsFragment.class, getBundle(2));
		adapter.addTab(title[3], "data3", ANewsFragment.class, getBundle(3));
	}

	/**
	 * 基类会根据不同的catalog展示相应的数据
	 */

	private Bundle getBundle(int catalog) {
		Bundle bundle = new Bundle();
		bundle.putInt("Data", catalog);
		return bundle;
	}

	@Override
	protected void setScreenPageLimit() {
		mViewPager.setOffscreenPageLimit(3);
	}

	@Override
	public void onTabReselect() {
		try {
			int currentIndex = mViewPager.getCurrentItem();
			Fragment currentFragment = getChildFragmentManager().getFragments()
					.get(currentIndex);
			if (currentFragment != null
					&& currentFragment instanceof OnTabReselectListener) {
				OnTabReselectListener listener = (OnTabReselectListener) currentFragment;
				listener.onTabReselect();
			}
		} catch (NullPointerException e) {
		}
	}
}
