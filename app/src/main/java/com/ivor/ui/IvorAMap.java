package com.ivor.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.MyLocationStyle;

/**
 * Description: AMap Location (高德地图定位：地图)
 * * @author  Ivor
 */

public class IvorAMap extends Activity implements View.OnClickListener, LocationSource, AMapLocationListener {

    private MapView mMapMV;
    private AMap mAMap;
    private ImageView mBackIV;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ivor_amap);

        mBackIV = (ImageView) findViewById(R.id.ivor_amapback_iv);
        mBackIV.setOnClickListener(this);
        // 调用savedInstanceState对MapView初始化
        mMapMV = (MapView) findViewById(R.id.ivor_locationmap_mv);
        mMapMV.onCreate(savedInstanceState);
        initAMap();
    }

    // 初始化AMap对象
    private void initAMap() {
        if (mAMap == null) {
            mAMap = mMapMV.getMap();
            setUpMap();
        }
    }

    private void setUpMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ivor_location_marker));    // 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.BLACK);    // 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));    // 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int);     //设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);      // 设置圆形的边框粗细
        mAMap.setMyLocationStyle(myLocationStyle);
        mAMap.setLocationSource(this);    // 设置定位监听
        mAMap.getUiSettings().setMyLocationButtonEnabled(true);    // 设置默认定位按钮是否显示
        mAMap.setMyLocationEnabled(true);    // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置缩放比例
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        // aMap.setMyLocationType();
    }

    @Override
    public void onLocationChanged(AMapLocation loc) {
        if (mListener != null && loc != null) {
            if (loc != null
                    && loc.getErrorCode() == 0) {
                mListener.onLocationChanged(loc);       // 显示系统小蓝点
            } else {
                String errText = "定位失败," + loc.getErrorCode() + ": " + loc.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivor_amapback_iv:
                IvorAMap.this.finish();
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mLocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mLocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

    // 重写生命周期中的方法对MapView监控
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapMV.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapMV.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapMV.onPause();
        deactivate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapMV.onDestroy();
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
