package type;

import java.text.DecimalFormat;
import java.util.*;

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
	
	public int getHistroySize(){
		return this.history.size();
	}
	
	public void addHistory(DetailType detail){
		changeAndSetValue(detail);
		this.history.addElement(detail);
	}
	
	public double getValueBeforTime(Date time){
		double ans=0;
		for (int i=0;i<this.history.size();i++){
			DetailType now=this.history.get(i);
			if (now.getTime().after(time)) break;
			ans=Double.valueOf(now.getExtraMessage("history value"));
		}
		return ans;
	}
	
	public Date getFisrtUse(){
		Date ans=new Date();
		ans.setTime(this.history.get(0).getTime().getTime());
		return ans;
	}
	
	public Date getLastUse(){
		Date ans=new Date();
		ans.setTime(this.history.lastElement().getTime().getTime());
		return ans;
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
