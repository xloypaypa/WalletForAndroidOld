package com.example.xlo.walletforandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Vector;

import database.viewer.DataViewer;
import type.DetailType;
import type.Type;

public class DetailFragment extends Fragment {
	public TableLayout status;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.detail_layout, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		loadItem();
		loadData();
	}

	private void loadData() {
		try{
			DataViewer viewer=new DataViewer();
			viewer.loadData("detail");
			Vector<Type> a=viewer.getAllItem();
			for (int i=0;i<a.size();i++){
				TableRow row=(TableRow) this.getActivity().getLayoutInflater().inflate(R.layout.detail_table_row, status, false);

				TextView time= (TextView) row.findViewById(R.id.detail_time);
				TextView event= (TextView) row.findViewById(R.id.detail_event);
				TextView type= (TextView) row.findViewById(R.id.detail_type);
				TextView value= (TextView) row.findViewById(R.id.detail_value);

				SimpleDateFormat sdf=new SimpleDateFormat("yy-MM-dd hh", Locale.getDefault());

				DetailType now= (DetailType) a.get(i);
				time.setText(sdf.format(now.getTime()));
				event.setText(now.getEvent());
				type.setText(now.getType());
				value.setText(now.getValue()+"");

				status.addView(row);
			}
		}catch(Exception e){}
	}

	private void loadItem() {
		status=(TableLayout) this.getActivity().findViewById(R.id.detail_table);
	}
}
