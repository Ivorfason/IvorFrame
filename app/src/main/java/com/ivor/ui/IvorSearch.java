package com.ivor.ui;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Description: ViewPager轮播 + 定制搜索
 * * @author  Ivor
 */

public class IvorSearch extends Activity implements View.OnClickListener {

    private int[] images = null;                            //图片资源ID
    private String[] titles = null;                         //标题
    private ArrayList<ImageView> imageSource = null;        //图片资源
    private ArrayList<View> dots = null;                    //点
    private TextView title = null;
    private ViewPager viewPager;                            //用于显示图片
    private MyPagerAdapter  adapter;                        //viewPager的适配器
    private ImageView mBackIV;
    private Button mSearchBtn;
    private EditText mSearchET;
    private  int currPage = 0;                              //当前显示的页
    private  int oldPage = 0;                               //上一次显示的页

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ivor_search);
        initData();
        initView();
        initListener();
    }

    private void initView() {
        //图片的标题
        title = (TextView) findViewById(R.id.ivor_search_title);
        title.setText(titles[0]);
        //显示图片的VIew
        viewPager = (ViewPager) findViewById(R.id.ivor_search_vp);
        //为viewPager设置适配器
        adapter = new MyPagerAdapter();
        viewPager.setAdapter(adapter);
        mBackIV = (ImageView) findViewById(R.id.ivor_searchback_iv);
        mSearchBtn = (Button) findViewById(R.id.ivor_searchweb_btn);
        mSearchET = (EditText) findViewById(R.id.ivor_searchweb_et);
    }

    public void initData() {
        images = new int[] {
                R.mipmap.ivor_search_a,
                R.mipmap.ivor_search_b,
                R.mipmap.ivor_search_c,
                R.mipmap.ivor_search_d,
                R.mipmap.ivor_search_e
        };
        titles = new String[] {
                "Android First",
                "Android Second",
                "Android Third",
                "Android Forth",
                "Andorid Fifth"
        };

        //将要显示的图片放到list集合中
        imageSource = new ArrayList<ImageView>();
        for(int i = 0; i < images.length; i++) {
            ImageView image = new ImageView(this);
            image.setBackgroundResource(images[i]);
            imageSource.add(image);
        }

        //获取显示的点（即文字下方的点，表示当前是第几张）
        dots = new ArrayList<View>();
        dots.add(findViewById(R.id.ivor_searchdot1_v));
        dots.add(findViewById(R.id.ivor_searchdot2_v));
        dots.add(findViewById(R.id.ivor_searchdot3_v));
        dots.add(findViewById(R.id.ivor_searchdot4_v));
        dots.add(findViewById(R.id.ivor_searchdot5_v));

        //开启定时器，每隔2秒自动播放下一张（通过调用线程实现）（与Timer类似，可使用Timer代替）
        ScheduledExecutorService scheduled = Executors.newSingleThreadScheduledExecutor();
        //设置一个线程，该线程用于通知UI线程变换图片
        ViewPagerTask pagerTask = new ViewPagerTask();
        scheduled.scheduleAtFixedRate(pagerTask, 2, 2, TimeUnit.SECONDS);
    }

    private void initListener() {
        //为viewPager添加监听器，该监听器用于当图片变换时，标题和点也跟着变化
        MyPageChangeListener listener = new MyPageChangeListener();
        viewPager.setOnPageChangeListener(listener);
        mBackIV.setOnClickListener(this);
        mSearchBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivor_searchback_iv:
                IvorSearch.this.finish();
                break;
            case R.id.ivor_searchweb_btn:
                Intent i = new Intent(IvorSearch.this, IvorWebView.class);
                i.putExtra("url", "https://www.baidu.com/s?wd=" + mSearchET.getText().toString());
                startActivity(i);
                break;
        }
    }

    //	ViewPager每次仅最多加载三张图片（有利于防止发送内存溢出）
    private class MyPagerAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            System.out.println("getCount");
            return images.length;
        }
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            //判断将要显示的图片是否和现在显示的图片是同一个
            //arg0为当前显示的图片，arg1是将要显示的图片
            System.out.println("isViewFromObject");
            return arg0 == arg1;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            System.out.println("destroyItem==" + position);
            //销毁该图片
            container.removeView(imageSource.get(position));
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //初始化将要显示的图片，将该图片加入到container中，即viewPager中
            container.addView(imageSource.get(position));
            System.out.println("instantiateItem===" + position +"===="+container.getChildCount());
            return imageSource.get(position);
        }
    }

    //监听ViewPager的变化
    private class MyPageChangeListener implements OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {
            //当显示的图片发生变化之后
            //设置标题
            title.setText(titles[position]);
            //改变点的状态
            dots.get(position).setBackgroundResource(R.drawable.ivor_searchdot_focus);
            dots.get(oldPage).setBackgroundResource(R.drawable.ivor_searchdot_normal);
            //记录的页面
            oldPage = position;
            currPage = position;
        }
    }

    private class ViewPagerTask implements Runnable {
        @Override
        public void run() {
            //改变当前页面的值
            currPage = (currPage+ 1) % images.length;
            //发送消息给UI线程
            handler.sendEmptyMessage(0);
        }
    }

    private Handler handler= new Handler() {
        public void handleMessage(Message msg) {
            //接收到消息后，更新页面
            viewPager.setCurrentItem(currPage);
        };
    };


}
