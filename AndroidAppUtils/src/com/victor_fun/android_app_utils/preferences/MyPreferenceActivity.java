package com.victor_fun.android_app_utils.preferences;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import com.victor_fun.android_app_utils.R;

public class MyPreferenceActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		 getFragmentManager().beginTransaction()
         .replace(android.R.id.content, new SettingsFragment())
         .commit();

	}

//	@Override
//	protected void onPause() {
//		super.onPause();
//		getPreferenceScreen().getSharedPreferences()
//				.unregisterOnSharedPreferenceChangeListener(this);
//	}
//
//	@Override
//	protected void onResume() {
//		super.onResume();
//		getPreferenceScreen().getSharedPreferences()
//				.registerOnSharedPreferenceChangeListener(this);
//	}
//
//	@Override
//	public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {
//	}
	
	public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        // Load the preferences from an XML resource
	        addPreferencesFromResource(R.xml.preferences_syncapp);
	    }

	    @Override
		public void onResume() {
	        super.onResume();
	        getPreferenceScreen().getSharedPreferences()
	                .registerOnSharedPreferenceChangeListener(this);
	    }

	    @Override
		public void onPause() {
	        super.onPause();
	        getPreferenceScreen().getSharedPreferences()
	                .unregisterOnSharedPreferenceChangeListener(this);
	    }
	    
		@Override
		public void onSharedPreferenceChanged(
				SharedPreferences sharedPreferences, String key) {
			if (key.equals(this.getActivity().getString(R.string.libneroconnect_pref_full_wakelock_key))) {
				Toast.makeText(this.getActivity(), "my value is changed!", Toast.LENGTH_SHORT).show();
			}
			
		}
	}
}
