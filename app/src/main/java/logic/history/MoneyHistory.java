package logic.history;

import java.util.Date;
import java.util.Vector;

import logic.wallet.Detail;

import org.afree.data.time.Second;
import org.afree.data.time.TimeSeries;

import type.MoneyHistoryType;

public class MoneyHistory extends History {
	public Vector <TimeSeries> getHistroyChartData(){
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
			TimeSeries now=moneyhistory.get(i).getMessage();
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
	
	public void update(){
		moneyhistory=new Detail().getHistoricalType();
	}
}
