package com.example.lt.walletforandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.afree.data.general.DefaultPieDataset;

import java.util.Vector;

import interfaceTool.ReasonChart;
import logic.User;
import logic.history.ReasonHistory;
import logic.history.TreeReasonHistory;
import type.ReasonTreeNodeType;
import type.ReasonType;

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
        if (User.userReason.equals("tree")){
            Vector <ReasonType> allReason=new ReasonHistory().getReasonType();
            DefaultPieDataset ans=new DefaultPieDataset();
            for (int i=0;i<allReason.size();i++){
                ReasonType rt=allReason.get(i);
                double value=new TreeReasonHistory().solveIncome((ReasonTreeNodeType) rt);
                if (value>=0.01){
                    ans.setValue(rt.getName(), value);
                }
            }
            return ans;
        }

        Vector <ReasonType> allReason=new ReasonHistory().getReasonType();
        DefaultPieDataset ans=new DefaultPieDataset();
        for (int i=0;i<allReason.size();i++){
            ReasonType rt=allReason.get(i);
            ans.setValue(rt.getName(), rt.getIncome());
        }
        return ans;
    }

    private DefaultPieDataset getReasonExpenditureChartData(){
        if (User.userReason.equals("tree")){
            Vector <ReasonType> allReason=new ReasonHistory().getReasonType();
            DefaultPieDataset ans=new DefaultPieDataset();
            for (int i=0;i<allReason.size();i++){
                ReasonType rt=allReason.get(i);
                double value=new TreeReasonHistory().solveExpenditure((ReasonTreeNodeType) rt);
                if (value>=0.01){
                    ans.setValue(rt.getName(), value);
                }
            }
            return ans;
        }

        Vector<ReasonType> allReason=new ReasonHistory().getReasonType();
        DefaultPieDataset ans=new DefaultPieDataset();
        for (int i=0;i<allReason.size();i++){
            ReasonType rt=allReason.get(i);
            ans.setValue(rt.getName(), rt.getExpenditure());
        }
        return ans;
    }
}
