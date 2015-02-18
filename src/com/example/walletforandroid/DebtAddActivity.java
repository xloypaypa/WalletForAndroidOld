package com.example.walletforandroid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import logic.wallet.Debt;
import logic.wallet.Money;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class DebtAddActivity extends Activity {
	Spinner type,rateType;
	EditText debtee,number,rate,deadline;
	Button next;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_debt_add);
		
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
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(DebtAddActivity.this, android.R.layout.simple_spinner_item, allType);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		type.setAdapter(adapter);
		if (a.size()!=0) type.setSelection(0);
		
		List<String> list=new ArrayList<String>();
		list.add("null");
		list.add("month");
		list.add("year");
		ArrayAdapter<String> ad=new ArrayAdapter<String>(DebtAddActivity.this, android.R.layout.simple_spinner_item, list);
		ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		rateType.setAdapter(ad);
		rateType.setSelection(0);
	}

	private void loadAction() {
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (type.getCount()==0) return;
				if (number.getText().toString().length()==0) return;
				if (rate.getText().toString().length()==0) return;
				
				final String debteeAns,typeAns,rateTypeAns,deadlinePreAns;
				final double numAns,rateAns;
				
				typeAns=type.getSelectedItem().toString();
				debteeAns=debtee.getText().toString();
				numAns=Double.valueOf(number.getText().toString());
				rateAns=Double.valueOf(rate.getText().toString());
				rateTypeAns=rateType.getSelectedItem().toString();
				deadlinePreAns=deadline.getText().toString();
				
				new AlertDialog.Builder(DebtAddActivity.this)
				.setTitle("确认信息")
				.setMessage(" creditor: "+debteeAns+"\r\n num: "+numAns+"\r\n rate: "+rateAns+"\r\n rate type: "+rateTypeAns+"\r\n to: "+typeAns+"\r\n deadline: "+deadlinePreAns)
				.setPositiveButton("确认", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Calendar c=Calendar.getInstance();
						try {
							SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
							
							c.setTime(df.parse(deadlinePreAns));
						} catch (ParseException e) {
							new AlertDialog.Builder(DebtAddActivity.this)
							.setTitle("error")
							.setIcon(android.R.drawable.ic_dialog_alert)
							.setMessage("日期输入错误")
							.setPositiveButton("ok", null)
							.show();
							return ;
						}
						Debt debt=new Debt();
						debt.addDebt(debteeAns, numAns, c, rateTypeAns, rateAns, typeAns);
						
						new AlertDialog.Builder(DebtAddActivity.this)
						.setTitle("message")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setMessage("success")
						.setPositiveButton("ok", null)
						.show();
					}
				})
				.setNegativeButton("取消", null)
				.show();
			}
		});
	}

	private void loadItem() {
		type=(Spinner) this.findViewById(R.id.typeChoiceForDebt);
		debtee=(EditText) this.findViewById(R.id.debteeInput);
		number=(EditText) this.findViewById(R.id.numberInputForDebt);
		rate=(EditText) this.findViewById(R.id.rateInput);
		rateType=(Spinner) this.findViewById(R.id.rateType);
		next=(Button) this.findViewById(R.id.buttonForChooseDeadline);
		deadline=(EditText) this.findViewById(R.id.deadlineInput);
	}
}
