package com.victor_fun.android_app_utils.preferences;

import com.victor_fun.android_app_utils.R;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

public class AboutPreference extends Preference {

	public AboutPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		View view = LayoutInflater.from(context).inflate(R.layout.preference_about, null);
		
		initViewStyle(context, view);
	}

	private void initViewStyle(Context ctx, View view) {
		int padding = (int)ctx.getResources().getDimension(R.dimen.margin_far);
		view.setPadding(padding, padding, padding, padding);
		
	}

}
