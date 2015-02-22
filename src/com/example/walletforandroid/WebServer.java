package com.example.walletforandroid;

import web.server.Server;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class WebServer extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override 
    public void onCreate() {  
        super.onCreate(); 
    }
	
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(WebServer.this, "Server created. ", Toast.LENGTH_LONG).show();
		new Thread(){
			@Override
			public void run(){
				Server server=new Server(8899);
				Server.fileSize=1000000;
				server.listen();
			}
		}.start();
        return super.onStartCommand(intent, flags, startId);  
    }  

    @Override 
    public void onDestroy() { 
        super.onDestroy();
        Toast.makeText(this, "Server destroyed.", Toast.LENGTH_LONG).show();
    }
	
}
