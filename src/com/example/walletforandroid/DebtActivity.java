package com.example.walletforandroid;

import logic.wallet.Debt;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DebtActivity extends Activity {
	Button show,add,repay,update;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_debt);
		
		loadItem();
		loadAction();
	}

	private void loadAction() {
		show.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent next=new Intent(DebtActivity.this,DebtDetailActivity.class);
				startActivity(next);
			}
		});
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent next=new Intent(DebtActivity.this,DebtAddActivity.class);
				startActivity(next);
			}
		});
		update.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Debt debt=new Debt();
				debt.updateAll();
			}
		});
		repay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent next=new Intent(DebtActivity.this,DebtRepayActivity.class);
				startActivity(next);
			}
		});
	}

	private void loadItem() {
		show=(Button) this.findViewById(R.id.debtDetail);
		add=(Button) this.findViewById(R.id.addDebt);
		repay=(Button) this.findViewById(R.id.repayDebt);
		update=(Button) this.findViewById(R.id.updateDebt);
	}
}
