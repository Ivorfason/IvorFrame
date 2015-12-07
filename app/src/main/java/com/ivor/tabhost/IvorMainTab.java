package com.ivor.tabhost;

import com.ivor.fragment.AFragment;
import com.ivor.fragment.BFragment;
import com.ivor.fragment.CFragment;
import com.ivor.fragment.DFragment;
import com.ivor.ui.R;

public enum IvorMainTab {

	TAB0(0, R.string.ivor_main_tab1, R.drawable.ivor_icon0, AFragment.class),
	TAB1(1, R.string.ivor_main_tab2, R.drawable.ivor_icon1, BFragment.class),
	TAB2(2, R.string.ivor_main_tab0, R.drawable.ivor_icon0, null),
	TAB3(3, R.string.ivor_main_tab3, R.drawable.ivor_icon3, CFragment.class),
	TAB4(4, R.string.ivor_main_tab4, R.drawable.ivor_icon4,	DFragment.class);

	private int idx;
	private int resName;
	private int resIcon;
	private Class<?> clz;

	IvorMainTab(int idx, int resName, int resIcon, Class<?> clz) {
		this.idx = idx;
		this.resName = resName;
		this.resIcon = resIcon;
		this.clz = clz;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public int getResName() {
		return resName;
	}

	public void setResName(int resName) {
		this.resName = resName;
	}

	public int getResIcon() {
		return resIcon;
	}

	public void setResIcon(int resIcon) {
		this.resIcon = resIcon;
	}

	public Class<?> getClz() {
		return clz;
	}

	public void setClz(Class<?> clz) {
		this.clz = clz;
	}
}
