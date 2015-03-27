package com.example.lt.walletforandroid;

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
import logic.history.MoneyHistory;
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
            MoneyHistory mh=new MoneyHistory();
            StatusChart sc=new StatusChart();
            sc.drawChart("money change chart","time","value",getHistroyChartData());
            ImageView iv=(ImageView) this.getActivity().findViewById(R.id.status_chart);
            iv.setImageBitmap(sc.getBitmap());
        }
    }

    public static void draw(){
        MoneyHistory mh=new MoneyHistory();
        StatusChart sc=new StatusChart();
        sc.drawChart("money change chart","time","value",getHistroyChartData());
        iv.setImageBitmap(sc.getBitmap());
    }

    private static Vector<TimeSeries> getHistroyChartData(){
        Vector <MoneyHistoryType> moneyhistory=new MoneyHistory().getHistoricalType();
        if (moneyhistory.size()==0) return new Vector<TimeSeries>();

        Date start=new Date();
        Date end=new Date();
        start.setTime(moneyhistory.get(0).getFisrtUse().getTime());
        end.setTime(moneyhistory.get(0).getLastUse().getTime());

        for (int i=0;i<moneyhistory.size();i++){
            MoneyHistoryType now=moneyhistory.get(i);
            if (start.after(now.getFisrtUse())){
                start.setTime(now.getFisrtUse().getTime());
            }
            if (end.before(now.getLastUse())){
                end.setTime(now.getLastUse().getTime());
            }
        }

        Vector <TimeSeries> ans=new Vector<TimeSeries>();
        for (int i=0;i<moneyhistory.size();i++){
            TimeSeries now=getMessage(moneyhistory.get(i));
            if (moneyhistory.get(i).getFisrtUse().after(start)){
                now.add(new Second(start), moneyhistory.get(i).getValueBeforTime(start));
            }
            if (moneyhistory.get(i).getLastUse().before(end)){
                now.add(new Second(end), moneyhistory.get(i).getValueBeforTime(end));
            }
            ans.add(now);
        }
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
