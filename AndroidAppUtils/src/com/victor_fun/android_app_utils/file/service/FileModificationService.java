package com.victor_fun.android_app_utils.file.service;

import java.io.File;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import com.victor_fun.android_app_utils.file.service.RecursiveFileObserver.onFileChangedListener;

public class FileModificationService extends Service implements onFileChangedListener{
	private RecursiveFileObserver fileOb;
	//private static final int MAX_FO = 1;
	//private List<MyFileObserver> fileOb_list = new ArrayList<MyFileObserver>();
	@Override
	public void onCreate() {
		if (Environment.getExternalStorageState() == Environment.MEDIA_UNMOUNTED) {
			Toast.makeText(FileModificationService.this, "SDCARD is not available!", Toast.LENGTH_SHORT).show();
			return;
		}
		
		File sdcard = Environment.getExternalStorageDirectory();
		if (sdcard == null) {
			return;
		} else {
			//fileOb_list.clear();
			num_of_fos = 0;
			createFileObs(sdcard);
		}
	}
	
	//only create fileobserver for folders
	int num_of_fos = 0;
	private void createFileObs(File f) {
		/*if (num_of_fos > MAX_FO) {
			return;
		}*/
		if (!f.isDirectory()) {
			//MyFileObserver aFileOb = new MyFileObserver(f.getAbsolutePath());
			//fileOb_list.add(aFileOb);
		} else {
			fileOb = new RecursiveFileObserver(f.getAbsolutePath());
			fileOb.setOnFileChangedListener(this);
			/*MyFileObserver aFileOb = new MyFileObserver(f.getAbsolutePath());
			fileOb_list.add(aFileOb);
			num_of_fos++;
			try {
				for (File currentFile : f.listFiles()) {
					createFileObs(currentFile);
				}
			} catch (Exception e) {
				Log.e("Error", e.toString());
			}*/
		}
	}
	
	@Override
	public void onStart(Intent intent, int startid) {
		fileOb.startWatching();
		/*for (int i = 0; i < fileOb_list.size(); ++i) {
			fileOb_list.get(i).startWatching();
		}*/
		Toast.makeText(this.getApplicationContext(), "start monitoring file modification", Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onDestroy() {
		fileOb.stopWatching();
		/*for (int i = 0; i < fileOb_list.size(); ++i) {
			fileOb_list.get(i).stopWatching();
		}*/
		Toast.makeText(this.getApplicationContext(), "stop monitoring file modification", Toast.LENGTH_SHORT).show();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
//	FileMonitorServer fileMonitorServer = new FileMonitorServer(this);
	@Override
	public void onFileChanged(int event, String path) {
		if (path == null) {
			return;
		}
		
//		fileMonitorServer.onFileChanged(event, path);
	}
}
