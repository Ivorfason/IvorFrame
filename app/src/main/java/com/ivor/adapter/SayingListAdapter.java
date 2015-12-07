package com.ivor.adapter;

import android.content.Context;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ivor.model.DataBean;
import java.util.List;
import com.ivor.ui.R;

public class SayingListAdapter extends BaseAdapter {

	private List<DataBean> data;
	private Context context;
	private LayoutInflater mInflater;

	private int mCurrentItem = 10000;
	private String mJoke = "<font color=red>请叫我</font><font color=blue>聪明的小帅哥！</font>";

	public SayingListAdapter(Context context, List<DataBean> data) {
		this.context = context;
		this.data = data;
		this.mInflater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(data==null) return 0;
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void setCurrentItem(int currentItem) {
		this.mCurrentItem = currentItem;
	}

	public static final class ViewHolderType {
		public TextView mSayingTV;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// int type = getItemViewType(position);
		ViewHolderType holderType = null;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.ivor_sayinglist_item, null);
			holderType = new ViewHolderType();
			holderType.mSayingTV = (TextView) convertView
					.findViewById(R.id.ivor_item_itemtext);
			convertView.setTag(holderType);
		} else {
			holderType = (ViewHolderType) convertView.getTag();
		}

		if (mCurrentItem == position) {
			holderType.mSayingTV.setText(Html.fromHtml(mJoke));
			holderType.mSayingTV.setGravity(Gravity.CENTER);
		} else {
			holderType.mSayingTV.setText(data.get(position).getName().toString()
					+ " (" + data.get(position).getPronounce().toString() + ")"
					+ "\n" + data.get(position).getContent().toString());
			holderType.mSayingTV.setGravity(Gravity.LEFT);
		}
		return convertView;
	}
	
	@Override
	public int getItemViewType(int position) {
		DataBean dataBean = data.get(position);
		int type = dataBean.getType();
		return type;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

}
