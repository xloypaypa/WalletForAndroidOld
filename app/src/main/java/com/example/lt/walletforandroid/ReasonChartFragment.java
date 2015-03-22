package com.example.lt.walletforandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import interfaceTool.ReasonChart;
import logic.User;
import logic.history.ReasonHistory;
import logic.history.TreeReasonHistory;

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
        ReasonHistory rh;
        if (User.userReason.equals("tree")){
            rh=new TreeReasonHistory();
        }else{
            rh=new ReasonHistory();
        }

        ReasonChart exp=new ReasonChart();
        exp.drawChart(rh.getReasonExpenditureChartData(),"expenditure");
        ImageView iv= (ImageView) this.getActivity().findViewById(R.id.reason_chart_expenditure);
        iv.setImageBitmap(exp.getBitmap());

        ReasonChart ic=new ReasonChart();
        ic.drawChart(rh.getReasonIncomeChartData(), "income");
        ImageView ev= (ImageView) this.getActivity().findViewById(R.id.reason_chart_income);
        ev.setImageBitmap(ic.getBitmap());
    }
}
