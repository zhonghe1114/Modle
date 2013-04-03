package com.yaomei.activity.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaomei.activity.info.R;
import com.yaomei.model.AppsModel;

public class AppsAdapter extends BaseAdapter {

	private List<AppsModel> mAppsModel;

	private AppsModel info;

	private Context context;

	public AppsAdapter(Context context, List<AppsModel> info) {
		this.context = context;
		this.mAppsModel = info;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mAppsModel.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mAppsModel.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(context);
		viewHolder holder;
		if (convertView == null) {
			holder = new viewHolder();
			convertView = inflater.inflate(R.layout.grid_row_layout, null);
			holder.app_icon = (ImageView) convertView
					.findViewById(R.id.btn_appicon);
			holder.layout = (LinearLayout) convertView
					.findViewById(R.id.grid_layout);
			holder.app_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.app_temp = (TextView) convertView.findViewById(R.id.folder);
			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
		}
		info = mAppsModel.get(position);

		holder.app_icon.setBackgroundDrawable(info.getActivity_Icon());
		holder.layout.setOnClickListener(new btnClickEvent(info));
		holder.app_name.setText(info.getActivity_text());
		holder.app_temp.setText(info.getPackage_Name() + "-"
				+ info.getActivity_Name());
		return convertView;
	}

	public class btnClickEvent implements OnClickListener {

		private AppsModel resole;

		public btnClickEvent(AppsModel info) {
			resole = info;
		}

		@Override
		public void onClick(View v) {
			Intent intent = null;
			System.out.println(resole.getPackage_Name());
			if (resole.getPackage_Name().equals("com.android.contacts")) {
				if (resole.getActivity_Name().equals(
						"com.android.contacts.DialtactsActivity")) {
					intent = new Intent(Intent.ACTION_DIAL);
				}
				if (resole.getActivity_Name().equals(
						"com.android.contacts.DialtactsContactsEntryActivity")) {
					intent = new Intent(Intent.ACTION_CALL_BUTTON);
				}
			} else {
				intent = context.getPackageManager().getLaunchIntentForPackage(
						resole.getPackage_Name());
			}
			context.startActivity(intent);
			//

		}

	}

	public class viewHolder {
		private ImageView app_icon;
		private TextView app_name, app_temp;
		private LinearLayout layout;
	}

}
