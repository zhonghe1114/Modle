package com.yaomei.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaomei.activity.adapter.AppsAdapter;
import com.yaomei.activity.info.R;

public class gridViewExt extends LinearLayout {
	public List<HashMap<String, Object>> tableRowsList;
	private List<HashMap<String, Object>> app = new ArrayList<HashMap<String, Object>>();
	private AppsAdapter adapter;

	onItemClickListener onItemClickEvent;
	onLongPressExt onLongPress;
	int checkRowID = -1; // 选中行的下标
	int checkColumnID = -1; // 选中列的下标
	int lastRowCount = -1; // 最后一行的总数
	private int ColumnCount; // 每列的总数

	public void setColumnCount(int count) {
		this.ColumnCount = count;
	}

	public int getColumnCount() {
		return ColumnCount;
	}

	public interface onItemClickListener {
		public boolean onItemClick(int position, MotionEvent event, View view);
	}

	public interface onLongPressExt {
		public boolean onLongPress(View view);
	}

	public gridViewExt(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public gridViewExt(Context context, AttributeSet attrs) {
		super(context, attrs);
		int resouceID = -1;
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.GridViewExt);
		int N = typedArray.getIndexCount();
		for (int i = 0; i < N; i++) {
			int attr = typedArray.getIndex(i);
			switch (attr) {
			case R.styleable.GridViewExt_ColumnCount:
				resouceID = typedArray.getInt(
						R.styleable.GridViewExt_ColumnCount, 0);
				setColumnCount(resouceID);
				break;

			}
		}
		typedArray.recycle();
	}

	public void setOnItemClickListener(onItemClickListener click) {
		this.onItemClickEvent = click;
	}

	public void setOnLongPressListener(onLongPressExt longPress) {
		this.onLongPress = longPress;
	}

	public void NotifyDataChange() {
		removeAllViews();
	}

	void bindView() {
		removeAllViews();
		int count = adapter.getCount();
		TableCell[] cell = null;
		int j = 0;
		LinearLayout layout;
		tableRowsList = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < count; i++) {
			j++;
			final int position = i;
			if (j > getColumnCount() || i == 0) {
				cell = new TableCell[getColumnCount()];
			}

			final View view = adapter.getView(i, null, null);

			view.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					unCheckPressed();
					checkRowID = -1;
					checkColumnID = -1;
					if (onItemClickEvent != null) {

						onItemClickEvent.onItemClick(position, event, view);
					}
					return false;
				}
			});

			view.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					if (onLongPress != null) {
						onLongPress.onLongPress(v);
					}
					return true;
				}
			});
			cell[j - 1] = new TableCell(view);
			if (j == getColumnCount()) {
				lastRowCount = j;
				j = 0;
				HashMap<String, Object> map = new HashMap<String, Object>();
				TableRow tr = new TableRow(cell);
				map.put("tableRow", tr);
				tableRowsList.add(map);
				layout = new LinearLayout(getContext());
				addLayout(layout, cell, tr.getSize(), tr);

			} else if (i >= count - 1 && j > 0) {
				lastRowCount = j;
				HashMap<String, Object> map = new HashMap<String, Object>();
				TableRow tr = new TableRow(cell);
				map.put("tableRow", tr);
				tableRowsList.add(map);
				layout = new LinearLayout(getContext());
				addLayout(layout, cell, j, tr);
			}

		}

	}

	private void addLayout(LinearLayout layout, TableCell[] cell, int size,
			TableRow tr) {

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(130,
				110);
		layout.setGravity(Gravity.LEFT);

		layout.setOrientation(LinearLayout.HORIZONTAL);
		for (int k = 0; k < size; k++) {
			View remoteView = (View) tr.getCellValue(k).getValue();
			layout.addView(remoteView, k, params);
		}
		LinearLayout.LayoutParams firstParams = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		firstParams.leftMargin = 60;
		addView(layout, firstParams);
	}

	public void setAdapter(AppsAdapter appsAdapter) {
		this.adapter = appsAdapter;
		this.setOrientation(LinearLayout.VERTICAL);
		bindView();
	}

	public void checkPressed(int tableRowId, int tableRowColumnId) {
		ViewGroup view = (ViewGroup) this.getChildAt(tableRowId);

		checkColumnID = tableRowColumnId;
		checkRowID = tableRowId;
		changeImageState(view.getChildAt(tableRowColumnId), app);

	}

	public void onClick(int tableRowId, int tableRowColumnId, Context context) {
		LinearLayout view = (LinearLayout) ((ViewGroup) this
				.getChildAt(tableRowId)).getChildAt(tableRowColumnId);

		TextView tv = (TextView) view.findViewById(R.id.folder);
		final String[] name = tv.getText().toString().split("-");
		Intent intent = null;
		if (name[0].toString().equals("com.android.contacts")) {
			if (name[1].toString().equals(
					"com.android.contacts.DialtactsActivity")) {
				intent = new Intent(Intent.ACTION_DIAL);
			}
			if (name[1].toString().equals(
					"com.android.contacts.DialtactsContactsEntryActivity")) {
				intent = new Intent(Intent.ACTION_CALL_BUTTON);
			}
		} else {
			intent = getContext().getPackageManager()
					.getLaunchIntentForPackage(name[0].toString());
		}
		context.startActivity(intent);

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
			View view = (View) list.get(i).get("touch");
			view.setPressed(false);
			list.remove(i);
		}
		v.setPressed(true);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("touch", v);
		list.add(map);

	}

	public void unCheckPressed() {
		if (checkColumnID != -1 && checkRowID != -1) {
			ViewGroup view = (ViewGroup) this.getChildAt(checkRowID);
			view.getChildAt(checkColumnID).setPressed(false);

		}
	}

	public class TableRow {
		private TableCell[] cell;

		public TableRow(TableCell[] cell) {
			this.cell = cell;
		}

		public int getSize() {
			return cell.length;
		}

		public TableCell getCellValue(int index) {
			if (index >= getSize()) {
				return null;
			}
			return cell[index];
		}

		public int getCellCount() {

			return cell.length;

		}

		public int getLastCellCount() {
			return lastRowCount;
		}
	}

	static public class TableCell {
		private Object value;

		public TableCell(Object value) {
			this.value = value;
		}

		public Object getValue() {
			return value;
		}
	}

}
