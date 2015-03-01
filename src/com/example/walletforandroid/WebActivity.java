package com.example.walletforandroid;

import logic.User;
import logic.history.History;
import logic.wallet.Wallet;
import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;

public class WebActivity extends Activity {
	
	Switch sw;
	TextView tip;
	static TextView tport;
	public String port=new String("");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
		
		loadItem();
		loadAction();
	}

	private void loadAction() {
		sw.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked){
					tip.setVisibility(View.VISIBLE);
					tport.setVisibility(View.VISIBLE);
					tip.setText(getIP());
					Intent intent = new Intent(WebActivity.this,WebServer.class); 
					startService(intent);
					tport.setText(port);
				}else{
					tip.setVisibility(View.INVISIBLE);
					tport.setVisibility(View.INVISIBLE);
					Intent intent = new Intent(WebActivity.this, WebServer.class);
					stopService(intent);
				}
			}
		});
	}
	
	private String getIP(){
		WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE); 
        WifiInfo info = wifiManager.getConnectionInfo();
        @SuppressWarnings("deprecation")
		String ans = Formatter.formatIpAddress(info.getIpAddress());
        return ans;
	}

	private void loadItem() {
		sw=(Switch) this.findViewById(R.id.webSwitch1);
		sw.setSelected(false);
		tip=(TextView) this.findViewById(R.id.webIP);
		tip.setVisibility(View.INVISIBLE);
		tport=(TextView) this.findViewById(R.id.webPort);
		tport.setVisibility(View.INVISIBLE);
	}
	
	@Override
	public void onDestroy(){
		Wallet wallet=new Wallet();
		History history=new History();
		wallet.loadWallet();
		history.loadHistory();
		if (!User.userReason.equals("normal")){
			history.build();
		}
		super.onDestroy();
	}
}
