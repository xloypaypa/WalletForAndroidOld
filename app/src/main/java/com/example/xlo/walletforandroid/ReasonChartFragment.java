package com.example.xlo.walletforandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.afree.data.general.DefaultPieDataset;

import java.util.Vector;

import database.viewer.DataViewer;
import database.viewer.TreeReasonViewer;
import interfaceTool.ReasonChart;
import type.ReasonTreeNodeType;
import type.ReasonType;
import type.Type;

/**
 * Created by LT on 2015/3/22.
 *
 */
public class ReasonChartFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.reason_chart_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ReasonChart exp=new ReasonChart();
        exp.drawChart(getReasonExpenditureChartData(),"expenditure");
        ImageView iv= (ImageView) this.getActivity().findViewById(R.id.reason_chart_expenditure);
        iv.setImageBitmap(exp.getBitmap());

        ReasonChart ic=new ReasonChart();
        ic.drawChart(getReasonIncomeChartData(), "income");
        ImageView ev= (ImageView) this.getActivity().findViewById(R.id.reason_chart_income);
        ev.setImageBitmap(ic.getBitmap());
    }

    private DefaultPieDataset getReasonIncomeChartData(){
        TreeReasonViewer tree=new TreeReasonViewer(); tree.loadData("reason");
        Vector<ReasonType> allReason=loadReason();
        DefaultPieDataset ans=new DefaultPieDataset();
        for (int i=0;i<allReason.size();i++){
            ReasonType rt=allReason.get(i);
            double value=tree.solveIncome((ReasonTreeNodeType) rt);
            if (value>=0.01){
                ans.setValue(rt.getName(), value);
            }
        }
        return ans;
    }

    private DefaultPieDataset getReasonExpenditureChartData(){
        TreeReasonViewer tree=new TreeReasonViewer(); tree.loadData("reason");
        Vector <ReasonType> allReason=loadReason();
        DefaultPieDataset ans=new DefaultPieDataset();
        for (int i=0;i<allReason.size();i++){
            ReasonType rt=allReason.get(i);
            double value=tree.solveExpenditure((ReasonTreeNodeType) rt);
            if (value>=0.01){
                ans.setValue(rt.getName(), value);
            }
        }
        return ans;
    }

    private Vector<ReasonType> loadReason() {
        DataViewer viewer=new DataViewer();
        viewer.loadData("reason");
        Vector <Type> allType=viewer.getAllItem();
        Vector <ReasonType> allReason=new Vector<>();
        for (int i=0;i<allType.size();i++) allReason.addElement((ReasonType) allType.get(i));
        return allReason;
    }
}
