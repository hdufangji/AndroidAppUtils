package com.victor_fun.android_app_utils.fundation_technoligy.java;

import java.nio.ByteBuffer;

public class ByteBufferDemon {
	static final int BUFFER_SIZE = 2048;
	
	public void testByteBuffer(){
		ByteBuffer byteBuffer =ByteBuffer.allocateDirect(BUFFER_SIZE);
		byteBuffer.putInt(0, 40);
	}
}
