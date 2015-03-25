package com.example.lt.walletforandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Vector;

import Action.TransferAction;
import Action.UseAction;
import interfaceTool.DataLoader;
import logic.wallet.Money;
import type.MoneyType;

public class StatusFragment extends Fragment {
	public Button use,transfer;
	public TableLayout status;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.status_layout, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		loadItem();
		loadAction();
		loadData();
	}

	private void loadData() {
        Vector<MoneyType> a= new Money().getMoney();
        for (int i=0;i<a.size();i++){
            TableRow row=(TableRow) this.getActivity().getLayoutInflater().inflate(R.layout.status_table_row, status, false);

            TextView type = (TextView)row.findViewById(R.id.status_money_type);
            TextView value = (TextView)row.findViewById(R.id.status_money_type_value);

            type.setText(a.get(i).getType());
            value.setText(a.get(i).getValue()+"");

            status.addView(row);
        }
	}

	private void loadAction() {
		use.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActionDialog ad=new ActionDialog();
				ad.setActivity(StatusFragment.this.getActivity());
				ad.setLayout(R.layout.use_action_layout);
				ad.setTitle("use");
				ad.build();

                Spinner type= (Spinner) ad.getView().findViewById(R.id.use_action_type);
                Spinner reason= (Spinner) ad.getView().findViewById(R.id.use_action_reason);
                EditText number= (EditText) ad.getView().findViewById(R.id.use_action_value);
                final RadioButton expenditure= (RadioButton) ad.getView().findViewById(R.id.use_action_expenditure);
                final RadioButton income= (RadioButton) ad.getView().findViewById(R.id.use_action_income);
                DataLoader.loadAllType(ad.getView().getContext(),type);
                DataLoader.loadAllReason(ad.getView().getContext(),reason);

                expenditure.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (income.isChecked()){
                            income.setChecked(false);
                        }
                    }
                });

                income.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("click");
                        if (expenditure.isChecked()){
                            expenditure.setChecked(false);
                        }
                    }
                });

                ad.setPositiveButton("submit", new UseAction(ad.getView().getContext(),type,expenditure,income,number,reason));
				ad.setNegativeButton("cancel", null);
				ad.create();
				ad.show();
			}
		});
		transfer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActionDialog ad=new ActionDialog();
				ad.setActivity(StatusFragment.this.getActivity());
				ad.setLayout(R.layout.transfer_action_layout);
				ad.setTitle("transfer");
				ad.build();

                Spinner from= (Spinner) ad.getView().findViewById(R.id.transfer_action_from);
                Spinner to= (Spinner) ad.getView().findViewById(R.id.transfer_action_to);
                EditText value= (EditText) ad.getView().findViewById(R.id.transfer_action_value);

                DataLoader.loadAllType(ad.getView().getContext(),from);
                DataLoader.loadAllType(ad.getView().getContext(),to);

				ad.setPositiveButton("submit", new TransferAction(ad.getView().getContext(),from, to, value));
				ad.setNegativeButton("cancel", null);
				ad.create();
				ad.show();
			}
		});
	}

	private void loadItem() {
		use=(Button) this.getActivity().findViewById(R.id.status_action_use);
		transfer=(Button) this.getActivity().findViewById(R.id.status_action_transfer);
		status=(TableLayout) this.getActivity().findViewById(R.id.status_table);
	}
	
}
