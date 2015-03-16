package com.example.walletforandroid;

import java.util.ArrayList;
import java.util.List;

import logic.User;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class RegisterActivity extends Activity {
	EditText name,pass,rept;
	Spinner type;
	Button ok;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		loadItem();
		loadData();
		loadAction();
	}

	private void loadData() {
		List<String> list=new ArrayList<String>();
		list.add("normal");
		list.add("tree");
		ArrayAdapter<String> ad=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		type.setAdapter(ad);
		type.setSelection(0);
	}

	private void loadAction() {
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String sname,spass,srep,tp;
				sname=name.getText().toString();
				spass=pass.getText().toString();
				srep=rept.getText().toString();
				tp=type.getSelectedItem().toString();
				
				if (sname.indexOf("[")>=0){
					new AlertDialog.Builder(RegisterActivity.this)
					.setTitle("message")
					.setMessage("输入请不要包含[")
					.setPositiveButton("ok", null)
					.show();
					return ;
				}
				if (spass.indexOf("[")>=0){
					new AlertDialog.Builder(RegisterActivity.this)
					.setTitle("message")
					.setMessage("输入请不要包含[")
					.setPositiveButton("ok", null)
					.show();
					return ;
				}
				if (sname.length()==0){
					new AlertDialog.Builder(RegisterActivity.this)
					.setTitle("message")
					.setMessage("请输入名称")
					.setPositiveButton("ok", null)
					.show();
					return ;
				}
				if (spass.length()==0){
					new AlertDialog.Builder(RegisterActivity.this)
					.setTitle("message")
					.setMessage("请输入密码")
					.setPositiveButton("ok", null)
					.show();
					return ;
				}
				if (!srep.equals(spass)){
					new AlertDialog.Builder(RegisterActivity.this)
					.setTitle("message")
					.setMessage("两次密码不一致")
					.setPositiveButton("ok", null)
					.show();
					return ;
				}
				
				if (!User.userExist(sname)){
					User.addUser(sname, spass,tp);
					new AlertDialog.Builder(RegisterActivity.this)
					.setTitle("message")
					.setMessage("用户已注册")
					.setPositiveButton("ok", null)
					.show();
				}else{
					new AlertDialog.Builder(RegisterActivity.this)
					.setTitle("message")
					.setMessage("用户已存在")
					.setPositiveButton("ok", null)
					.show();
				}
			}
		});
	}

	private void loadItem() {
		name=(EditText) this.findViewById(R.id.newUserNameInput);
		pass=(EditText) this.findViewById(R.id.newUserPasswordInput);
		rept=(EditText) this.findViewById(R.id.newUserPasswordRepeat);
		type=(Spinner) this.findViewById(R.id.newUserReasonTypeChoice);
		ok=(Button) this.findViewById(R.id.newUserRegister);
	}
}
