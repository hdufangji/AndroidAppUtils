package com.victor_fun.android_app_utils.uikit;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.victor_fun.android_app_utils.R;

public class MyTestRefreshView extends LinearLayout implements OnTouchListener {
	static final String LOG_TAG = MyTestRefreshView.class.getSimpleName();
	
	enum RefreshStatus {
		RefreshFinished,
		PullToRefresh,
		ReleaseToRefresh,
		Refreshing
	}
	
	private View 				mHeader;
	private ImageView 			mArrow;
	private ProgressBar 		mPb;
	private TextView 			mDescription;
	private ListView			mLv;
	private OnRefreshListener 	mListener;
	private float 				mY;
	private int 				mHeaderHeight;
	private RefreshStatus		mCurrentRefreshStatus;
	private LayoutParams 		mHeaderParams;

	public MyTestRefreshView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mHeader = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh, null);
		mArrow = (ImageView)mHeader.findViewById(R.id.arrow);
		mDescription = (TextView)mHeader.findViewById(R.id.description);
		mPb = (ProgressBar)mHeader.findViewById(R.id.progress_bar);
		
		mCurrentRefreshStatus = RefreshStatus.RefreshFinished;
		this.setOrientation(VERTICAL);
		addView(mHeader, 0);
	}
	
	public void setOnRefreshListener(OnRefreshListener l){
		if (l != null)
			this.mListener = l;
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
		
		if(changed){
			mHeaderHeight = mHeader.getHeight();
			mHeaderParams = (LayoutParams) mHeader.getLayoutParams();
			mHeaderParams.topMargin = -mHeader.getHeight();
			mHeader.setLayoutParams(mHeaderParams);
			mLv = (ListView) this.getChildAt(1);
			mLv.setOnTouchListener(this);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mY = event.getRawY();
			Log.i(LOG_TAG, "first tip y = " + mY);
			break;
		case MotionEvent.ACTION_MOVE:
			int distance = (int)(event.getRawY() - mY);
			mY = event.getRawY();
			Log.i(LOG_TAG, "move tip y = " + mY + ", distance = " + distance);
			if (distance > 0 && distance < mHeaderHeight) {
				mCurrentRefreshStatus = RefreshStatus.PullToRefresh;
			} else if (distance >= mHeaderHeight) {
				mCurrentRefreshStatus = RefreshStatus.ReleaseToRefresh;
			} else {
				mCurrentRefreshStatus = RefreshStatus.RefreshFinished;
			}
			setHeaderByRefreshStatus(mCurrentRefreshStatus, distance);
			break;
		case MotionEvent.ACTION_UP:
		default:
			setHeaderByRefreshStatus(mCurrentRefreshStatus, 0);
			doBackgroundThings(mCurrentRefreshStatus);
			break;
		}
		
		return false;
	}
	
	private void doBackgroundThings(RefreshStatus status) {
		switch (status) {
		case Refreshing:
			if (mListener != null)
				mListener.onRefresh();
			break;

		default:
			break;
		}
		
	}

	public void finishRefreshing(){
		mCurrentRefreshStatus = RefreshStatus.RefreshFinished;
		setHeaderByRefreshStatus(mCurrentRefreshStatus, 0);
	}
	
	private void setHeaderByRefreshStatus(RefreshStatus status, int distance){
		switch (status) {
		case PullToRefresh:
			mPb.setVisibility(View.GONE);
			mArrow.setVisibility(View.VISIBLE);
			mDescription.setText(R.string.pull_to_refresh);
			rotateArrow();
			break;
		case ReleaseToRefresh:
			mPb.setVisibility(View.GONE);
			mArrow.setVisibility(View.VISIBLE);
			mDescription.setText(R.string.release_to_refresh);
			rotateArrow();
			break;
		case Refreshing:
			mPb.setVisibility(View.VISIBLE);
			mArrow.setVisibility(View.GONE);
			mDescription.setText(R.string.refreshing);
			break;
		case RefreshFinished:
			mHeaderParams.topMargin = -mHeaderHeight;
			mHeader.setLayoutParams(mHeaderParams);
			break;
		default:
			break;
		}
		if (distance != 0) {
			mHeaderParams.topMargin = distance / 2;
			mHeader.setLayoutParams(mHeaderParams);				
		}
	}
	
	private void rotateArrow() {
		float pivotX = mArrow.getWidth() / 2f;
		float pivotY = mArrow.getHeight() / 2f;
		float fromDegrees = 0f;
		float toDegrees = 0f;
		if (mCurrentRefreshStatus == RefreshStatus.PullToRefresh) {
			fromDegrees = 180f;
			toDegrees = 360f;
		} else if (mCurrentRefreshStatus == RefreshStatus.ReleaseToRefresh) {
			fromDegrees = 0f;
			toDegrees = 180f;
		}
		RotateAnimation animation = new RotateAnimation(fromDegrees, toDegrees, pivotX, pivotY);
		animation.setDuration(100);
		animation.setFillAfter(true);
		mArrow.startAnimation(animation);
	}
	
	public interface OnRefreshListener{
		public void onRefresh();
	}

}
