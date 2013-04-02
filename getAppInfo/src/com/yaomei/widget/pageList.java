package com.yaomei.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.yaomei.activity.adapter.SimpleAdapterForPageList;
import com.yaomei.activity.info.R;

public class pageList extends LinearLayout {

	private SimpleAdapterForPageList simpleAdapter;

	private onItemClickListener itemClick;
	private List<HashMap<String, Object>> app = new ArrayList<HashMap<String, Object>>();

	public pageList(Context context) {
		this(context, null);
	}

	public pageList(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	/**
	 * 改变图片状态
	 * 
	 * @param v
	 * @param list
	 */
	private void changeImageState(View v, List<HashMap<String, Object>> list) {
		int size = list.size();
		for (int i = 0; i < size; i++) {
			View view = (View) list.get(i).get("click");
			view.setBackgroundResource(R.drawable.top_num_normal);
			list.remove(i);
		}
		v.setBackgroundResource(R.drawable.top_num_focus);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("click", v);
		list.add(map);
	}

	public void setCheckPage(int position) {
		View view = this.getChildAt(position);

		changeImageState(view, app);
	}

	/**
	 * 通过循环添加布局
	 */
	private void bindLinearLayout() {
		final int count = simpleAdapter.getCount();

		for (int i = 0; i < count; i++) {

			final View view = simpleAdapter.getView(i, null, null);
			final int index = i;
			if (i == 0) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("click", view);
				app.add(map);
				view.setBackgroundResource(R.drawable.top_num_focus);
			}
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (itemClick != null) {
						itemClick.onItemClick(index, view);
						changeImageState(view, app);
					}
				}
			});

			LinearLayout.LayoutParams parms = new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			parms.leftMargin = 5;
			addView(view, parms);
		}

	}

	/**
	 * 添加数据源
	 * 
	 * @param adapter
	 */
	public void setSimpleAdapter(SimpleAdapterForPageList adapter) {
		this.simpleAdapter = adapter;
		bindLinearLayout();
	}

	/**
	 * 获取数据源
	 * 
	 * @return
	 */
	public SimpleAdapterForPageList getSimpleAdapterForPageList() {
		return simpleAdapter;
	}

	public interface onItemClickListener {
		public void onItemClick(int position, View fristView);
	}

	/**
	 * 设置项点击事件
	 * 
	 * @param click
	 */
	public void setOnItemClick(onItemClickListener click) {
		this.itemClick = click;
	}

}
