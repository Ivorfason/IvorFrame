package com.ivor.model;

public class NumberBean {

	private String mobilenumber;
	private String mobilearea;
	private String mobiletype;
	private String areacode;
	private String postcode;
	private String supplier;

	public String getMobilenumber() {
		return mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}

	public String getMobilearea() {
		return mobilearea;
	}

	public void setMobilearea(String mobilearea) {
		this.mobilearea = mobilearea;
	}

	public String getMobiletype() {
		return mobiletype;
	}

	public void setMobiletype(String mobiletype) {
		this.mobiletype = mobiletype;
	}

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	@Override
	public String toString() {
		return "您的手机信息为：\n\n城市：" + mobilearea +  "\n前缀："	+ mobilenumber
				+ "\n服务商：" + mobiletype + "\n区域：" + areacode + "\n邮编：" + postcode;
	}

}
