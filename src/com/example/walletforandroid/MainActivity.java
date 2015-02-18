package com.example.walletforandroid;

import java.util.Vector;



import logic.User;
import logic.history.History;
import logic.wallet.Wallet;
import database.DataBase;
import database.HHD;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	Button login,register;
	EditText name,pass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		loadDB();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		loadItem();
		loadAction();
	}

	private void loadDB() {
		if (!HHD.folderExiste("/storage/sdcard0/Bank/")){
			HHD.createFolder("/storage/sdcard0/", "Bank");
		}
		if (!HHD.fileExiste("/storage/sdcard0/Bank/bank root file.txt")){
			HHD.createFile("/storage/sdcard0/Bank/", "bank root file.txt");
			HHD.writeFile("/storage/sdcard0/Bank/bank root file.txt", "/storage/sdcard0/Bank/");
		}
		
		Vector <String> root=HHD.readFile("/storage/sdcard0/Bank/bank root file.txt");
		DataBase.Root=root.get(0);
		
		User user=new User();
		user.loadUser();
	}

	private void loadAction() {
		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String sname,spass;
				sname=name.getText().toString();
				spass=pass.getText().toString();
				
				User user=new User();
				if (user.checkUser(sname, spass)){
					loginAction(sname, spass, user);
				}else{
					new AlertDialog.Builder(MainActivity.this)
					.setTitle("message")
					.setMessage("用户名或密码错误")
					.setPositiveButton("ok", null)
					.show();
					return ;
				}
			}
		});
		
		register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String sname,spass;
				sname=name.getText().toString();
				spass=pass.getText().toString();
				
				if (sname.indexOf("[")>=0){
					new AlertDialog.Builder(MainActivity.this)
					.setTitle("message")
					.setMessage("输入请不要包含[")
					.setPositiveButton("ok", null)
					.show();
					return ;
				}
				if (spass.indexOf("[")>=0){
					new AlertDialog.Builder(MainActivity.this)
					.setTitle("message")
					.setMessage("输入请不要包含[")
					.setPositiveButton("ok", null)
					.show();
					return ;
				}
				if (sname.length()==0){
					new AlertDialog.Builder(MainActivity.this)
					.setTitle("message")
					.setMessage("请输入名称")
					.setPositiveButton("ok", null)
					.show();
					return ;
				}
				if (spass.length()==0){
					new AlertDialog.Builder(MainActivity.this)
					.setTitle("message")
					.setMessage("请输入密码")
					.setPositiveButton("ok", null)
					.show();
					return ;
				}
				
				User user=new User();
				if (!user.userExist(sname)){
					user.addUser(sname, spass);
					loginAction(sname, spass, user);
				}else{
					new AlertDialog.Builder(MainActivity.this)
					.setTitle("message")
					.setMessage("用户已存在")
					.setPositiveButton("ok", null)
					.show();
				}
			}
		});
	}

	private void loadItem() {
		login=(Button) this.findViewById(R.id.loginButton);
		register=(Button) this.findViewById(R.id.registerButton);
		name=(EditText) this.findViewById(R.id.userNameInput);
		name.requestFocus();
		pass=(EditText) this.findViewById(R.id.passwordInput);
	}
	
	public void loginAction(String name, String pass, User user) {
		user.login(name, pass);
		
		Wallet wallet=new Wallet();
		History history=new History();
		wallet.loadWallet();
		history.loadHistory();
		
		Intent next=new Intent(MainActivity.this,FirstActivity.class);
		startActivity(next);
	}
}
