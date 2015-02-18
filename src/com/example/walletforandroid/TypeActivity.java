package com.example.walletforandroid;

import logic.wallet.Money;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class TypeActivity extends Activity {

	Button add,del,rename;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_type);
		
		loadItem();
		loadAction();
	}

	private void loadAction() {
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final EditText edit=new EditText(TypeActivity.this);
				
			    new AlertDialog.Builder(TypeActivity.this)
			    .setTitle("��������")
			    .setView(edit)
			    .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Money money=new Money();
						String type=edit.getText().toString();
						if (!money.moneyTypeExist(type)){
							new Money().addType(edit.getText().toString());
						}
					}
				})
				.setNegativeButton("ȡ��", null)
				.show();  
			}
		});
		
		del.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Money money=new Money();
				final String[] a;
				
				if (money.getMoney().size()==0){
					new AlertDialog.Builder(TypeActivity.this)
					.setTitle("message")
					.setMessage("û�����Ϳɹ�ɾ��")
					.setPositiveButton("ok", null)
					.show();
					return ;
				}
				
				a=new String[money.getMoney().size()];
				for (int i=0;i<money.getMoney().size();i++){
					a[i]=new String(money.getAllTypeName().get(i));
				}
				
				class ButtonOnClick implements DialogInterface.OnClickListener{
					private int index;
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which>=0){
							index=which;
						}else if (which==DialogInterface.BUTTON_POSITIVE){
							money.removeType(a[index]);
						}
					}
				}
				
				ButtonOnClick listener=new ButtonOnClick();
				new AlertDialog.Builder(TypeActivity.this)
				.setTitle("ѡ������")
				.setSingleChoiceItems(a, 0, listener)
			    .setPositiveButton("ȷ��", listener)
			    .setNegativeButton("ȡ��", listener)
			    .show();
			}
		});
		
		rename.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Money money=new Money();
				final String[] a;
				
				if (money.getAllTypeName().size()==0){
					new AlertDialog.Builder(TypeActivity.this)
					.setTitle("message")
					.setMessage("û�����Ϳɹ�������")
					.setPositiveButton("ok", null)
					.show();
					return ;
				}
				
				a=new String[money.getAllTypeName().size()];
				for (int i=0;i<money.getAllTypeName().size();i++){
					a[i]=new String(money.getAllTypeName().get(i));
				}
				
				class ButtonOnClick implements DialogInterface.OnClickListener{
					private int index;
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which>=0){
							index=which;
						}else if (which==DialogInterface.BUTTON_POSITIVE){
							final String pastName=a[index];
							final EditText edit=new EditText(TypeActivity.this);
							
							new AlertDialog.Builder(TypeActivity.this)
							.setTitle("����������")
							.setView(edit)
							.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									String nowName=edit.getText().toString();
									if (!money.moneyTypeExist(nowName)){
										money.renameType(pastName, nowName);
									}
								}
							})
							.setNegativeButton("ȡ��", null)
							.show();
						}
					}
				}
				
				ButtonOnClick listener=new ButtonOnClick();
				new AlertDialog.Builder(TypeActivity.this)
				.setTitle("ѡ������")
				.setSingleChoiceItems(a, 0, listener)
			    .setPositiveButton("ȷ��", listener)
			    .setNegativeButton("ȡ��", listener)
			    .show();
			}
		});
	}

	private void loadItem() {
		add=(Button) this.findViewById(R.id.addType);
		del=(Button) this.findViewById(R.id.delType);
		rename=(Button) this.findViewById(R.id.renameType);
	}
}
