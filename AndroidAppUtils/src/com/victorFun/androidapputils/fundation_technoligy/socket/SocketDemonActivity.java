package com.victorFun.androidapputils.fundation_technoligy.socket;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.victorFun.androidapputils.R;


public class SocketDemonActivity extends Activity implements OnClickListener {
	private Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.socket_test);
    
        bt = (Button)findViewById(R.id.bt);
        bt.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		(new Thread(){

			@Override
			public void run() {
				NIOSocketClient client = new NIOSocketClient();
				try {
					client.connect();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
		}).start();
		
	}
}
