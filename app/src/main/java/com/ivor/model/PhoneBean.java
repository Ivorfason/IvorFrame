package com.ivor.model;

public class PhoneBean {

	private String reason;
	private String error_code;
	private NumberBean result;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public NumberBean getResult() {
		return result;
	}

	public void setResult(NumberBean result) {
		this.result = result;
	}

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	@Override
	public String toString() {
		return result + "";
	}

}
