package com.yaomei.activity.info;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ViewFlipper;

import com.yaomei.activity.adapter.AppsAdapter;
import com.yaomei.activity.adapter.SimpleAdapterForPageList;
import com.yaomei.dbHelper.sqliteHelperUtil;
import com.yaomei.model.AppsModel;
import com.yaomei.widget.gridViewExt;
import com.yaomei.widget.pageList;
import com.yaomei.widget.gridViewExt.TableRow;
import com.yaomei.widget.pageList.onItemClickListener;

public class infoActivity extends Activity implements OnGestureListener,
		OnTouchListener {
	private gridViewExt gridview;
	private List<ResolveInfo> mApps;
	private List<AppsModel> mAppsModel;
	private sqliteHelperUtil dbHelper;
	private AppsAdapter adapter;
	private SimpleAdapterForPageList simpleAdapter;
	private pageList page;
	private ViewFlipper viewFlipper;
	private int mCurrentLayoutState = 0;
	private static final int FLING_MIN_DISTANCE = 100;
	private static final int FLING_MIN_VELOCITY = 200;
	private GestureDetector mGestureDetector;
	private int pageIndxe = 0;

	int tableRowId = -1;
	int tableColumnId = -1;
	int RowCount = 0;
	int ColumnCountByRow = 0;
	int appCount = -1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.main);
		dbHelper = new sqliteHelperUtil(this);
		loadApp();
		findView();
		// 注册一个用于手势识别的类
		mGestureDetector = new GestureDetector(this);
		// 给mFlipper设置一个listener
		viewFlipper.setOnTouchListener(this);
		viewFlipper.setOnClickListener(new viewFlipperClickEvent());
		mCurrentLayoutState = 0;
		// 允许长按住ViewFlipper,这样才能识别拖动等手势
		viewFlipper.setLongClickable(true);
		init();
		appCount = adapter.getCount();
	}

	void findView() {

		page = (pageList) findViewById(R.id.myPage);
		viewFlipper = (ViewFlipper) findViewById(R.id.myFlipper);

	}

	void init() {

		simpleAdapter = new SimpleAdapterForPageList(this, dbHelper.getPage(),
				R.layout.page_text, new String[] { "num" },
				new int[] { R.id.itemText });
		int count = simpleAdapter.getCount();
		page.setSimpleAdapter(simpleAdapter);

		adapter = new AppsAdapter(this, mAppsModel);

		page.setOnItemClick(new pageItemClickEvent());
		mCurrentLayoutState = 0;

		for (int i = 0; i < count; i++) {
			gridview = new gridViewExt(getApplicationContext());
			viewFlipper.addView(gridview);
		}
		gridViewExt grid = (gridViewExt) viewFlipper
				.getChildAt(mCurrentLayoutState);
		grid.setColumnCount(5);
		grid.setAdapter(adapter);

	}

	/**
	 * 指定跳转到某个页面
	 * 
	 * @param switchTo
	 */
	public void switchLayoutStateTo(int switchTo) {
		while (mCurrentLayoutState != switchTo) {
			if (mCurrentLayoutState > switchTo) {
				mCurrentLayoutState--;

				viewFlipper.showPrevious();
			} else {
				mCurrentLayoutState++;

				viewFlipper.showNext();
			}
		}
		pageIndxe = mCurrentLayoutState;
	}

	/**
	 * 加载应用程序
	 */
	void loadApp() {
		Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		mApps = getPackageManager().queryIntentActivities(intent, 0);
		checkAppIsRight(mApps);
		mAppsModel = new ArrayList<AppsModel>();
		for (ResolveInfo info : mApps) {
			if (!info.activityInfo.packageName.equals(getPackageName())) {
				if (!dbHelper.isExistsByPackageName(info.activityInfo.name)) {
					AppsModel model = new AppsModel();

					model.setActivity_Name(info.activityInfo.name);
					model.setPackage_Name(info.activityInfo.packageName);
					dbHelper.insertAppInfo(model);
				}
			}
		}
		loadPage(0, mApps);

	}

	/**
	 * 加载分页数据
	 * 
	 * @param pageNum
	 * @param info
	 */
	private void loadPage(final int pageNum, final List<ResolveInfo> info) {

		Cursor temp = dbHelper.queryPageById(pageNum);
		String packageName = "";
		int count = temp.getCount();

		for (int i = 0; i < count; i++) {
			temp.moveToPosition(i);

			packageName = temp.getString(1);
			for (ResolveInfo resolver : info) {
				if (resolver.activityInfo.name.equals(packageName)) {

					AppsModel appsModel = new AppsModel();
					appsModel.setActivity_Icon(resolver
							.loadIcon(getPackageManager()));
					appsModel.setActivity_Name(resolver.activityInfo.name);
					appsModel.setPackage_Name(temp.getString(2));
					appsModel.setActivity_text(resolver.activityInfo
							.loadLabel(getPackageManager()));
					mAppsModel.add(appsModel);
				}
			}
		}
		temp.close();

	}

	/**
	 * 验证系统应用程序是否数量小于数据库保存的数量，如果小于则删除不符合的数据
	 * 
	 * @param info
	 */
	private void checkAppIsRight(List<ResolveInfo> info) {
		int i = 0;
		if (dbHelper.getAllName() != null) {
			String[] packageNames = dbHelper.getAllName();
			if (info.size() < dbHelper.getAppsCount()) {
				for (String packageName : packageNames) {
					for (ResolveInfo resolver : info) {
						if (packageName
								.equals(resolver.activityInfo.packageName)) {
							i++;
						}
					}
					if (i == 0) {
						dbHelper.deleteAppByPackageName(packageName);
					}
				}
			}
		}
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	public class pageItemClickEvent implements onItemClickListener {

		@Override
		public void onItemClick(int position, View firstView) {
			pageIndxe = position;
			loadGrid(pageIndxe);
			switchLayoutStateTo(pageIndxe);//
			appCount = adapter.getCount();
			tableRowId = -1;
			tableColumnId = -1;
		}

	}

	private void loadGrid(int pagePosition) {
		mAppsModel.clear();
		loadPage(pagePosition, mApps);
		gridViewExt itemGrid = (gridViewExt) viewFlipper
				.getChildAt(pagePosition);
		itemGrid.setColumnCount(5);
		itemGrid.setAdapter(new AppsAdapter(infoActivity.this, mAppsModel));

	}

	/**
	 * 向左划动
	 */
	private void loadPageByLeft() {
		if (pageIndxe < simpleAdapter.getCount() - 1) {

			pageIndxe++;

			loadGrid(pageIndxe);
			page.setCheckPage(pageIndxe);//
			viewFlipper.showNext();// 
			mCurrentLayoutState = pageIndxe;
		}
	}

	/**
	 * 向右划动
	 */
	private void loadPageByRight() {
		if (pageIndxe > 0) {
			pageIndxe--;

			loadGrid(pageIndxe);
			page.setCheckPage(pageIndxe);
			viewFlipper.showPrevious();
			mCurrentLayoutState = pageIndxe;
		}
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {

		if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
				&& Math.abs(velocityX) > FLING_MIN_VELOCITY) {
			loadPageByLeft();

		} else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
				&& Math.abs(velocityX) > FLING_MIN_VELOCITY) {

			loadPageByRight();
		}
		tableRowId = -1;
		tableColumnId = -1;
		appCount = adapter.getCount();
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return mGestureDetector.onTouchEvent(event);
	}

	public class viewFlipperClickEvent implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			gridViewExt gv = (gridViewExt) viewFlipper.getChildAt(pageIndxe);
			gv.unCheckPressed();
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		gridViewExt gv = (gridViewExt) viewFlipper.getChildAt(pageIndxe);
		RowCount = gv.tableRowsList.size();
		super.onKeyDown(keyCode, event);
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_UP:
			if (RowCount > 0) {
				if (tableColumnId == -1 && tableRowId == -1) {
					tableColumnId = 0;
					tableRowId = 0;
					gv.checkPressed(tableRowId, tableColumnId);
				}
				if (tableRowId > 0) {
					tableRowId--;
					gv.checkPressed(tableRowId, tableColumnId);
				} else {
					if (tableRowId == 0) {

						tableRowId = RowCount - 1;
						TableRow tr = (TableRow) gv.tableRowsList.get(
								tableRowId).get("tableRow");
						if (tableColumnId > tr.getLastCellCount() - 1) {
							tableColumnId = tr.getLastCellCount() - 1;
						}
					}
				}

			}

			break;

		case KeyEvent.KEYCODE_DPAD_DOWN:
			if (RowCount > 0) {
				if (tableColumnId == -1 && tableRowId == -1) {
					tableColumnId = 0;
					tableRowId = 0;
				} else {
					if (tableRowId == RowCount - 1) {
						tableRowId = 0;
					} else {
						if (tableRowId == RowCount - 2) {
							TableRow tr = (TableRow) gv.tableRowsList.get(
									tableRowId).get("tableRow");
							if (tableColumnId > tr.getLastCellCount() - 1) {
								tableColumnId = tr.getLastCellCount() - 1;
							}
						}
						tableRowId++;
					}
				}

			}
			gv.checkPressed(tableRowId, tableColumnId);
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:

			if (RowCount > 0) {
				if (tableColumnId == -1 && tableRowId == -1) {
					tableColumnId = 0;
					tableRowId = 0;
				} else {
					if (tableRowId < RowCount) {
						TableRow tr = (TableRow) gv.tableRowsList.get(
								tableRowId).get("tableRow");
						if (tableRowId == RowCount - 1) {
							ColumnCountByRow = tr.getLastCellCount();
						} else {
							ColumnCountByRow = tr.getCellCount();
						}
						if (tableColumnId < ColumnCountByRow - 1) {
							tableColumnId++;
						} else {
							if (tableColumnId == ColumnCountByRow - 1) {
								if (tableRowId == RowCount - 1) {
									tableRowId = 0;
									pageIndxe = pageIndxe == simpleAdapter
											.getCount() - 1 ? 0 : ++pageIndxe;
									loadGrid(pageIndxe);
									page.setCheckPage(pageIndxe);//
									switchLayoutStateTo(pageIndxe);
									mCurrentLayoutState = pageIndxe;
									gv = (gridViewExt) viewFlipper
											.getChildAt(pageIndxe);
									tableRowId = 0;
									tableColumnId = 0;

								} else {
									tableRowId++;
								}
								tableColumnId = 0;
							} else {
								tableRowId = 0;
								tableColumnId = 0;
							}
						}
					}
				}
			}
			gv.checkPressed(tableRowId, tableColumnId);
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			if (tableColumnId == -1 && tableRowId == -1) {
				tableColumnId = 0;
				tableRowId = 0;
			} else {
				if (tableRowId < RowCount) {
					TableRow tr = (TableRow) gv.tableRowsList.get(tableRowId)
							.get("tableRow");
					if (tableRowId == RowCount - 1) {
						ColumnCountByRow = tr.getLastCellCount();
					} else {
						ColumnCountByRow = tr.getCellCount();
					}
					// if (tableColumnId < ColumnCountByRow - 1) {
					if (tableColumnId == 0 && tableRowId > 0) {
						tableRowId--;

						tableColumnId = ((TableRow) gv.tableRowsList.get(
								tableRowId).get("tableRow")).getCellCount() - 1;
					} else {
						if (tableColumnId == 0 && tableRowId == 0) {

							pageIndxe = pageIndxe == 0 ? simpleAdapter
									.getCount() - 1 : pageIndxe - 1;
							loadGrid(pageIndxe);
							switchLayoutStateTo(pageIndxe);//

							mCurrentLayoutState = pageIndxe;
							gv = (gridViewExt) viewFlipper
									.getChildAt(pageIndxe);
							TableRow tableRow = (TableRow) gv.tableRowsList
									.get(tableRowId).get("tableRow");
							RowCount = gv.tableRowsList.size();
							tableColumnId = tableRow.getLastCellCount() - 1;
							tableRowId = RowCount - 1;
							appCount = adapter.getCount();
							page.setCheckPage(pageIndxe);

						} else {
							tableColumnId--;
						}
					}
					// } else {
					// tableColumnId--;
					// }
				}
			}
			gv.checkPressed(tableRowId, tableColumnId);

			break;
		case KeyEvent.KEYCODE_DPAD_CENTER:
			if (tableColumnId == -1 && tableRowId == -1) {
				break;
			} else {
				gv.onClick(tableRowId, tableColumnId, infoActivity.this);
			}
			break;

		}

		return false;
	}
}
