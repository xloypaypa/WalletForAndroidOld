package com.example.lt.walletforandroid;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Vector;

import Action.DebtAddAction;
import Action.DebtRepayAction;
import interfaceTool.DataLoader;
import logic.wallet.Debt;
import type.DebtType;

public class DebtFragment extends Fragment {
	public Button add,repay;
	public TableLayout status;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.debt_layout, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		loadItem();
		loadAction();
		loadData();
	};

	private void loadData() {
        Vector<DebtType> a=new Debt().getDebt();
        for (int i=0;i<a.size();i++){
            TableRow row=(TableRow) this.getActivity().getLayoutInflater().inflate(R.layout.debt_table_row, status, false);

            TextView id=(TextView) row.findViewById(R.id.debt_id);
            TextView creditor=(TextView) row.findViewById(R.id.debt_creditor);
            TextView value=(TextView) row.findViewById(R.id.debt_value);
            TextView deadline=(TextView) row.findViewById(R.id.debt_deadline);

            id.setText(a.get(i).getID()+"");
            creditor.setText(a.get(i).getCreditor());
            value.setText(a.get(i).getValue()+"");
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            deadline.setText(sdf.format(a.get(i).getDeadline()));
            if (a.get(i).beExceed()){
                deadline.setBackgroundColor(Color.RED);
            }
            status.addView(row);
        }
	}

	private void loadAction() {
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActionDialog ad=new ActionDialog();
				ad.setActivity(DebtFragment.this.getActivity());
				ad.setLayout(R.layout.add_debt_action_layout);
				ad.setTitle("add debt");
				ad.build();

                Spinner type= (Spinner) ad.getView().findViewById(R.id.add_debt_action_type);
                EditText creidtor= (EditText) ad.getView().findViewById(R.id.add_debt_action_creditor);
                EditText value= (EditText) ad.getView().findViewById(R.id.add_debt_action_value);
                EditText deadline= (EditText) ad.getView().findViewById(R.id.add_debt_action_deadline);
                EditText rate= (EditText) ad.getView().findViewById(R.id.add_debt_action_rate);
                Spinner rateType= (Spinner) ad.getView().findViewById(R.id.add_debt_action_rate_type);

                DataLoader.loadAllType(ad.getView().getContext(),type);
                DataLoader.loadAllDebtRateType(ad.getView().getContext(), rateType);

				ad.setPositiveButton("submit", new DebtAddAction(ad.getView().getContext(),type,creidtor,value,deadline,rate,rateType));
				ad.setNegativeButton("cancel", null);
				ad.create();
				ad.show();
			}
		});
		repay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActionDialog ad=new ActionDialog();
				ad.setActivity(DebtFragment.this.getActivity());
				ad.setLayout(R.layout.repay_debt_action_layout);
				ad.setTitle("repay debt");
				ad.build();

                Spinner type= (Spinner) ad.getView().findViewById(R.id.repay_debt_action_type);
                EditText id= (EditText) ad.getView().findViewById(R.id.repay_debt_action_id);
                EditText value= (EditText) ad.getView().findViewById(R.id.repay_debt_action_value);

                DataLoader.loadAllType(ad.getView().getContext(),type);

				ad.setPositiveButton("submit", new DebtRepayAction(ad.getView().getContext(), type,id,value));
				ad.setNegativeButton("cancel", null);
				ad.create();
				ad.show();
			}
		});
	}

	private void loadItem() {
		add=(Button) this.getView().findViewById(R.id.debt_action_add);
		repay=(Button) this.getView().findViewById(R.id.debt_action_repay);
		status=(TableLayout) this.getView().findViewById(R.id.debt_table);
	}
}
