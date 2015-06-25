package com.victor_fun.android_app_utils.utils;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

public class PickPhotoUtils {

	public static void PickBelowKitKat(final String photoPath,
			final int targetSize, final Activity activity, final int requestCode) {

		AlertDialog dialog = new AlertDialog.Builder(activity).setItems(
				new String[] { "Ã§â€ºÂ¸Ã¦Å“Â?", "Ã§â€ºÂ¸Ã¥â? Å?" },
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which == 0) {
							Intent intent = new Intent(
									"android.media.action.IMAGE_CAPTURE");
							intent.putExtra("output", Uri.parse(photoPath));
							intent.putExtra("crop", "true");
							intent.putExtra("aspectX", 1);//Ã¨Â£ï¿½Ã¥â?°ÂªÃ¦Â¡â? Ã¦Â¯â?Ã¤Â¾â??
							intent.putExtra("aspectY", 1);
							intent.putExtra("outputX", targetSize);// Ã¨Â¾â€œÃ¥â?¡ÂºÃ¥â?ºÂ¾Ã§â?°â?¡Ã¥Â¤Â§Ã¥Â°ï¿½
							intent.putExtra("outputY", targetSize);
							activity.startActivityForResult(intent, requestCode);
						} else {
							Intent intent = new Intent(
									"android.intent.action.PICK");
							intent.setDataAndType(
									MediaStore.Images.Media.INTERNAL_CONTENT_URI,
									"image/*");
							intent.putExtra("output", Uri.parse(photoPath));
							intent.putExtra("crop", "true");
							intent.putExtra("aspectX", 1);// Ã¨Â£ï¿½Ã¥â?°ÂªÃ¦Â¡â? Ã¦Â¯â?Ã¤Â¾â??
							intent.putExtra("aspectY", 1);
							intent.putExtra("outputX", targetSize);// Ã¨Â¾â€œÃ¥â?¡ÂºÃ¥â?ºÂ¾Ã§â?°â?¡Ã¥Â¤Â§Ã¥Â°ï¿½
							intent.putExtra("outputY", targetSize);
							activity.startActivityForResult(intent, requestCode);
						}
					}
				}).create();

		dialog.show();
	}
	
	
	public static void PickOnKitKat(final File file,
			final int targetSize, final Activity activity, final int requestCode) {

		AlertDialog dialog = new AlertDialog.Builder(activity).setItems(
				new String[] { "Ã§â€ºÂ¸Ã¦Å“Â?", "Ã§â€ºÂ¸Ã¥â? Å?" },
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which == 0) {
							Intent intent = new Intent(
									"android.media.action.IMAGE_CAPTURE");
							intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));							
							activity.startActivityForResult(intent, requestCode);
						} else {
							Intent intent = new Intent();
							intent.setType("image/*");
							intent.setAction(Intent.ACTION_GET_CONTENT);
							intent.addCategory(Intent.CATEGORY_OPENABLE);
							intent.putExtra("output", Uri.fromFile(file));
							activity.startActivityForResult(intent, requestCode);
						}
					}
				}).create();

		dialog.show();
	}

	public static void Capture(Activity activity, File file, int code) {
		Intent it = new Intent("android.media.action.IMAGE_CAPTURE");
		it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		activity.startActivityForResult(it, code);
	}

	public static void Capture2(Activity activity, File file, int code,Uri photoUri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		// this will open all images in the Galery
		intent.setDataAndType(photoUri, "image/*");
		intent.putExtra("crop", "true");
		// this defines the aspect ration
		intent.putExtra("aspectX", 0);
		intent.putExtra("aspectY", 0);
		// this defines the output bitmap size
		intent.putExtra("outputX", 100);
		intent.putExtra("outputY", 100);
		// true to return a Bitmap, false to directly save the cropped iamge
		intent.putExtra("return-data", false);
		//save output image in uri
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		activity.startActivity(intent);
	}
	
	public static void PickCommon(final Activity activity, int requestCode) {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		activity.startActivityForResult(intent, requestCode);
	}

	public static void PickCommon2(final Activity activity, int requestCode) {
		Intent i = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		activity.startActivityForResult(i, requestCode);
	}

	@SuppressLint("NewApi")
	public static String GetPathForKitKat(final Context context, final Uri uri) {

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/"
							+ split[1];
				}

				// TODO handle non-primary volumes
			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {

				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"),
						Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[] { split[1] };

				return getDataColumn(context, contentUri, selection,
						selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {

			// Return the remote address
			if (isGooglePhotosUri(uri))
				return uri.getLastPathSegment();

			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}
	
	@SuppressLint("NewApi")
	public static long GetIdForKitKat(final Context context, final Uri uri) {

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			String docId = DocumentsContract.getDocumentId(uri);
			final String[] split = docId.split(":");
			final String id = split[1];
			return Long.parseLong(id);
		}
		// MediaStore (and general)
//		else if ("content".equalsIgnoreCase(uri.getScheme())) {
//
//			// Return the remote address
//			if (isGooglePhotosUri(uri))
//				return uri.get;
//
//			return getIdColumn(context, uri, null, null);
//		}
//		// File
//		else if ("file".equalsIgnoreCase(uri.getScheme())) {
//			return uri.get;
//		}

		return -1;
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 * 
	 * @param context
	 *            The context.
	 * @param uri
	 *            The Uri to query.
	 * @param selection
	 *            (Optional) Filter used in the query.
	 * @param selectionArgs
	 *            (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public static String getDataColumn(Context context, Uri uri,
			String selection, String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = { column };

		try {
			cursor = context.getContentResolver().query(uri, projection,
					selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}
	
	/**
	 * Get the value of the _ID column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 * 
	 * @param context
	 *            The context.
	 * @param uri
	 *            The Uri to query.
	 * @param selection
	 *            (Optional) Filter used in the query.
	 * @param selectionArgs
	 *            (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public static long getIdColumn(Context context, Uri uri,
			String selection, String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_ID";
		final String[] projection = { column };

		try {
			cursor = context.getContentResolver().query(uri, projection,
					selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getLong(index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return -1;
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri
				.getAuthority());
	}

	public static Bitmap ResizeBitmap(Bitmap bitmap, int newWidth) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float temp = ((float) height) / ((float) width);
		int newHeight = (int) ((newWidth) * temp);
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		Matrix matrix = new Matrix();
		// resize the bit map
		matrix.postScale(scaleWidth, scaleHeight);
		// matrix.postRotate(45);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		bitmap.recycle();
		return resizedBitmap;
	}
}