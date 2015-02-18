package com.example.walletforandroid;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import type.ReasonType;
import logic.history.ReasonHistory;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ReasonStateActivity extends Activity {
	
	private ListView listView;
	private List<String> data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reason_state);
		
		loadItem();
		loadAction();
	}

	private void loadAction() {
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				new AlertDialog.Builder(ReasonStateActivity.this)
				.setTitle("reason:")
				.setMessage(data.get(position))
				.setPositiveButton("ok", null)
				.show();
			}
		});
	}

	private void loadItem() {
		listView=new ListView(this);
		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,loadData()));
		setContentView(listView);
	}

	private List <String> loadData() {
		ReasonHistory rh=new ReasonHistory();
		Vector <ReasonType> a=rh.getReasonType();
		data=new ArrayList<String>();
		for (int i=0;i<a.size();i++){
			ReasonType now=a.get(i);
			DecimalFormat df = new DecimalFormat("0.00");
			data.add(now.getName()+" income:"+ df.format(now.getIncome())+" expenditure:"+df.format(now.getExpenditure()));
		}
		return data;
	}
}
