package com.example.xlo.walletforandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Vector;

import database.viewer.DataViewer;
import type.ReasonTreeNodeType;
import type.Type;

public class ReasonTreeFragment extends Fragment {
	public TableLayout status;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.reason_tree_table, container, false);
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
			viewer.loadData("reason");
			Vector<Type> a=viewer.getAllItem();
			for (int i=0;i<a.size();i++){
				TableRow row=(TableRow) this.getActivity().getLayoutInflater().inflate(R.layout.reason_tree_table_row, status, false);

				TextView father = (TextView)row.findViewById(R.id.reason_father);
				TextView name = (TextView)row.findViewById(R.id.reason_name2);
				TextView rank = (TextView)row.findViewById(R.id.reason_rank);
				TextView min = (TextView)row.findViewById(R.id.reason_min);
				TextView max = (TextView)row.findViewById(R.id.reason_max);

				ReasonTreeNodeType now= (ReasonTreeNodeType) a.get(i);
				father.setText(now.getFather());
				name.setText(now.getName());
				rank.setText(now.getRank()+"");
				min.setText(now.getMin()+"");
				max.setText(now.getMax()+"");

				status.addView(row);
			}
		}catch(Exception e){}
	}

	private void loadItem() {
		status=(TableLayout) this.getActivity().findViewById(R.id.reason_tree_table);
	};
}
