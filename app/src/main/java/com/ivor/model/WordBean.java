package com.ivor.model;

import java.util.List;

public class WordBean {
	private String total;
	List<DataBean> data;
	
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public List<DataBean> getData() {
		return data;
	}
	public void setData(List<DataBean> data) {
		this.data = data;
	}
}
