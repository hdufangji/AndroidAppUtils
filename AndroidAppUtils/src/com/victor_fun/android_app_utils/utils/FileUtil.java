package com.victor_fun.android_app_utils.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Environment;
import android.util.Log;

public class FileUtil {
	private static final String TAG = FileUtil.class.getSimpleName();

	private static byte[] DATA_BUFFER = new byte[2048];
	
	public static boolean hasStorage(boolean requireWriteAccess) {
	    //TODO: After fix the bug,  add "if (VERBOSE)" before logging errors.
	    String state = Environment.getExternalStorageState();
	    Log.v(TAG, "storage state is " + state);

	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        if (requireWriteAccess) {
	            boolean writable = checkFsWritable();
	            Log.v(TAG, "storage writable is " + writable);
	            return writable;
	        } else {
	            return true;
	        }
	    } else if (!requireWriteAccess && Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        return true;
	    }
	    return false;
	}
	
	private static boolean checkFsWritable() {
        // Create a temporary file to see whether a volume is really writeable.
        // It's important not to put it in the root directory which may have a
        // limit on the number of files.
        String directoryName = Environment.getExternalStorageDirectory().toString() + "/DCIM";
        File directory = new File(directoryName);
        if (!directory.isDirectory()) {
            if (!directory.mkdirs()) {
                return false;
            }
        }
        return directory.canWrite();
    }
	
	public static boolean saveData2File(byte[] data, File file, String fileName, int dataLength, boolean append){
		return saveData2File(data, file.toString(), fileName, dataLength, append);
	}
	
	public static boolean saveData2File(byte[] data, String filePath, String fileName, int dataLength, boolean append){
		File file = new File(filePath);
		
		if(!file.exists()){
			file.mkdirs();
		}
		
		File file1 = new File(filePath + "/" + fileName);
		if(!file1.exists()){
			try {
				file1.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file1, append);
			
			int size = data.length;
			int offset = 0;
			int length = 0;
			
			while(offset < size){
				length = size - offset > DATA_BUFFER.length ? DATA_BUFFER.length : size - offset;
				System.arraycopy(data, 0, DATA_BUFFER, 0, length);
//				fos.write(DATA_BUFFER);
				if (dataLength < DATA_BUFFER.length) {
					fos.write(DATA_BUFFER, 0, dataLength);					
				} else {
					fos.write(DATA_BUFFER, 0, length);					
				}
				offset += length;
				fos.flush();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return true;
	}
	
	public static String read(File file) throws IOException {
		String data = "";
		try {
			FileInputStream stream = new FileInputStream(file);
			StringBuffer sb = new StringBuffer();
			int c;
			while ((c = stream.read()) != -1) {
				sb.append((char) c);
			}
			stream.close();
			data = sb.toString();

		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}
		return data;
	}
	
	public static String getParent(String path) {
		int index = path.lastIndexOf('/');
		if (index <= 0) {
			return "/";
		}
		return path.substring(0, index);
	}

	public static String getName(String path) {
		if (path.endsWith("/"))
			return "";
		int index = path.lastIndexOf('/');
		if (index >= 0)
			return path.substring(index + 1);
		return "";
	}
}
