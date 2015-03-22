package com.example.lt.walletforandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import interfaceTool.ReasonChart;
import interfaceTool.StatusChart;
import logic.User;
import logic.history.MoneyHistory;
import logic.history.ReasonHistory;
import logic.history.TreeReasonHistory;

/**
 * Created by LT on 2015/3/23.
 *
 */
public class StatusChartFragment extends Fragment {
    public static boolean canDraw=false;
    static ImageView iv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.status_chart_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        iv=(ImageView) this.getActivity().findViewById(R.id.status_chart);

        if (canDraw){
            MoneyHistory mh=new MoneyHistory();
            StatusChart sc=new StatusChart();
            sc.drawChart("money change chart","time","value",mh.getHistroyChartData());
            ImageView iv=(ImageView) this.getActivity().findViewById(R.id.status_chart);
            iv.setImageBitmap(sc.getBitmap());
        }
    }

    public static void draw(){
        MoneyHistory mh=new MoneyHistory();
        StatusChart sc=new StatusChart();
        sc.drawChart("money change chart","time","value",mh.getHistroyChartData());
        iv.setImageBitmap(sc.getBitmap());
    }
}
