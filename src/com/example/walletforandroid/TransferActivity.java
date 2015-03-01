package com.example.walletforandroid;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import logic.wallet.Cost;
import logic.wallet.Money;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class TransferActivity extends Activity {
	
	Spinner from,to;
	EditText num;
	Button ok;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transfer);
		
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
		from.setAdapter(adapter);
		if (a.size()!=0) from.setSelection(0);
		
		ArrayAdapter<String> ad=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, allType);
		ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		to.setAdapter(adapter);
		if (a.size()!=0) to.setSelection(0);
	}

	private void loadAction() {
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String f=from.getSelectedItem().toString();
				String t=to.getSelectedItem().toString();
				double val=Double.valueOf(num.getText().toString());
				if (f.equals(t)){
					new AlertDialog.Builder(TransferActivity.this)
					.setTitle("error")
					.setMessage("两个类型不应该一样")
					.setPositiveButton("ok", null)
					.show();
					return ;
				}
				
				Cost cost=new Cost();
				cost.transfer(f, t, val);
				
				new AlertDialog.Builder(TransferActivity.this)
				.setTitle("message")
				.setMessage("success")
				.setPositiveButton("ok", null)
				.show();
			}
		});
	}

	private void loadItem() {
		from=(Spinner) this.findViewById(R.id.fromtype);
		to=(Spinner) this.findViewById(R.id.aimType);
		num=(EditText) this.findViewById(R.id.transferNumber);
		ok=(Button) this.findViewById(R.id.transferSubmit);
	}
}
