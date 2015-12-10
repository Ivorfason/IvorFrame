package com.ivor.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ivor.model.PhoneBean;
import com.ivor.ui.R;

/**
 * Description: 手机号码归属地查询 (Google Volley)
 * * @author  Ivor
 */

public class BFragment extends Fragment implements View.OnClickListener {

	private Button mSearchBtn;
	private Button mEGBtn;
	private EditText mPointET;
	private TextView mShowTV;

	private String Numberurl = "http://api.avatardata.cn/MobilePlace/LookUp";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.ivor_phonenumber, container, false);
		initView(v);
		initListener();
		return v;
	}

	private void initView(View v) {
		this.mSearchBtn = (Button) v.findViewById(R.id.ivor_phonesearch_btn);
		this.mEGBtn = (Button) v.findViewById(R.id.ivor_phoneeg_btn);
		this.mPointET = (EditText) v.findViewById(R.id.ivor_phone_et);
		this.mShowTV = (TextView) v.findViewById(R.id.ivor_phone_tv);

	}

	private void initListener() {
		mSearchBtn.setOnClickListener(this);
		mEGBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ivor_phonesearch_btn:
				if(TextUtils.isEmpty(mPointET.getText().toString())) {
					Toast.makeText(getActivity().getApplicationContext(), "请输入手机号码！", Toast.LENGTH_SHORT).show();
				} else {
					phonehttpurlConnectionget();
				}
				break;
			case R.id.ivor_phoneeg_btn:
				mPointET.setText("18710057085");
				break;
		}
	}

	private void phonehttpurlConnectionget() {
		String Baiduurl = Numberurl + "?key=a6373ddade05403aa16751f82e3a38f7&mobileNumber=" + mPointET.getText().toString();
		RequestQueue mQueue = Volley.newRequestQueue(getActivity());
		StringRequest stringRequest = new StringRequest(Request.Method.GET, Baiduurl,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.d("TAG", response);
						PhoneBean phoneBean = JSON.parseObject(response, PhoneBean.class);
						mShowTV.setBackgroundResource(0);
						mShowTV.setText(phoneBean.toString());
					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e("TAG", error.getMessage(), error);
			}
		});

		mQueue.add(stringRequest);
	}

}
