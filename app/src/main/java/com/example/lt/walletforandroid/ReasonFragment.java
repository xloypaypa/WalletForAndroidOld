package com.example.lt.walletforandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import java.util.Vector;

import Action.ReasonAddAction;
import Action.ReasonRemoveAction;
import Action.ReasonRenameAction;
import Action.TreeReasonAddAction;
import Action.TreeReasonRenameAction;
import interfaceTool.DataLoader;
import logic.User;
import logic.history.ReasonHistory;
import type.ReasonType;

public class ReasonFragment extends Fragment {
	public Button add,rename,remove;
	public TableLayout normal;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.reason_layout, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		loadItem();
        if (User.userReason.equals("tree")){
            loadTreeAction();
        }else{
            loadNormalAction();
        }

		loadData();
	}

	private void loadData() {
        Vector<ReasonType> a=new ReasonHistory().getReasonType();
        for (int i=0;i<a.size();i++){
            TableRow row=(TableRow) this.getActivity().getLayoutInflater().inflate(R.layout.reason_normal_table_row, normal, false);
            TextView name=(TextView) row.findViewById(R.id.reason_name);
            TextView income=(TextView) row.findViewById(R.id.reason_income);
            TextView expenditure=(TextView) row.findViewById(R.id.reason_expenditure);

            name.setText(a.get(i).getName());
            income.setText(a.get(i).getIncome()+"");
            expenditure.setText(a.get(i).getExpenditure()+"");

            normal.addView(row);
        }

        if (User.userReason.equals("tree")){
            loadTree();
        }
	}
	
	private void loadTree(){
		FragmentManager fragmentManager=this.getChildFragmentManager();
		fragmentManager.beginTransaction()
		.replace(R.id.reason_tree_container, new ReasonTreeFragment()).commit();
	}

    private void loadNormalAction(){
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionDialog ad=new ActionDialog();
                ad.setActivity(ReasonFragment.this.getActivity());
                ad.setLayout(R.layout.add_reason_action_layout);
                ad.setTitle("add reason");
                ad.build();

                EditText name= (EditText) ad.getView().findViewById(R.id.add_reason_action_name);

                ad.setPositiveButton("submit", new ReasonAddAction(ad.getView().getContext(),name));
                ad.setNegativeButton("cancel", null);
                ad.create();
                ad.show();
            }
        });
        rename.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionDialog ad=new ActionDialog();
                ad.setActivity(ReasonFragment.this.getActivity());
                ad.setLayout(R.layout.rename_reason_action);
                ad.setTitle("rename reason");
                ad.build();

                Spinner past= (Spinner) ad.getView().findViewById(R.id.rename_reason_action_type);
                EditText name= (EditText) ad.getView().findViewById(R.id.rename_reason_action_name);

                DataLoader.loadAllReason(ad.getView().getContext(), past);

                ad.setPositiveButton("submit", new ReasonRenameAction(ad.getView().getContext(),past,name));
                ad.setNegativeButton("cancel", null);
                ad.create();
                ad.show();
            }
        });

        remove.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionDialog ad=new ActionDialog();
                ad.setActivity(ReasonFragment.this.getActivity());
                ad.setLayout(R.layout.remove_tree_reason_action_layout);
                ad.setTitle("remove reason");
                ad.build();

                Spinner name= (Spinner) ad.getView().findViewById(R.id.remove_tree_reason_action_name);

                DataLoader.loadAllReason(ad.getView().getContext(),name);

                ad.setPositiveButton("submit", new ReasonRemoveAction(ad.getView().getContext(),name));
                ad.setNegativeButton("cancel", null);
                ad.create();
                ad.show();
            }
        });
    }

	private void loadTreeAction() {
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActionDialog ad=new ActionDialog();
				ad.setActivity(ReasonFragment.this.getActivity());
				ad.setLayout(R.layout.add_tree_reason_action);
				ad.setTitle("add reason");
				ad.build();

                Spinner father= (Spinner) ad.getView().findViewById(R.id.add_tree_reason_action_father);
                EditText name= (EditText) ad.getView().findViewById(R.id.add_tree_reason_action_name);
                EditText min= (EditText) ad.getView().findViewById(R.id.add_tree_reason_action_min);
                EditText max= (EditText) ad.getView().findViewById(R.id.add_tree_reason_action_max);
                EditText rank= (EditText) ad.getView().findViewById(R.id.add_tree_reason_action_rank);

                DataLoader.loadAllReasonWithRoot(ad.getView().getContext(),father);

				ad.setPositiveButton("submit", new TreeReasonAddAction(ad.getView().getContext(),father,name,min,max,rank));
				ad.setNegativeButton("cancel", null);
				ad.create();
				ad.show();
			}
		});
		rename.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActionDialog ad=new ActionDialog();
				ad.setActivity(ReasonFragment.this.getActivity());
				ad.setLayout(R.layout.rename_tree_reason_action_layout);
				ad.setTitle("rename reason");
				ad.build();

                Spinner past= (Spinner) ad.getView().findViewById(R.id.rename_tree_reason_action_name);
                Spinner father= (Spinner) ad.getView().findViewById(R.id.rename_tree_reason_action_father);
                EditText name= (EditText) ad.getView().findViewById(R.id.rename_tree_reason_action_new_name);
                EditText min= (EditText) ad.getView().findViewById(R.id.rename_tree_reason_action_min);
                EditText max= (EditText) ad.getView().findViewById(R.id.rename_tree_reason_action_max);
                EditText rank= (EditText) ad.getView().findViewById(R.id.rename_tree_reason_action_rank);

                DataLoader.loadAllReason(ad.getView().getContext(),past);
                DataLoader.loadAllReasonWithRoot(ad.getView().getContext(),father);

				ad.setPositiveButton("submit", new TreeReasonRenameAction(ad.getView().getContext(),past,father,name,min,max,rank));
				ad.setNegativeButton("cancel", null);
				ad.create();
				ad.show();
			}
		});
		
		remove.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActionDialog ad=new ActionDialog();
				ad.setActivity(ReasonFragment.this.getActivity());
				ad.setLayout(R.layout.remove_tree_reason_action_layout);
				ad.setTitle("remove reason");
				ad.build();

                Spinner name= (Spinner) ad.getView().findViewById(R.id.remove_tree_reason_action_name);

                DataLoader.loadAllReason(ad.getView().getContext(),name);

				ad.setPositiveButton("submit", new ReasonRemoveAction(ad.getView().getContext(),name));
				ad.setNegativeButton("cancel", null);
				ad.create();
				ad.show();
			}
		});
	}

	private void loadItem() {
		add=(Button) this.getView().findViewById(R.id.reason_action_add);
		rename=(Button) this.getView().findViewById(R.id.reason_action_rename);
		remove=(Button) this.getView().findViewById(R.id.reason_action_remove);
		normal=(TableLayout) this.getView().findViewById(R.id.reason_normal_table);
	};
}
