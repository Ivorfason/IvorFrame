package com.ivor.custom;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.net.Uri;
import android.os.Bundle;

import com.ivor.ui.R;

/**
 * Description: 自定义打电话样式 Dialog
 * * @author  Ivor
 */

public class CallDialog extends Dialog implements View.OnClickListener {
	
        private int layoutRes;      //布局文件
        private Context context;
        private TextView mDialogPhone1TV;
        private TextView mDialogPhone2TV;
        private TextView mDialogPhone3TV;
        
        public CallDialog(Context context) {
            super(context);
            this.context = context;
        }
        /**
         * 自定义布局的构造方法
         * @param context
         * @param resLayout
         */
        public CallDialog(Context context, int resLayout){
            super(context);
            this.context = context;
            this.layoutRes=resLayout;
        }
        /**
         * 自定义主题及布局的构造方法
         * @param context
         * @param theme
         * @param resLayout
         */
        public CallDialog(Context context, int theme, int resLayout){
            super(context, theme);
            this.context = context;
            this.layoutRes=resLayout;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.setContentView(layoutRes);
            
            initView();
            initListener();
        }
        
        private void initView() {
        	this.mDialogPhone1TV = (TextView) findViewById(R.id.aci_dialogphone1_tv);
        	this.mDialogPhone2TV = (TextView) findViewById(R.id.aci_dialogphone2_tv);
        	this.mDialogPhone3TV = (TextView) findViewById(R.id.aci_dialogphone3_tv);
        }
        
        private void initListener() {
        	mDialogPhone1TV.setOnClickListener(this);
        	mDialogPhone2TV.setOnClickListener(this);
        	mDialogPhone3TV.setOnClickListener(this);
        }
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
            case R.id.aci_dialogphone1_tv:
            	Intent i1 = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:10086"));
            	context.startActivity(i1);
            	this.dismiss();
                break;
            case R.id.aci_dialogphone2_tv:
            	Intent i2 = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:10010"));
            	context.startActivity(i2);
            	this.dismiss();
                break;
            case R.id.aci_dialogphone3_tv:
            	Intent i3 = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:10000"));
            	context.startActivity(i3);
             	this.dismiss();
                break;
        }
			
		}
    }