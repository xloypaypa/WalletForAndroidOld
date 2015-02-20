package type;

import java.text.DecimalFormat;
import java.util.*;

import org.afree.data.time.Second;
import org.afree.data.time.TimeSeries;

public class MoneyHistoryType extends Type {
	String name; double value;
	Vector <DetailType> history; 
	public MoneyHistoryType(String name){
		this.name=new String(name);
		this.value=0;
		this.history=new Vector<DetailType>();
	}
	
	public MoneyHistoryType(String name, double value){
		this.name=new String(name);
		this.value=value;
		this.history=new Vector<DetailType>();
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getHistorySize(){
		return this.history.size();
	}
	
	public DetailType getHistory(int index){
		return this.history.get(index);
	}
	
	public void addHistory(DetailType detail){
		changeAndSetValue(detail);
		this.history.addElement(detail);
	}
	
	public TimeSeries getMessage(){
		TimeSeries ans=new TimeSeries(this.name);
		for (int i=0;i<history.size();i++){
			DetailType now=history.get(i);
			ans.add(new Second(now.getTime().getTime()), Double.valueOf(now.getExtraMessage("history value")));
		}
		return ans;
	}
	
	public double getValueBeforTime(Calendar time){
		double ans=0;
		for (int i=0;i<this.history.size();i++){
			DetailType now=this.history.get(i);
			if (now.getTime().after(time)) break;
			ans=Double.valueOf(now.getExtraMessage("history value"));
		}
		return ans;
	}
	
	public Calendar getFisrtUse(){
		return this.history.get(0).getTime();
	}
	
	public Calendar getLastUse(){
		return this.history.get(this.history.size()-1).getTime();
	}

	private void changeAndSetValue(DetailType detail) {
		changeValueAndName(detail);
		DecimalFormat df = new DecimalFormat("0.00");
		detail.addExtra("history value", df.format(this.value));
	}

	private void changeValueAndName(DetailType detail) {
		if (detail.getEvent().equals("add money type")){
			this.value=detail.getValue();
		}else if (detail.getEvent().equals("remove type")){
			this.value=0;
		}else if (detail.getEvent().equals("rename type")){
			this.name=new String(detail.getType());
		}else if (detail.getEvent().equals("income")){
			this.value+=detail.getValue();
		}else if (detail.getEvent().equals("expenditure")){
			this.value-=detail.getValue();
		}else if (detail.getEvent().equals("transfer in")){
			this.value+=detail.getValue();
		}else if (detail.getEvent().equals("transfer out")){
			this.value-=detail.getValue();
		}else if (detail.getEvent().equals("add debt")){
			this.value+=detail.getValue();
		}else if (detail.getEvent().equals("repay debt")){
			this.value-=detail.getValue();
		}
	}
}
