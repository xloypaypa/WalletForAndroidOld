package com.example.xlo.walletforandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.afree.data.time.Second;
import org.afree.data.time.TimeSeries;

import java.util.Date;
import java.util.Vector;

import interfaceTool.StatusChart;
import type.DetailType;
import type.MoneyHistoryType;

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
        }
    }

    public static void draw(){
    }

    private static Vector<TimeSeries> getHistroyChartData(){
        Vector <TimeSeries> ans=new Vector<TimeSeries>();
        return ans;
    }

    private static TimeSeries getMessage(MoneyHistoryType mht){
        TimeSeries ans=new TimeSeries(mht.getName());
        for (int i=mht.getHistorySize()-1;i>=0;i--){
            DetailType now=mht.getHistory(i);
            try{
                ans.add(new Second(now.getTime()), Double.valueOf(now.getExtraMessage("history value")));
            }catch(Exception e){}
        }
        return ans;
    }
}
