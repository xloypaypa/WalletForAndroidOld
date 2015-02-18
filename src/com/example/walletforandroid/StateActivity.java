package com.example.walletforandroid;

import java.util.*;

import logic.wallet.Money;
import type.*;
import android.app.Activity;
import android.os.Bundle;
import android.widget.*;

public class StateActivity extends Activity {
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_state);
		
		loadItem();
	}

	private void loadItem() {
		listView=new ListView(this);
		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,loadData()));
		setContentView(listView);
	}

	private List<String> loadData() {
		Vector <MoneyType> a=new Money().getMoney();
		double sum=0;
		
        List<String> data = new ArrayList<String>();
        for (int i=0;i<a.size();i++){
        	data.add(a.get(i).getType()+": "+a.get(i).getValue());
        	sum+=a.get(i).getValue();
        }
        
        data.add("×Ü¼Æ£º "+sum);
        return data;
	}
}
