package com.victor_fun.android_app_utils.htc_view_test;

import com.victor_fun.android_app_utils.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class HSMConnectActivity extends Activity implements OnClickListener {
	static final String LOG_TAG = HSMConnectActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		View dlgContent = getLayoutInflater().inflate(R.layout.hsm_connect_dlg, null);
		
		initViewStyle(dlgContent);
//		hideTextViewWarning2IfTethering(dlgContent);
		
//		HtcAlertDialog.Builder builder = new HtcAlertDialog.Builder(this);		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		HtcAlertDialog dialog = 
		AlertDialog dialog = 
			builder.setTitle(R.string.hsm_connect_dlg_title)
			.setNegativeButton(android.R.string.cancel, this)
			.setPositiveButton(android.R.string.ok, this)
			.setView(dlgContent)
			.setOnCancelListener(new OnCancelListener() {				
				@Override
				public void onCancel(DialogInterface dialog) {
					HSMConnectActivity.this.finish();
				}
			})
			.create();
		
		dialog.show();
	}

	private void initViewStyle(View dlgContent) {
		try {
			int padding = (int)this.getResources().getDimension(R.dimen.dialog_text_size);
			dlgContent.setPadding(padding, padding, padding, padding);
		

			TextView tv = (TextView)dlgContent.findViewById(R.id.textViewWarning1);
//			tv.setTextAppearance(this, com.htc.R.style.list_body_primary_m);
			tv.setTextAppearance(this, android.R.style.TextAppearance_DeviceDefault_Medium);
		
			tv = (TextView)dlgContent.findViewById(R.id.textViewWarning2);
//			tv.setTextAppearance(this, com.htc.R.style.list_body_primary_m);
			tv.setTextAppearance(this, android.R.style.TextAppearance_DeviceDefault_Medium);
		
			tv = (TextView)dlgContent.findViewById(R.id.textViewDontAsk);
//			tv.setTextAppearance(this, com.htc.R.style.list_secondary_m);
			tv.setTextAppearance(this, android.R.style.TextAppearance_DeviceDefault_Small);
		
			padding = (int)this.getResources().getDimension(R.dimen.dialog_text_size);
			tv.setPadding(padding, tv.getPaddingTop(), tv.getPaddingRight(), tv.getPaddingBottom());						

		} catch (Throwable any) {
			Log.e(LOG_TAG, "Failed to customize the dialog with HTC's style", any);
		}
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}
}
