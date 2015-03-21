package com.example.lt.walletforandroid;

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

import logic.wallet.Detail;
import type.DetailType;

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
	};

	private void loadData() {
        Vector<DetailType> a=new Detail().getDetail();
        for (int i=a.size()-1;i>=0;i--){
            TableRow row=(TableRow) this.getActivity().getLayoutInflater().inflate(R.layout.detail_table_row, status, false);

            TextView time=(TextView) row.findViewById(R.id.detail_time);
            TextView event=(TextView) row.findViewById(R.id.detail_event);
            TextView type=(TextView) row.findViewById(R.id.detail_type);
            TextView value=(TextView) row.findViewById(R.id.detail_value);

            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            time.setText(sdf.format(a.get(i).getTime()));
            event.setText(a.get(i).getEvent());
            type.setText(a.get(i).getType());
            value.setText(a.get(i).getValue()+"");

            status.addView(row);
        }
	}

	private void loadItem() {
		status=(TableLayout) this.getView().findViewById(R.id.detail_table);
	}
}
