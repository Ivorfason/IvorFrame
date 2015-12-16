package com.ivor.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ivor.capricorn.ArcMenu;
import com.ivor.capricorn.RayMenu;

public class IvorTest extends AppCompatActivity {

    private ImageView mBackIV;
    private static final int[] ITEM_DRAWABLES = { R.mipmap.ivor_composer_camera, R.mipmap.ivor_composer_music,
            R.mipmap.ivor_composer_place, R.mipmap.ivor_composer_sleep, R.mipmap.ivor_composer_thought, R.mipmap.ivor_composer_with };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ivor_test);

        mBackIV = (ImageView) findViewById(R.id.ivor_searchback_iv);
        mBackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IvorTest.this.finish();
            }
        });

        RayMenu rayMenu = (RayMenu) findViewById(R.id.ivor_ray_menu);
        final int itemCount = ITEM_DRAWABLES.length;
        for (int i = 0; i < itemCount; i++) {
            ImageView item = new ImageView(this);
            item.setImageResource(ITEM_DRAWABLES[i]);

            final int position = i;
            rayMenu.addItem(item, new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(IvorTest.this, "欢迎小帅哥点击:" + position, Toast.LENGTH_SHORT).show();
                }
            });     // Add a menu item
        }
    }

}
