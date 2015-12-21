package com.ivor.custom;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.ivor.capricorn.ArcMenu;
import com.ivor.ui.IvorAMap;
import com.ivor.ui.R;

/**
 * Description: 自定义 QuickOption Dialog
 * * @author  Ivor
 */

public class QuickOptionDialog extends Dialog {

        private int layoutRes;      //布局文件
        private Context context;
        private ArcMenu arcMenu;
        private static final int[] ITEM_DRAWABLES = { R.mipmap.ivor_composer_camera, R.mipmap.ivor_composer_music,
            R.mipmap.ivor_composer_place, R.mipmap.ivor_composer_thought, R.mipmap.ivor_composer_with };

        public QuickOptionDialog(Context context) {
            super(context);
            this.context = context;
        }
        /**
         * 自定义布局的构造方法
         * @param context
         * @param resLayout
         */
        public QuickOptionDialog(Context context, int resLayout){
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
        public QuickOptionDialog(Context context, int theme, int resLayout){
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
            this.arcMenu = (ArcMenu) findViewById(R.id.ivor_quickoption_menu);
        }
        
        private void initListener() {
            initArcMenu(arcMenu, ITEM_DRAWABLES);
        }

        private void initArcMenu(ArcMenu menu, int[] itemDrawables) {
            final int itemCount = itemDrawables.length;
            for (int i = 0; i < itemCount; i++) {
                ImageView item = new ImageView(context);
                item.setImageResource(itemDrawables[i]);

                final int position = i;
                menu.addItem(item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (position) {
                            case 0:
                                dismiss();
                                Intent i1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                context.startActivity(i1);
                                break;
                            case 1:
                                Toast.makeText(getContext(), "音乐功能敬请期待！", Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                dismiss();
                                Intent i2 = new Intent(getContext(), IvorAMap.class);
                                context.startActivity(i2);
                                break;
                            case 3:
                                dismiss();
                                CallDialog dialog1 = new CallDialog(context, R.style.customDialog, R.layout.ivor_call_dialog);
                                dialog1.show();
                                break;
                            case 4:
                                dismiss();
                                CardDialog dialog2 = new CardDialog(context, R.style.customDialog, R.layout.ivor_card_dialog);
                                dialog2.show();
                                break;
                        }
                    }
                });
            }
        }

    }