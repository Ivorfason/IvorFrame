package com.ivor.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.alibaba.fastjson.JSON;
import com.ivor.adapter.SayingListAdapter;
import com.ivor.model.DataBean;
import com.ivor.model.WordBean;
import com.ivor.ui.R;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

/**
 * Description: 成语字典
 * * @author  Ivor
 */

public class DFragment extends Fragment implements View.OnClickListener {

	private static final int kURLCONNECTION_GET_RESPONSE = 0x1;

	private Button mSearchBtn;
	private EditText mPointET;
	private ListView mListLV;

	private String Sayingurl = "http://apis.baidu.com/netpopo/idiom/chengyu";

	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case kURLCONNECTION_GET_RESPONSE:
					List<DataBean> mList = (List) msg.obj;
					for (int i = 0; i < (mList.size()); i++) {
						mList.get(i).setType(i % 2 == 0 ? 0 : 1);
					}
					final SayingListAdapter mSayingAdapter = new SayingListAdapter(getActivity(), mList);
					mListLV.setAdapter(mSayingAdapter);
					mListLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							mSayingAdapter.setCurrentItem(position);
							mSayingAdapter.notifyDataSetChanged();
						}
					});

					break;
				default:
					break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.ivor_sayinglist, container, false);
		initView(v);
		initListener();
		return v;
	}

	private void initView(View v) {

		this.mPointET = (EditText) v.findViewById(R.id.ivor_sayingpoint_et);
		this.mSearchBtn = (Button) v.findViewById(R.id.ivor_sayingsearch_btn);
		this.mListLV = (ListView) v.findViewById(R.id.ivor_sayinglist_lv);
	}

	private void initListener() {

		mPointET.setOnClickListener(this);
		mSearchBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ivor_sayingsearch_btn:
				SearchWord();
				break;
		}
	}

	public void SearchWord() {
		String Baiduurl = Sayingurl + "?keyword=" + mPointET.getText().toString() + "&appkey=1307ee261de8bbcf83830de89caae73f";
		OkHttpClient mOkHttpClient = new OkHttpClient();
		Request request = new Request.Builder()
				.url(Baiduurl)
				.addHeader("apikey","6c36e1ebba98b1c157d34cfe81c5ef3e")
				.build();
		Call call = mOkHttpClient.newCall(request);
		call.enqueue(new Callback() {
			@Override
			public void onResponse(Response response) throws IOException {
				String result = response.body().string().toString();
				WordBean wordBean = JSON.parseObject(result, WordBean.class);

				Message msg = Message.obtain();
				msg.what = kURLCONNECTION_GET_RESPONSE;
				msg.obj = wordBean.getData();
				mHandler.sendMessage(msg);
			}

			@Override
			public void onFailure(Request arg0, IOException arg1) {
				// TODO Auto-generated method stub
			}
		});

	}
}
