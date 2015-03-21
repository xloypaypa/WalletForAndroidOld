package logic.wallet;

import java.util.*;

import type.*;

public class Cost extends Wallet {
	public void addChange(String name,double val,String reason){
		addChange(name, val, reason, new Date());
	}
	public void addChange(String name,double val,String reason,Date time){
		if (val==0) return ;
		
		DetailType dt = changeValueDetail(name, val, reason);
		this.setDetailTime(time, dt);
		
		super.addDetail(dt);
		typeAddValue(name, -val);
	}
	
	public void transfer(String from,String to,double val){
		transfer(from, to, val, new Date());
	}
	public void transfer(String from,String to,double val,Date time){
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
	
	public void doDetail(DetailType now){
		if (now.getEvent().equals("income")){
			addChange(now.getType(), -now.getValue(), now.getReason(), now.getTime());
		}else if (now.getEvent().equals("expenditure")){
			addChange(now.getType(), now.getValue(), now.getReason(), now.getTime());
		}else if (now.getEvent().equals("transfer")){
			this.checkExtra(now, "from type");
			transfer(now.getExtraMessage("from type"), now.getType(), now.getValue(), now.getTime());
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
