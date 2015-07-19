package com.victor_fun.android_app_utils.uikit;

import com.victor_fun.android_app_utils.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RefreshableView extends LinearLayout implements OnTouchListener {
	static final String LOG_TAG = RefreshableView.class.getSimpleName();

	public static final int			STATUS_PULL_TO_REFRESH		= 0;

	public static final int			STATUS_RELEASE_TO_REFRESH	= 1;

	public static final int			STATUS_REFRESHING			= 2;

	public static final int			STATUS_REFRESH_FINISHED		= 3;

	public static final int			STATUS_DISABLE				= 4;

	public static final int			SCROLL_SPEED				= -20;

	public static final long		ONE_MINUTE					= 60 * 1000;

	public static final long		ONE_HOUR					= 60 * ONE_MINUTE;

	public static final long		ONE_DAY						= 24 * ONE_HOUR;

	public static final long		ONE_MONTH					= 30 * ONE_DAY;

	public static final long		ONE_YEAR					= 12 * ONE_MONTH;

	private static final String		UPDATED_AT					= "updated_at";

	private PullToRefreshListener	mListener;

	private SharedPreferences		preferences;

	private View					header;

	private ListView				listView;

	private ProgressBar				progressBar;

	private ImageView				arrow;

	private TextView				description;

	private MarginLayoutParams		headerLayoutParams;

	private long					lastUpdateTime;

	private int						mId							= -1;

	private int						hideHeaderHeight;

	private int						currentStatus				= STATUS_REFRESH_FINISHED;

	private int						lastStatus					= currentStatus;

	private float					yDown;

	private int						touchSlop;

	private boolean					loadOnce;

	private boolean					ableToPull;

	private boolean					refreshAfterLayout;

	public RefreshableView(Context context, AttributeSet attrs) {
		super(context, attrs);
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
		header = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh, null, true);
		progressBar = (ProgressBar) header.findViewById(R.id.progress_bar);
		arrow = (ImageView) header.findViewById(R.id.arrow);
		description = (TextView) header.findViewById(R.id.description);
		touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		Log.i(LOG_TAG, "touchSlop = " + touchSlop);
		setOrientation(VERTICAL);
		addView(header, 0);
	}

	StringBuilder sb = new StringBuilder();
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		sb.setLength(0);
		sb.append("changed = ");
		sb.append(changed);
		sb.append(", l = ");
		sb.append(l);
		sb.append(", t = ");
		sb.append(t);
		sb.append(", r = ");
		sb.append(r);
		sb.append(", b = ");
		sb.append(b);
		Log.i(LOG_TAG, sb.toString());
		if (changed && !loadOnce) {
			hideHeaderHeight = -header.getHeight();
			Log.i(LOG_TAG, "header.getHeight() = " + header.getHeight() + ", hideHeaderHeight = " + hideHeaderHeight);
			headerLayoutParams = (MarginLayoutParams) header.getLayoutParams();
			headerLayoutParams.topMargin = hideHeaderHeight;
			listView = (ListView) getChildAt(1);
			listView.setOnTouchListener(this);
			loadOnce = true;
			if (refreshAfterLayout) {
				startRefresh();
			}
		}
	}

	public void enablePull(boolean enable) {
		if (enable) {
			if (currentStatus == STATUS_DISABLE)
				currentStatus = STATUS_REFRESH_FINISHED;
		} else {
			if (currentStatus == STATUS_REFRESH_FINISHED)
				currentStatus = STATUS_DISABLE;
		}
	}

	public void startRefresh() {
		if (loadOnce == false) {
			refreshAfterLayout = true;
		} else {
			currentStatus = STATUS_RELEASE_TO_REFRESH;
			new RefreshingTask().execute();
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		//3.2.2 MOTO xoom can reproduce this Exception
		try{
			return super.dispatchTouchEvent(ev);
		}catch(IllegalArgumentException e){
			e.printStackTrace();
			Log.e(VIEW_LOG_TAG, "dispatchTouchEvent IllegalArgumentException");
		}
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		setIsAbleToPull(event);
		if (ableToPull) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				yDown = event.getRawY();
				Log.i(LOG_TAG, "first tip y = " + yDown);
				break;
			case MotionEvent.ACTION_MOVE:
				float yMove = event.getRawY();
				int distance = (int) (yMove - yDown);
				Log.i(LOG_TAG, "move tip y = " + yMove + ", distance = " + distance);
				if (distance <= 0 && headerLayoutParams.topMargin <= hideHeaderHeight) {
					return false;
				}
				if (distance < touchSlop) {
					return false;
				}
				if (currentStatus != STATUS_REFRESHING && currentStatus != STATUS_DISABLE) {
					if (headerLayoutParams.topMargin > 0) {
						currentStatus = STATUS_RELEASE_TO_REFRESH;
					} else {
						currentStatus = STATUS_PULL_TO_REFRESH;
					}
					headerLayoutParams.topMargin = (distance / 2) + hideHeaderHeight;
					header.setLayoutParams(headerLayoutParams);
				}
				break;
			case MotionEvent.ACTION_UP:
			default:
				if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
					new RefreshingTask().execute();
				} else if (currentStatus == STATUS_PULL_TO_REFRESH) {
					new HideHeaderTask().execute();
				}
				break;
			}

			if (currentStatus == STATUS_PULL_TO_REFRESH || currentStatus == STATUS_RELEASE_TO_REFRESH) {
				updateHeaderView();
				listView.setPressed(false);
				listView.setFocusable(false);
				listView.setFocusableInTouchMode(false);
				lastStatus = currentStatus;
				return true;
			}
		}
		return false;
	}

	public void setOnRefreshListener(PullToRefreshListener listener, int id) {
		mListener = listener;
		mId = id;
	}

	public void finishRefreshing() {
		currentStatus = STATUS_REFRESH_FINISHED;
		preferences.edit().putLong(UPDATED_AT + mId, System.currentTimeMillis()).commit();
		new HideHeaderTask().execute();
	}

	private void setIsAbleToPull(MotionEvent event) {
		View firstChild = listView.getChildAt(0);
		if (firstChild != null) {
			int firstVisiblePos = listView.getFirstVisiblePosition();
			Log.i(LOG_TAG, "listView firstVisiblePos = " + firstVisiblePos + ", firstChild.getTop() = " + firstChild.getTop());
			if (firstVisiblePos == 0 && firstChild.getTop() == 0) {
				if (!ableToPull) {
					yDown = event.getRawY();
				}
				ableToPull = true;
			} else {
				if (headerLayoutParams.topMargin != hideHeaderHeight) {
					headerLayoutParams.topMargin = hideHeaderHeight;
					header.setLayoutParams(headerLayoutParams);
				}
				ableToPull = false;
			}
		} else {
			ableToPull = true;
		}
	}

	private void updateHeaderView() {
		if (lastStatus != currentStatus) {
			if (currentStatus == STATUS_PULL_TO_REFRESH) {
				description.setText(getResources().getString(R.string.pull_to_refresh));
				arrow.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);
				rotateArrow();
			} else if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
				description.setText(getResources().getString(R.string.release_to_refresh));
				arrow.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);
				rotateArrow();
			} else if (currentStatus == STATUS_REFRESHING) {
				description.setText(getResources().getString(R.string.refreshing));
				progressBar.setVisibility(View.VISIBLE);
				arrow.clearAnimation();
				arrow.setVisibility(View.GONE);
			}
		}
	}

	private void rotateArrow() {
		float pivotX = arrow.getWidth() / 2f;
		float pivotY = arrow.getHeight() / 2f;
		float fromDegrees = 0f;
		float toDegrees = 0f;
		if (currentStatus == STATUS_PULL_TO_REFRESH) {
			fromDegrees = 180f;
			toDegrees = 360f;
		} else if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
			fromDegrees = 0f;
			toDegrees = 180f;
		}
		RotateAnimation animation = new RotateAnimation(fromDegrees, toDegrees, pivotX, pivotY);
		animation.setDuration(100);
		animation.setFillAfter(true);
		arrow.startAnimation(animation);
	}

	public boolean isRefreshing() {
		return currentStatus == STATUS_REFRESHING;
	}

	class RefreshingTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			int topMargin = 0;
			if (headerLayoutParams != null)
				topMargin = headerLayoutParams.topMargin;
			while (true) {
				topMargin = topMargin + SCROLL_SPEED;
				if (topMargin <= 0) {
					topMargin = 0;
					break;
				}
				publishProgress(topMargin);
				sleep(10);
			}
			currentStatus = STATUS_REFRESHING;
			publishProgress(0);
			if (mListener != null) {
				mListener.onRefresh();
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... topMargin) {
			updateHeaderView();
			if (headerLayoutParams != null) {
				headerLayoutParams.topMargin = topMargin[0];
				header.setLayoutParams(headerLayoutParams);
			}
		}
	}

	class HideHeaderTask extends AsyncTask<Void, Integer, Integer> {

		@Override
		protected Integer doInBackground(Void... params) {
			int topMargin = 0;
			if (headerLayoutParams != null)
				topMargin = headerLayoutParams.topMargin;
			while (true) {
				topMargin = topMargin + SCROLL_SPEED;
				if (topMargin <= hideHeaderHeight) {
					topMargin = hideHeaderHeight;
					break;
				}
				publishProgress(topMargin);
				sleep(10);
			}
			return topMargin;
		}

		@Override
		protected void onProgressUpdate(Integer... topMargin) {
			if (headerLayoutParams != null) {
				headerLayoutParams.topMargin = topMargin[0];
				header.setLayoutParams(headerLayoutParams);
			}
		}

		@Override
		protected void onPostExecute(Integer topMargin) {
			if (headerLayoutParams != null) {
				headerLayoutParams.topMargin = topMargin;
				header.setLayoutParams(headerLayoutParams);
			}
			currentStatus = STATUS_REFRESH_FINISHED;
		}
	}

	private void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public interface PullToRefreshListener {
		void onRefresh();
	}
}
