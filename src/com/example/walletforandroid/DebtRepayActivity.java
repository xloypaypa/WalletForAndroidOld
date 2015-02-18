package com.example.walletforandroid;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import logic.wallet.Debt;
import logic.wallet.Money;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class DebtRepayActivity extends Activity {
	Button submit;
	Spinner type;
	TextView id,num;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_debt_repay);
		
		loadItem();
		loadData();
		loadAction();
	}

	private void loadAction() {
		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (id.getText().toString().length()==0) return ;
				if (num.getText().toString().length()==0) return ;
				if (type.getCount()==0) return ;
				
				final String t;
				final int d,n;
				final Debt debt=new Debt();
				
				t=type.getSelectedItem().toString();
				d=Integer.valueOf(id.getText().toString());
				n=Integer.valueOf(num.getText().toString());
				
				if (!debt.debtExist(d)){
					new AlertDialog.Builder(DebtRepayActivity.this)
					.setTitle("message")
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setMessage("债务不存在")
					.setPositiveButton("ok", null)
					.show();
					return ;
				}
				
				if (debt.getDebtByID(d).getMaxRepay()<n){
					new AlertDialog.Builder(DebtRepayActivity.this)
					.setTitle("message")
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setMessage("最多仅需要："+debt.getDebtByID(d).getMaxRepay())
					.setPositiveButton("ok", null)
					.show();
					return ;
				}
				
				new AlertDialog.Builder(DebtRepayActivity.this)
				.setTitle("review")
				.setMessage("用 "+t+" 偿还id为 "+d+" 的债务中的 "+n+"\r\n注意此操作不能回退")
				.setPositiveButton("ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						debt.repay(d, n, t);
						
						new AlertDialog.Builder(DebtRepayActivity.this)
						.setTitle("message")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setMessage("success")
						.setPositiveButton("ok", null)
						.show();
					}
				})
				.setNegativeButton("cancel", null)
				.show();
			}
		});
	}

	private void loadData() {
		List<String> allType=new ArrayList<String>();
		Vector <String> a=new Money().getAllTypeName();
		for (int i=0;i<a.size();i++){
			allType.add(a.get(i));
		}
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(DebtRepayActivity.this, android.R.layout.simple_spinner_item, allType);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		type.setAdapter(adapter);
		if (a.size()!=0) type.setSelection(0);
	}

	private void loadItem() {
		type=(Spinner) this.findViewById(R.id.typeChoiceForRepay);
		id=(TextView) this.findViewById(R.id.debtIDInput);
		num=(TextView) this.findViewById(R.id.numberInputForRepay);
		submit=(Button) this.findViewById(R.id.submitForRepay);
	}
}
