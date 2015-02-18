package com.example.walletforandroid;

import java.text.SimpleDateFormat;
import java.util.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import logic.wallet.Detail;
import type.*;

public class DetailActivity extends Activity {
	private ListView listView;
	private List<String> data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		loadItem();
		loadAction();
	}

	private void loadAction() {
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				new AlertDialog.Builder(DetailActivity.this)
				.setTitle("detail:")
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

	private List<String> loadData() {
		Detail detail=new Detail();
		
		Vector <DetailType> a=detail.getDetail();
		data=new ArrayList<String>();
		for (int i=0;i<a.size();i++){
			DetailType now=a.get(i);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",Locale.getDefault());
			data.add(sdf.format(now.getTime().getTime())+","+now.getEvent()+" with type "+now.getType()+" and "+now.getValue()+"$. because:"+now.getReason());
		}
		return data;
	}
}
