package com.ivor.custom;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ivor.ui.R;

/**
 * Description: 自定义名片样式 Dialog
 * * @author  Ivor
 */

public class CardDialog extends Dialog{

        private int layoutRes;      //布局文件
        private Context context;
        private TextView mDialogPhone1TV;
        private TextView mDialogPhone2TV;
        private TextView mDialogPhone3TV;

        public CardDialog(Context context) {
            super(context);
            this.context = context;
        }
        /**
         * 自定义布局的构造方法
         * @param context
         * @param resLayout
         */
        public CardDialog(Context context, int resLayout){
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
        public CardDialog(Context context, int theme, int resLayout){
            super(context, theme);
            this.context = context;
            this.layoutRes=resLayout;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.setContentView(layoutRes);
        }
        
}