package com.victor_fun.android_app_utils.test;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.victor_fun.android_app_utils.R;
import com.victor_fun.android_app_utils.uikit.RefreshableView;
import com.victor_fun.android_app_utils.uikit.RefreshableView.PullToRefreshListener;

public class RefreshActivity extends Activity {
	ListView mLv;
	RefreshableView refreshView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.refresh_activity);
		mLv = (ListView)findViewById(R.id.lv);
		mLv.setAdapter(getAdapter());
	
		refreshView = (RefreshableView)findViewById(R.id.refreshView);
		refreshView.setOnRefreshListener(new PullToRefreshListener(){

			@Override
			public void onRefresh() {
				SystemClock.sleep(1000);
				refreshView.finishRefreshing();
			}
			
		}, 0);
	}
	
	private ListAdapter getAdapter(){
		ArrayList<String> al = new ArrayList<String>();
		al.add("aaa");
		al.add("bbb");
		al.add("ccc");
		ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, al);
		return adapter;
	}

}
