package com.example.xlo.walletforandroid;

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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Vector;

import Action.DebtAddAction;
import Action.DebtRepayAction;
import database.viewer.DataViewer;
import database.viewer.DebtViewer;
import interfaceTool.DataLoader;
import type.DebtType;

public class DebtFragment extends Fragment {
    public Button add, repay;
    public TableLayout borrow, loan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.debt_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadItem();
        loadAction();
        loadData();
    }

    private void loadData() {
        try {
            DebtViewer viewer = new DebtViewer();
            viewer.loadData("debt");
            Vector<DebtType> debt = viewer.getDebtType("borrowing");
            for (int i = 0; i < debt.size(); i++) {
                DebtType now = debt.get(i);

                TableRow row = (TableRow) this.getActivity().getLayoutInflater().inflate(R.layout.debt_table_row, borrow, false);

                TextView debt_id = (TextView) row.findViewById(R.id.debt_id);
                TextView debt_creditor = (TextView) row.findViewById(R.id.debt_creditor);
                TextView debt_value = (TextView) row.findViewById(R.id.debt_value);
                TextView debt_deadline = (TextView) row.findViewById(R.id.debt_deadline);

                DecimalFormat df = new DecimalFormat("0.00");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                debt_id.setText(now.getID()+"");
                debt_creditor.setText(now.getCreditor());
                debt_value.setText(df.format(now.getValue()));
                debt_deadline.setText(sdf.format(now.getDeadline()));

                borrow.addView(row);
            }

            debt = viewer.getDebtType("loan");
            for (int i = 0; i < debt.size(); i++) {
                DebtType now = debt.get(i);
                TableRow row = (TableRow) this.getActivity().getLayoutInflater().inflate(R.layout.debt_table_row, loan, false);

                TextView debt_id = (TextView) row.findViewById(R.id.debt_id);
                TextView debt_creditor = (TextView) row.findViewById(R.id.debt_creditor);
                TextView debt_value = (TextView) row.findViewById(R.id.debt_value);
                TextView debt_deadline = (TextView) row.findViewById(R.id.debt_deadline);

                DecimalFormat df = new DecimalFormat("0.00");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                debt_id.setText(now.getID()+"");
                debt_creditor.setText(now.getCreditor());
                debt_value.setText(df.format(now.getValue()));
                debt_deadline.setText(sdf.format(now.getDeadline()));

                loan.addView(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAction() {
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DataViewer viewer=new DataViewer(); viewer.loadData("money");
                if (viewer.getAllItem().size()==0) return ;

                ActionDialog ad = new ActionDialog();
                ad.setActivity(DebtFragment.this.getActivity());
                ad.setLayout(R.layout.add_debt_action_layout);
                ad.setTitle("add debt");
                ad.build();

                Spinner type = (Spinner) ad.getView().findViewById(R.id.add_debt_action_type);
                EditText creidtor = (EditText) ad.getView().findViewById(R.id.add_debt_action_creditor);
                EditText value = (EditText) ad.getView().findViewById(R.id.add_debt_action_value);
                EditText deadline = (EditText) ad.getView().findViewById(R.id.add_debt_action_deadline);
                EditText rate = (EditText) ad.getView().findViewById(R.id.add_debt_action_rate);
                Spinner rateType = (Spinner) ad.getView().findViewById(R.id.add_debt_action_rate_type);

                final RadioButton borrowing = (RadioButton) ad.getView().findViewById(R.id.borrowing);
                final RadioButton loan = (RadioButton) ad.getView().findViewById(R.id.loan);

                borrowing.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (loan.isChecked()){
                            loan.setChecked(false);
                        }
                    }
                });

                loan.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (borrowing.isChecked()){
                            borrowing.setChecked(false);
                        }
                    }
                });

                DataLoader.loadAllType(ad.getView().getContext(), type);
                DataLoader.loadAllDebtRateType(ad.getView().getContext(), rateType);

                ad.setPositiveButton("submit", new DebtAddAction(ad.getView().getContext(), borrowing, loan, type, creidtor, value, deadline, rate, rateType));
                ad.setNegativeButton("cancel", null);
                ad.create();
                ad.show();
            }
        });
        repay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DataViewer viewer=new DataViewer(); viewer.loadData("money");
                if (viewer.getAllItem().size()==0) return ;
                viewer.loadData("debt");
                if (viewer.getAllItem().size()==0) return ;

                ActionDialog ad = new ActionDialog();
                ad.setActivity(DebtFragment.this.getActivity());
                ad.setLayout(R.layout.repay_debt_action_layout);
                ad.setTitle("repay debt");
                ad.build();

                Spinner type = (Spinner) ad.getView().findViewById(R.id.repay_debt_action_type);
                Spinner id = (Spinner) ad.getView().findViewById(R.id.repay_debt_action_id);
                EditText value = (EditText) ad.getView().findViewById(R.id.repay_debt_action_value);

                DataLoader.loadAllType(ad.getView().getContext(), type);
                DataLoader.loadAllDebtID(ad.getView().getContext(), id);

                ad.setPositiveButton("submit", new DebtRepayAction(ad.getView().getContext(), type, id, value));
                ad.setNegativeButton("cancel", null);
                ad.create();
                ad.show();
            }
        });
    }

    private void loadItem() {
        add = (Button) this.getActivity().findViewById(R.id.debt_action_add);
        repay = (Button) this.getActivity().findViewById(R.id.debt_action_repay);
        borrow = (TableLayout) this.getActivity().findViewById(R.id.borrow_debt_table);
        loan = (TableLayout) this.getActivity().findViewById(R.id.loan_debt_table);
    }
}
