package com.ivor.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.ivor.model.PhoneBean;
import com.ivor.ui.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Description: 手机号码归属地查询
 * * @author  Ivor
 */

public class BFragment extends Fragment implements View.OnClickListener {

	private static final int kURLCONNECTION_GET_RESPONSE = 0x1;

	private Button mSearchBtn;
	private Button mEGBtn;
	private EditText mPointET;
	private TextView mShowTV;

	private String Numberurl = "http://api.avatardata.cn/MobilePlace/LookUp";

	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case kURLCONNECTION_GET_RESPONSE:
					mShowTV.setBackgroundResource(0);
					mShowTV.setText((CharSequence) msg.obj);
					break;
				default:
					break;
			}
		};
	};

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
					new Thread(new Runnable() {
						@Override
						public void run() {
							phonehttpurlConnectionget();
						}
					}).start();
				}
				break;
			case R.id.ivor_phoneeg_btn:
				mPointET.setText("18710057085");
				break;
		}
	}

	private void phonehttpurlConnectionget() {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		String Baiduurl = Numberurl + "?key=a6373ddade05403aa16751f82e3a38f7&mobileNumber=" + mPointET.getText().toString();
		try {
			URL url = new URL(Baiduurl);
			// TODO
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("apikey", "6c36e1ebba98b1c157d34cfe81c5ef3e");
			connection.connect();
			// TODO
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
			Log.e("PhoneBean", result);
			PhoneBean phoneBean = JSON.parseObject(result, PhoneBean.class);
			Log.e("PhoneBean", phoneBean.toString());

			Message msg = Message.obtain();
			msg.what = kURLCONNECTION_GET_RESPONSE;
			msg.obj = phoneBean.toString();
			mHandler.sendMessage(msg);

		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}
