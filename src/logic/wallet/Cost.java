package logic.wallet;

import java.util.*;

import type.*;

public class Cost extends Wallet {
	public void addChange(String name,double val,String reason){
		if (val==0) return ;
		
		DetailType dt = changeValueDetail(name, val, reason);
		super.setDetailTime(dt);
		
		super.addDetail(dt);
		typeAddValue(name, -val);
	}
	public void addChange(String name,double val,String reason,Calendar time){
		if (val==0) return ;
		
		DetailType dt = changeValueDetail(name, val, reason);
		this.setDetailTime(time, dt);
		
		super.addDetail(dt);
		typeAddValue(name, -val);
	}
	
	public void transfer(String from,String to,double val){
		if (val==0) return ;
		
		DetailType dt = transferDetail(from, to, val);
		setDetailTime(dt);
		
		super.addDetail(dt);
		typeAddValue(from, -val);
		typeAddValue(to, val);
	}
	public void transfer(String from,String to,double val,Calendar time){
		if (val==0) return ;
		
		DetailType dt = transferDetail(from, to, val);
		setDetailTime(time,dt);
		
		super.addDetail(dt);
		typeAddValue(from, -val);
		typeAddValue(to, val);
	}
	
	public void backup(DetailType last){
		if (last.getEvent().equals("income")){
			super.typeAddValue(last.getType(), -last.getValue());
		}else if (last.getEvent().equals("expenditure")){
			super.typeAddValue(last.getType(), last.getValue());
		}else if (last.getEvent().equals("transfer")){
			super.typeAddValue(last.getType(), -last.getValue());
			super.typeAddValue(last.getExtraMessage("from type"), last.getValue());
		}
	}
	
	private DetailType changeValueDetail(String name, double val, String reason) {
		DetailType dt=new DetailType();
		if (val>0){
			dt.setEvent("expenditure");
		}else{
			dt.setEvent("income");
		}
		dt.setReason(reason);
		dt.setType(name);
		dt.setValue(Math.abs(val));
		return dt;
	}
	private DetailType transferDetail(String from, String to, double val) {
		DetailType dt=new DetailType();
		dt.setEvent("transfer");
		dt.setReason("");
		dt.setType(to);
		dt.setValue(val);
		dt.addExtra("from type", from);
		return dt;
	}
}
