package com.victorFun.androidapputils.fundation_technoligy.android.pair;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.victorFun.androidapputils.R;

public class PairDemonActivity extends Activity{
	ListView lv;
	Map<Integer, Pair<Integer, String>> data = new HashMap<Integer, Pair<Integer, String>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.pair_test);
		
		initData();
		lv = (ListView)findViewById(R.id.lv);
		LVAdapter adapter = new LVAdapter(this, data);
		lv.setAdapter(adapter);
	}

	private void initData() {
		for(int i = 0; i < 20; i++){
			Pair<Integer, String> m = new Pair<Integer, String>(i, "current item index is " + i);
			data.put(i, m);
		}
		
	}
	
	class LVAdapter extends BaseAdapter{
		Context ctx;
		Map<Integer, Pair<Integer, String>> mdata;
		
		public LVAdapter(Context ctx, Map<Integer, Pair<Integer, String>> data){
			this.ctx = ctx;
			this.mdata = data;
		}

		@Override
		public int getCount() {
			return mdata.size();
		}

		@Override
		public Object getItem(int position) {
			return mdata.get(position).second;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null){
				convertView = new TextView(ctx);
			}
			((TextView)convertView).setText(mdata.get(position).second);
			return convertView;
		}
		
	}

}
