package com.ivor.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ivor.ui.R;

/**
 * Description: Viewpager + Fragment：子Fragment
 * * @author  Ivor
 */

public class ANewsFragment extends Fragment {

    private int mCatalog;               // 定义传回的整型参数字段

    @Override
    public void onAttach(Activity activity) {
        // 取得AFragment中Bundle传回的参数
        Bundle args = getArguments();
        if (args != null) {
            mCatalog = args.getInt("Data");
        }
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = null;
        switch (mCatalog) {
            case 0:
                v = inflater.inflate(R.layout.a_fragment, container, false);
                break;
            case 1:
                v = inflater.inflate(R.layout.b_fragment, container, false);
                break;
            case 2:
                v = inflater.inflate(R.layout.c_fragment, container, false);
                break;
            case 3:
                v = inflater.inflate(R.layout.d_fragment, container, false);
                break;
        }
        return v;
    }



}
