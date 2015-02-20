package com.example.walletforandroid;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import logic.history.TreeReasonHistory;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ReasonChangeActivity extends Activity {
	
	Spinner father,aim;
	EditText name,max,min,rank;
	Button ok;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reason_change);
		
		loadItem();
		loadData();
		loadAction();
	}

	private void loadAction() {
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String am=aim.getSelectedItem().toString();
				String fa=father.getSelectedItem().toString();
				String na=name.getText().toString();
				double ma=Double.valueOf(max.getText().toString());
				double mi=Double.valueOf(min.getText().toString());
				double tp=Double.valueOf(rank.getText().toString()); int ra=(int) tp;
				
				if (ra>5) ra=5;
				if (ra<0) ra=0;
				
				if (na.indexOf("[")>=0){
					new AlertDialog.Builder(ReasonChangeActivity.this)
					.setTitle("message")
					.setMessage("不要包含‘[’")
					.setPositiveButton("ok", null)
					.show();
					return ;
				}
				if (!na.equals(am)&&new TreeReasonHistory().reasonExist(na)||na.equals("root")){
					new AlertDialog.Builder(ReasonChangeActivity.this)
					.setTitle("message")
					.setMessage("原因已存在")
					.setPositiveButton("ok", null)
					.show();
					return ;
				}
				
				if (fa.equals(am)){
					new AlertDialog.Builder(ReasonChangeActivity.this)
					.setTitle("message")
					.setMessage("原因不应该是父亲")
					.setPositiveButton("ok", null)
					.show();
					return ;
				}
				
				new TreeReasonHistory().changeReason(am, fa, na, mi, ma, ra);
				
				new AlertDialog.Builder(ReasonChangeActivity.this)
				.setTitle("message")
				.setMessage("成功.")
				.setPositiveButton("ok", null)
				.show();
			}
		});
	}

	private void loadData() {
		List<String> allType=new ArrayList<String>();
		List<String> al=new ArrayList<String>();
		Vector <String> a=new TreeReasonHistory().getAllReasonName();
		
		allType.add("root");
		for (int i=0;i<a.size();i++){
			allType.add(a.get(i));
			al.add(a.get(i));
		}
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, allType);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		father.setAdapter(adapter);
		if (a.size()!=0) father.setSelection(0);
		
		ArrayAdapter<String> ad=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, al);
		aim.setAdapter(ad);
		if (a.size()!=0) aim.setSelection(0);
	}

	private void loadItem() {
		aim=(Spinner) this.findViewById(R.id.changeReasonChoice);
		father=(Spinner) this.findViewById(R.id.changeReasonFather);
		name=(EditText) this.findViewById(R.id.changeReasonName);
		min=(EditText) this.findViewById(R.id.changeReasonMin);
		max=(EditText) this.findViewById(R.id.changeReasonMax);
		rank=(EditText) this.findViewById(R.id.changeReasonRank);
		ok=(Button) this.findViewById(R.id.changeReasonSubmit);
	}
}
