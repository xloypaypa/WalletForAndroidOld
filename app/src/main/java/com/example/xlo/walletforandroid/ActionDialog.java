package com.example.xlo.walletforandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

public class ActionDialog {
	int layout;
	String title;
	Activity activity;
	View view;
	AlertDialog.Builder builder;
	AlertDialog ans;
	
	public void setLayout(int layout){
		this.layout=layout;
	}
	
	public void setTitle(String title){
		this.title=title;
	}
	
	public void setActivity(Activity activity){
		this.activity=activity;
	}
	
	@SuppressLint("InflateParams")
	public void build(){
		LayoutInflater factory = LayoutInflater.from(activity);
		view = factory.inflate(this.layout, null);
		builder=new AlertDialog.Builder(activity);
		builder.setTitle(title);
        builder.setView(view);
	}
	
	public View getView(){
		return this.view;
	}
	
	public AlertDialog getDialog(){
		return ans;
	}
	
	public void create(){
		ans=builder.create();
	}
	
	public void setCancelAble(boolean flag){
		builder.setCancelable(flag); 
	}
	
	public void setPositiveButton(String name,DialogInterface.OnClickListener action){
		builder.setPositiveButton(name, action);
	}
	
	public void setNegativeButton(String name,DialogInterface.OnClickListener action){
		builder.setNegativeButton(name, action);
	}
	
	public void show(){
		ans.show();
	}
}
