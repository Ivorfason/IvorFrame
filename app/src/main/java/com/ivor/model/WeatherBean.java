package com.ivor.model;

public class WeatherBean {

	private String errNum;
	private String errMsg;
	private ForecastBean retData;

	public String getErrNum() {
		return errNum;
	}

	public void setErrNum(String errNum) {
		this.errNum = errNum;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public ForecastBean getRetData() {
		return retData;
	}

	public void setRetData(ForecastBean retData) {
		this.retData = retData;
	}

	@Override
	public String toString() {
		return retData + "";
	}

	
}
