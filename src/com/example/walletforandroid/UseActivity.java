package com.example.walletforandroid;

import java.util.*;

import logic.history.ReasonHistory;
import logic.wallet.Cost;
import logic.wallet.Money;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.*;
import android.widget.*;

public class UseActivity extends Activity {
	Button submit;
	Spinner type,io,reason;
	EditText num;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_use);
		
		loadItem();
		loadData();
		loadAction();
	}

	private void loadData() {
		List<String> allType=new ArrayList<String>();
		Vector <String> a=new Money().getAllTypeName();
		for (int i=0;i<a.size();i++){
			allType.add(a.get(i));
		}
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, allType);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		type.setAdapter(adapter);
		if (a.size()!=0) type.setSelection(0);
		
		List<String> allReason=new ArrayList<String>();
		Vector <String> b=new ReasonHistory().getAllReasonName();
		for (int i=0;i<b.size();i++){
			allReason.add(b.get(i));
		}
		ArrayAdapter<String> adr=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, allReason);
		adr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		reason.setAdapter(adr);
		if (b.size()!=0) reason.setSelection(0);
		
		List<String> list=new ArrayList<String>();
		list.add("expenditure");
		list.add("income");
		ArrayAdapter<String> ad=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		io.setAdapter(ad);
		io.setSelection(0);
	}

	private void loadAction() {
		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String typeAns,ioAns,reasonAns;
				final double numAns;
				if (type.getCount()==0) return ;
				typeAns=type.getSelectedItem().toString();
				ioAns=io.getSelectedItem().toString();
				if (num.getText().toString().length()==0) return ;
				numAns=Double.valueOf(num.getText().toString());
				reasonAns=reason.getSelectedItem().toString();
				
				new AlertDialog.Builder(UseActivity.this)
				.setTitle("确认信息")
				.setMessage(ioAns+" "+numAns+" to "+typeAns+". Reason: "+reasonAns)
				.setPositiveButton("确认", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Cost cost=new Cost();
						if (ioAns.equals("income")){
							cost.addChange(typeAns, -numAns, reasonAns);
							new ReasonHistory().addIncome(reasonAns, numAns);
						}
						else{
							cost.addChange(typeAns, numAns, reasonAns);
							new ReasonHistory().addExpenditure(reasonAns, numAns);
						}
						
						new AlertDialog.Builder(UseActivity.this)
						.setTitle("message")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setMessage("success")
						.setPositiveButton("ok", null)
						.show();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						num.setText("");
					}
				})
				.show();
			}
		});
	}

	private void loadItem() {
		submit=(Button) this.findViewById(R.id.submit);
		type=(Spinner) this.findViewById(R.id.typeChoice);
		io=(Spinner) this.findViewById(R.id.inOutChoice);
		num=(EditText) this.findViewById(R.id.numberInput);
		reason=(Spinner) this.findViewById(R.id.reasonChoice);
	}
}
