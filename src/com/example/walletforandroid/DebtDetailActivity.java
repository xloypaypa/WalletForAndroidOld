package com.example.walletforandroid;

import java.text.SimpleDateFormat;
import java.util.*;

import logic.wallet.Debt;
import logic.wallet.Wallet;
import type.*;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;

public class DebtDetailActivity extends Activity {
	Spinner debtID;
	TextView debtee,value,starting,deadline,rate,type;
	List <String> list;
	Wallet user;
	Debt debt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_debt_detail);
		
		loadItem();
		loadData();
		loadAction();
	}

	private void loadData() {
		list=new ArrayList<String>();
		debt=new Debt();
		for (int i=0;i<debt.getDebt().size();i++){
			list.add(debt.getDebt().get(i).getID()+"");
		}
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		debtID.setAdapter(adapter);
	}

	private void loadAction() {
		debtID.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				DebtType dt=new DebtType();
				
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());
				int pos=Integer.valueOf(debtID.getSelectedItem().toString());
				dt=debt.getDebtByID(pos);
				
				debtee.setText("creditor: "+dt.getCreditor());
				value.setText("value: "+dt.getValue());
				starting.setText("starting time: "+df.format(dt.getStartingTime().getTime()));
				deadline.setText("deadline: "+df.format(dt.getDeadline().getTime()));
				rate.setText("rate: "+dt.getRate().getRate()+"");
				type.setText("rate type: "+dt.getRate().getType());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				return ;
			}
		});
	}

	private void loadItem() {
		debtID=(Spinner) this.findViewById(R.id.debtId);
		debtee=(TextView) this.findViewById(R.id.debtee);
		value=(TextView) this.findViewById(R.id.value);
		starting=(TextView) this.findViewById(R.id.startingTime);
		deadline=(TextView) this.findViewById(R.id.deadline);
		rate=(TextView) this.findViewById(R.id.rate);
		type=(TextView) this.findViewById(R.id.type);
	}
}