package com.example.xlo.walletforandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.afree.data.general.DefaultPieDataset;

import interfaceTool.ReasonChart;

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
        //TODO get income chart
        return null;
    }

    private DefaultPieDataset getReasonExpenditureChartData(){
        //TODO get expenditure chart
        return null;
    }
}
