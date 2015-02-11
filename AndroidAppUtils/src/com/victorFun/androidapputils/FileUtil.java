package com.victorFun.androidapputils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
	private static byte[] DATA_BUFFER = new byte[2048];
	
	public static boolean saveData2File(byte[] data, File file, String fileName){
		return saveData2File(data, file.toString(), fileName);
	}
	
	public static boolean saveData2File(byte[] data, String filePath, String fileName){
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
			fos = new FileOutputStream(file1);
			
			int size = data.length;
			int offset = 0;
			int length = 0;
			
			while(offset < size){
				length = size - offset > DATA_BUFFER.length ? DATA_BUFFER.length : size - offset;
				System.arraycopy(data, 0, DATA_BUFFER, 0, length);
				fos.write(DATA_BUFFER);
				offset += length;
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
