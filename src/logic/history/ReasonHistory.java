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
		ReasonType rt=new  ReasonType(name);
		
		DetailType dt=new DetailType();
		dt.setEvent("add reason");
		dt.setReason(name);
		dt.setTime(new Date());
		dt.setType("");
		dt.setValue(0);
		
		allDetail.addElement(dt);
		allReason.addElement(rt);
		new ReasonDB(username, passWord).addReason(rt);
		new DetailDB(username, passWord).addDetail(dt);
	}
	
	public void removeReason(String name){
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
	
}
