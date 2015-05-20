package com.example.xlo.walletforandroid;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import Action.TypeAddAction;
import Action.TypeRemoveAction;
import Action.TypeRnameAction;
import database.viewer.DataViewer;
import interfaceTool.DataLoader;

public class SettingFragment extends Fragment {
	Switch web;
	Button add,rename,remove;
    public static TextView ip,port;
    private WebServer server;
    boolean flag;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.setting_layout, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		loadItem();
		loadAction();

        Intent intent = new Intent(SettingFragment.this.getActivity(), WebServer.class);
        SettingFragment.this.getActivity().bindService(intent,conn, Context.BIND_AUTO_CREATE);
        flag=false;
	}

	private void loadAction() {
		web.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked){
					ip.setVisibility(View.VISIBLE);
					port.setVisibility(View.VISIBLE);

                    ip.setText("ip: "+getIP());

                    if (server!=null){
                        server.runServer();
                        flag=true;
                    }

                    Toast.makeText(SettingFragment.this.getActivity(), "server started!", Toast.LENGTH_SHORT).show();
				}else{
					ip.setVisibility(View.GONE);
					port.setVisibility(View.GONE);

                    if (server!=null){
                        server.ip=getIP();
                        server.stopServer();
                        flag=false;
                    }

                    Toast.makeText(SettingFragment.this.getActivity(), "server end!", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActionDialog ad=new ActionDialog();
				ad.setActivity(SettingFragment.this.getActivity());
				ad.setLayout(R.layout.add_type_action_layout);
				ad.setTitle("add type");
				ad.build();

                EditText name= (EditText) ad.getView().findViewById(R.id.add_type_action_new_name);

				ad.setPositiveButton("submit", new TypeAddAction(ad.getView().getContext(),name));
				ad.setNegativeButton("cancel", null);
				ad.create();
				ad.show();
			}
		});
		rename.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                DataViewer viewer=new DataViewer(); viewer.loadData("money");
                if (viewer.getAllItem().size()==0) {
					Toast.makeText(SettingFragment.this.getActivity(), "No type for rename!", Toast.LENGTH_SHORT).show();
					return ;
				}

				ActionDialog ad=new ActionDialog();
				ad.setActivity(SettingFragment.this.getActivity());
				ad.setLayout(R.layout.rename_type_action_layout);
				ad.setTitle("rename type");
				ad.build();

                Spinner past= (Spinner) ad.getView().findViewById(R.id.rename_type_action_name);
                EditText name= (EditText) ad.getView().findViewById(R.id.rename_type_action_new_name);

                DataLoader.loadAllType(SettingFragment.this.getActivity(),past);

				ad.setPositiveButton("submit", new TypeRnameAction(ad.getView().getContext(),past,name));
				ad.setNegativeButton("cancel", null);
				ad.create();
				ad.show();
			}
		});
		remove.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                DataViewer viewer=new DataViewer(); viewer.loadData("money");
                if (viewer.getAllItem().size()==0) return ;

				ActionDialog ad=new ActionDialog();
				ad.setActivity(SettingFragment.this.getActivity());
				ad.setLayout(R.layout.remove_type_action_layout);
				ad.setTitle("remove type");
				ad.build();

                Spinner name= (Spinner) ad.getView().findViewById(R.id.remove_type_action_name);

                DataLoader.loadAllType(SettingFragment.this.getActivity(),name);

				ad.setPositiveButton("submit", new TypeRemoveAction(ad.getView().getContext(),name));
				ad.setNegativeButton("cancel", null);
				ad.create();
				ad.show();
			}
		});
	}

    public String getIP(){
        WifiManager wifiManager = (WifiManager) this.getActivity().getSystemService(this.getActivity().WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo();
        return Formatter.formatIpAddress(info.getIpAddress());
    }

	private void loadItem() {
		add=(Button) this.getView().findViewById(R.id.setting_action_add);
		rename=(Button) this.getView().findViewById(R.id.setting_action_rename);
		remove=(Button) this.getView().findViewById(R.id.setting_action_remove);
		web=(Switch) this.getView().findViewById(R.id.setting_action_web);

        ip= (TextView) SettingFragment.this.getView().findViewById(R.id.web_ip);
        port= (TextView) SettingFragment.this.getView().findViewById(R.id.web_port);
	};

    @Override
    public void onStop(){
        super.onStop();
        if (flag){
            if (server!=null){
                server.ip=getIP();
                server.stopServer();
            }
            Toast.makeText(SettingFragment.this.getActivity(), "server end!", Toast.LENGTH_SHORT).show();
        }
        //TODO :reload Data
    }

    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {}

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            server=((WebServer.MyBinder)service).getService();
        }
    };
}
