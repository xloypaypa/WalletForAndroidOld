package logic.history;

import java.util.Date;
import java.util.Vector;

import database.DetailDB;
import database.HHD;
import database.ReasonDB;
import type.DetailType;
import type.ReasonType;

public class ReasonHistory extends History {
	public void addIncome(String reason, double value){
		int pos=super.findReasonIndex(reason);
		if (pos==-1) return ;
		
		ReasonType rt=allReason.get(pos);
		rt.setIncome(rt.getIncome()+value);
		
		new ReasonDB(username, passWord).updateReason(reason, rt);
	}
	
	public Vector <String> getAllReasonName(){
		Vector <String> ans=new Vector<String>();
		for (int i=0;i<allReason.size();i++){
			ans.add(allReason.get(i).getName());
		}
		return ans;
	}
	
	public void addExpenditure(String reason, double value){
		int pos=super.findReasonIndex(reason);
		if (pos==-1) return ;
		
		ReasonType rt=allReason.get(pos);
		rt.setExpenditure(rt.getExpenditure()+value);
		
		new ReasonDB(username, passWord).updateReason(reason, rt);
	}
	
	public void addReason(String name){
		addReason(name, new Date());
	}
	
	public void addReason(String name,Date time){
		ReasonType rt=new  ReasonType(name);
		
		DetailType dt=new DetailType();
		dt.setEvent("add reason");
		dt.setReason(name);
		dt.setTime(new Date());
		dt.setType("");
		dt.setValue(0);
		
		setDetailTime(time, dt);
		
		allDetail.addElement(dt);
		allReason.addElement(rt);
		new ReasonDB(username, passWord).addReason(rt);
		new DetailDB(username, passWord).addDetail(dt);
	}
	
	public void removeReason(String name){
		removeReason(name, new Date());
	}
	
	public void removeReason(String name,Date time){
		if (!super.reasonExist(name)) return ;
		
		int pos=super.findReasonIndex(name);
		
		DetailType dt=new DetailType();
		dt.setEvent("delete reason");
		dt.setReason(name);
		dt.setTime(new Date());
		dt.setType("");
		dt.setValue(0);
		dt.addExtra("income", allReason.get(pos).getIncome()+"");
		dt.addExtra("expenditure", allReason.get(pos).getExpenditure()+"");
		
		setDetailTime(time, dt);
		
		allDetail.addElement(dt);
		allReason.remove(pos);
		
		if (allReason.size()!=0){
			new ReasonDB(username, passWord).deleteReason(name);
		}else{
			HHD.cleanFile(new ReasonDB(username, passWord).getAimPath());
		}
		new DetailDB(username, passWord).addDetail(dt);
	}
	
	public void changeReasonName(String pastName, String newName){
		changeReasonName(pastName, newName, new Date());
	}
	
	public void changeReasonName(String pastName, String newName,Date time){
		if (!super.reasonExist(pastName)) return ;
		
		int pos=super.findReasonIndex(pastName);
		ReasonType rt=allReason.get(pos);
		rt.setName(newName);
		
		DetailType dt=new DetailType();
		dt.setEvent("rename reason");
		dt.setReason(newName);
		dt.setTime(new Date());
		dt.setType("");
		dt.setValue(0);
		dt.addExtra("past name", pastName);
		
		setDetailTime(time, dt);
		
		allDetail.addElement(dt);
		new ReasonDB(username, passWord).updateReason(pastName, rt);
		new DetailDB(username, passWord).addDetail(dt);
	}
	
	public void backup(DetailType last){
		if (last.getEvent().equals("add reason")){
			allReason.remove(super.findReasonIndex(last.getReason()));
			if (allReason.size()!=0){
				new ReasonDB(username, passWord).deleteReason(last.getReason());				
			}else{
				HHD.cleanFile(new ReasonDB(username, passWord).getAimPath());
			}
			
		}else if (last.getEvent().equals("delete reason")){
			String name=last.getReason();
			double in=Double.valueOf(last.getExtraMessage("income"));
			double out=Double.valueOf(last.getExtraMessage("expenditure"));
			ReasonType rt=new ReasonType(name, in, out);
			allReason.add(rt);
			new ReasonDB(username, passWord).addReason(rt);
		}else if (last.getEvent().equals("rename reason")){
			String now,past;
			now=last.getReason();
			past=last.getExtraMessage("past name");
			allReason.get(super.findReasonIndex(now)).setName(past);
			new ReasonDB(username, passWord).updateReason(now, allReason.get(super.findReasonIndex(past)));
		}else if (last.getEvent().equals("income")){
			int pos=super.findReasonIndex(last.getReason()); if (pos==-1) return ;
			allReason.get(pos).setIncome(allReason.get(pos).getIncome()-last.getValue());
			new ReasonDB(username, passWord).updateReason(last.getReason(), allReason.get(pos));
		}else if (last.getEvent().equals("expenditure")){
			int pos=super.findReasonIndex(last.getReason()); if (pos==-1) return ;
			allReason.get(pos).setExpenditure(allReason.get(pos).getExpenditure()-last.getValue());
			new ReasonDB(username, passWord).updateReason(last.getReason(), allReason.get(pos));
		}
	}
	
	public void doDetail(DetailType now){
		if (now.getEvent().equals("add reason")){
			addReason(now.getReason(),now.getTime());
		}else if (now.getEvent().equals("delete reason")){
			removeReason(now.getReason(),now.getTime());
		}else if (now.getEvent().equals("rename reason")){
			this.checkExtra(now, "past name");
			changeReasonName(now.getExtraMessage("past name"), now.getReason(),now.getTime());
		}else if (now.getEvent().equals("income")){
			addIncome(now.getReason(), now.getValue());
		}else if (now.getEvent().equals("expenditure")){
			addExpenditure(now.getReason(), now.getValue());
		}
	}
	
}
