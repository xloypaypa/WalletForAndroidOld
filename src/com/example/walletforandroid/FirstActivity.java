package com.example.walletforandroid;

import logic.wallet.Wallet;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FirstActivity extends Activity {
	Button showState,typeChange,backup,showDetail,useMoney,debt,reason;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);
		
		loadItem();
		loadAction();
	}

	private void loadAction() {
		showState.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent next=new Intent(FirstActivity.this,StateActivity.class);
				startActivity(next);
			}
		});
		
		typeChange.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent next=new Intent(FirstActivity.this,TypeActivity.class);
				startActivity(next);
			}
		});
		
		backup.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(FirstActivity.this)
				.setTitle("message")
				.setMessage("确定要撤销上一个操作")
				.setPositiveButton("确认", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Wallet user=new Wallet();
						user.backup();
					}
				})
				.setNegativeButton("取消", null)
				.show();
			}
		});
		
		showDetail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent next=new Intent(FirstActivity.this,DetailActivity.class);
				startActivity(next);
			}
		});
		
		useMoney.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent next=new Intent(FirstActivity.this,UseActivity.class);
				startActivity(next);
			}
		});
		
		debt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent next=new Intent(FirstActivity.this,DebtActivity.class);
				startActivity(next);
			}
		});
		
		reason.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent next=new Intent(FirstActivity.this,ReasonActivity.class);
				startActivity(next);
			}
		});
	}

	private void loadItem() {
		showState=(Button) this.findViewById(R.id.showStateButton);
		typeChange=(Button) this.findViewById(R.id.typeChange);
		backup=(Button) this.findViewById(R.id.backup);
		showDetail=(Button) this.findViewById(R.id.detail);
		useMoney=(Button) this.findViewById(R.id.useMoney);
		debt=(Button) this.findViewById(R.id.debt);
		reason=(Button) this.findViewById(R.id.reasonSystemButton);
	}
}
