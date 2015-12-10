package com.ivor.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ScrollView;
import android.widget.TextView;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.ivor.util.AMapUtils;

/**
 * Description：AMap Location (高德地图定位：信息)
 * * @author  Ivor
 */

public class IvorLocation extends Activity implements OnCheckedChangeListener, OnClickListener, AMapLocationListener {

	private RadioGroup rgLocation;
	private RadioButton rbLocationContinue;
	private RadioButton rbLocationOnce;
	private View layoutInterval;
	private EditText etInterval;
	private CheckBox cbAddress;
	private TextView tvReult;
	private Button btLocation;
	private Button mMapBtn;
	private ImageView mBackIV;
	private ScrollView mShowSV;

	private AMapLocationClient mLocationClient = null;
	private AMapLocationClientOption mLocationOption = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ivor_location);
		initView();
		initListener();
	}

	private void initView() {
		rgLocation = (RadioGroup) findViewById(R.id.ivor_location_rg);
		rbLocationContinue = (RadioButton)findViewById(R.id.ivor_continueLocation_rb);
		rbLocationOnce = (RadioButton)findViewById(R.id.ivor_onceLocation_rb);
		layoutInterval = findViewById(R.id.ivor_interval_ll);
		etInterval = (EditText) findViewById(R.id.ivor_interval_et);
		cbAddress = (CheckBox) findViewById(R.id.ivor_needAddress_cb);
		tvReult = (TextView) findViewById(R.id.ivor_locationresult_tv);
		btLocation = (Button) findViewById(R.id.ivor_locationmes_btn);
		mMapBtn = (Button) findViewById(R.id.ivor_locationmap_btn);
		mBackIV = (ImageView) findViewById(R.id.ivor_locationback_iv);
		mShowSV = (ScrollView) findViewById(R.id.ivor_location_sv);


	}

	private void initListener() {
		rgLocation.setOnCheckedChangeListener(this);
		btLocation.setOnClickListener(this);
		mBackIV.setOnClickListener(this);
		mMapBtn.setOnClickListener(this);
		mLocationClient = new AMapLocationClient(this.getApplicationContext());
		mLocationOption = new AMapLocationClientOption();
		// 设置定位模式为低功耗模式
		mLocationOption.setLocationMode(AMapLocationMode.Battery_Saving);
		// 设置定位监听
		mLocationClient.setLocationListener(this);
	}



	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.ivor_continueLocation_rb:
			//只有持续定位设置定位间隔才有效，单次定位无效
			layoutInterval.setVisibility(View.VISIBLE);
			//设置为不是单次定位
			mLocationOption.setOnceLocation(false);
			break;
		case R.id.ivor_onceLocation_rb:
			//只有持续定位设置定位间隔才有效，单次定位无效
			layoutInterval.setVisibility(View.GONE);
			//设置为单次定位
			mLocationOption.setOnceLocation(true);
			break;
		}
	}

	/**
	 * 设置控件的可用状态
	 */
	private void setViewEnable(boolean isEnable) {
		rbLocationContinue.setEnabled(isEnable);
		rbLocationOnce.setEnabled(isEnable);
		etInterval.setEnabled(isEnable);
		cbAddress.setEnabled(isEnable);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ivor_locationmes_btn:
				if (btLocation.getText().equals("开始定位")) {
					setViewEnable(false);
					initOption();
					btLocation.setText("停止定位");
					// 设置定位参数
					mLocationClient.setLocationOption(mLocationOption);
					// 启动定位
					mLocationClient.startLocation();
					mHandler.sendEmptyMessage(AMapUtils.MSG_LOCATION_START);
				} else {
					setViewEnable(true);
					btLocation.setText("开始定位");
					// 停止定位
					mLocationClient.stopLocation();
					mHandler.sendEmptyMessage(AMapUtils.MSG_LOCATION_STOP);
				}
				break;
			case R.id.ivor_locationmap_btn:
				Intent i = new Intent(IvorLocation.this, IvorAMap.class);
				startActivity(i);
				break;
			case R.id.ivor_locationback_iv:
				IvorLocation.this.finish();
				break;
		}
	}

	// 根据控件的选择，重新设置定位参数
	private void initOption() {
		// 设置是否需要显示地址信息
		mLocationOption.setNeedAddress(cbAddress.isChecked());
		String strInterval = etInterval.getText().toString();
		if (!TextUtils.isEmpty(strInterval)) {
			/**
			 *  设置发送定位请求的时间间隔,最小值为2000，如果小于2000，按照2000算
			 *  只有持续定位设置定位间隔才有效，单次定位无效
			 */
			mLocationOption.setInterval(Long.valueOf(strInterval));
		}
	}

	Handler mHandler = new Handler(){
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case AMapUtils.MSG_LOCATION_START:
				tvReult.setText("正在定位...");
				break;
			//定位完成
			case AMapUtils.MSG_LOCATION_FINISH:
				AMapLocation loc = (AMapLocation)msg.obj;
				String result = AMapUtils.getLocationStr(loc);
				tvReult.setText(result);
				tvReult.setGravity(Gravity.LEFT);
				break;
			case AMapUtils.MSG_LOCATION_STOP:
				tvReult.setText("定位停止");
				break;
			default:
				break;
			}
		};
	};

	@Override
	public void onLocationChanged(AMapLocation loc) {
		if (null != loc) {
			Message msg = mHandler.obtainMessage();
			msg.obj = loc;
			msg.what = AMapUtils.MSG_LOCATION_FINISH;
			mHandler.sendMessage(msg);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (null != mLocationClient) {
			/**
			 * 如果AMapLocationClient是在当前Activity实例化的，
			 * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
			 */
			mLocationClient.onDestroy();
			mLocationClient = null;
			mLocationOption = null;
		}
	}

}
