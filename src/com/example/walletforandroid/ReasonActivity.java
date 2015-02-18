package com.example.walletforandroid;

import logic.history.ReasonHistory;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ReasonActivity extends Activity {
	
	Button resaonState,add,rename,remove;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reason);
		
		loadItem();
		loadAction();
	}

	private void loadAction() {
		this.add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final EditText edit=new EditText(ReasonActivity.this);
				new AlertDialog.Builder(ReasonActivity.this)
			    .setTitle("输入名称")
			    .setView(edit)
			    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						new ReasonHistory().addReason(edit.getText().toString());
					}
				})
				.setNegativeButton("取消", null)
				.show();
			}
		});
		
		this.remove.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final ReasonHistory rh=new ReasonHistory();
				final String[] a;
				
				if (rh.getAllReasonName().size()==0){
					new AlertDialog.Builder(ReasonActivity.this)
					.setTitle("message")
					.setMessage("没有原因可供删除")
					.setPositiveButton("ok", null)
					.show();
					return ;
				}
				
				a=new String[rh.getAllReasonName().size()];
				for (int i=0;i<rh.getAllReasonName().size();i++){
					a[i]=new String(rh.getAllReasonName().get(i));
				}
				
				class ButtonOnClick implements DialogInterface.OnClickListener{
					private int index;
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which>=0){
							index=which;
						}else if (which==DialogInterface.BUTTON_POSITIVE){
							rh.deleteReason(a[index]);
						}
					}
				}
				
				ButtonOnClick listener=new ButtonOnClick();
				new AlertDialog.Builder(ReasonActivity.this)
				.setTitle("选择原因")
				.setSingleChoiceItems(a, 0, listener)
			    .setPositiveButton("确定", listener)
			    .setNegativeButton("取消", listener)
			    .show();
			}
		});
		
		rename.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final ReasonHistory rh=new ReasonHistory();
				final String[] a;
				
				if (rh.getAllReasonName().size()==0){
					new AlertDialog.Builder(ReasonActivity.this)
					.setTitle("message")
					.setMessage("没有原因可供重命名")
					.setPositiveButton("ok", null)
					.show();
					return ;
				}
				
				a=new String[rh.getAllReasonName().size()];
				for (int i=0;i<rh.getAllReasonName().size();i++){
					a[i]=new String(rh.getAllReasonName().get(i));
				}
				
				class ButtonOnClick implements DialogInterface.OnClickListener{
					private int index;
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which>=0){
							index=which;
						}else if (which==DialogInterface.BUTTON_POSITIVE){
							final String pastName=a[index];
							final EditText edit=new EditText(ReasonActivity.this);
							
							new AlertDialog.Builder(ReasonActivity.this)
							.setTitle("输入新名称")
							.setView(edit)
							.setPositiveButton("确定", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									String nowName=edit.getText().toString();
									if (!rh.reasonExist(nowName)){
										rh.changeReasonName(pastName, nowName);
									}
								}
							})
							.setNegativeButton("取消", null)
							.show();
						}
					}
				}
				
				ButtonOnClick listener=new ButtonOnClick();
				new AlertDialog.Builder(ReasonActivity.this)
				.setTitle("选择类型")
				.setSingleChoiceItems(a, 0, listener)
			    .setPositiveButton("确定", listener)
			    .setNegativeButton("取消", listener)
			    .show();
			}
		});
		
		this.resaonState.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent next=new Intent(ReasonActivity.this,ReasonStateActivity.class);
				startActivity(next); 
			}
		});
	}

	private void loadItem() {
		this.resaonState=(Button) this.findViewById(R.id.reasonStateButton);
		this.add=(Button) this.findViewById(R.id.addReasonButton);
		this.rename=(Button) this.findViewById(R.id.renameReasonButton);
		this.remove=(Button) this.findViewById(R.id.removeReasonButton);
	}
}
