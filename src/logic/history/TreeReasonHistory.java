package logic.history;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import database.DetailDB;
import database.HHD;
import database.ReasonDB;
import type.DetailType;
import type.ReasonTreeNodeType;

public class TreeReasonHistory extends ReasonHistory {
	
	public static void buildTree(){
		for (int i=0;i<allReason.size();i++){
			String father=((ReasonTreeNodeType) allReason.get(i)).getFather();
			int fPos=findReasonIndex(father);
			((ReasonTreeNodeType) allReason.get(i)).setFatherPos(fPos);
			if (fPos!=-1){
				((ReasonTreeNodeType) allReason.get(fPos)).addKid(i);
			}
		}
	}
	
	public boolean checkExpenditure(String reason, double value){
		int now=findReasonIndex(reason);
		while (now!=-1){
			((ReasonTreeNodeType) allReason.get(now)).update();
			if (allReason.get(now).getExpenditure()+value>((ReasonTreeNodeType) allReason.get(now)).getMax()){
				return false;
			}
			now=((ReasonTreeNodeType) allReason.get(now)).getFatherPos();
		}
		return true;
	}
	
	public void addExpenditure(String reason, double value){
		int now;
		doExpenditure(reason, value);
		
		now=findReasonIndex(reason);
		while (now!=-1){
			ReasonTreeNodeType node=(ReasonTreeNodeType) allReason.get(now);
			new ReasonDB(username, passWord).updateReason(node.getName(), node);
			now=node.getFatherPos();
		}
	}

	public void addIncome(String reason, double value){
		int now;
		doIncome(reason, value);
		
		now=findReasonIndex(reason);
		while (now!=-1){
			ReasonTreeNodeType node=(ReasonTreeNodeType) allReason.get(now);
			new ReasonDB(username, passWord).updateReason(node.getName(), node);
			now=node.getFatherPos();
		}
	}
	
	public void setMin(String reason, double value){
		int pos=findReasonIndex(reason);
		((ReasonTreeNodeType) allReason.get(pos)).setMin(value);
	}
	
	public void setMax(String reason, double value){
		int pos=findReasonIndex(reason);
		((ReasonTreeNodeType) allReason.get(pos)).setMax(value);
	}
	
	public Vector <ReasonTreeNodeType> getRoots(){
		Vector <ReasonTreeNodeType> ans=new Vector<ReasonTreeNodeType>();
		for (int i=0;i<allReason.size();i++){
			if(((ReasonTreeNodeType) allReason.get(i)).getFather().equals("root")){
				ans.add((ReasonTreeNodeType) allReason.get(i));
			}
		}
		return ans;
	}
	
	public Vector <ReasonTreeNodeType> getKid(int pos){
		Vector <ReasonTreeNodeType> ans=new Vector<ReasonTreeNodeType>();
		ReasonTreeNodeType now=(ReasonTreeNodeType) allReason.get(pos);
		Vector <Integer> kids=now.getKid();
		for (int i=0;i<kids.size();i++){
			ans.addElement((ReasonTreeNodeType) allReason.get(kids.get(i)));
		}
		return ans;
	}
	public Vector <String> getKidName(int pos){
		Vector <String> ans=new Vector<String>();
		Vector <ReasonTreeNodeType> ret=getKid(pos);
		for (int i=0;i<ret.size();i++){
			ans.add(ret.get(i).getName());
		}
		return ans;
	}
	public Vector <ReasonTreeNodeType> getKid(String reason){
		return getKid(findReasonIndex(reason));
	}
	public Vector <String> getKidName(String reason){
		return getKidName(findReasonIndex(reason));
	}
	
	public void addReason(String name){
		addReason("root",name,0,0,0);
	}
	
	public void changeReason(String reason, String father, String name, double min, double max, int rank){
		if (!super.reasonExist(reason)) return ;
		
		int pos=super.findReasonIndex(reason);
		ReasonTreeNodeType rt=(ReasonTreeNodeType) allReason.get(pos);
		
		DetailType dt = getChangeDetail(rt, father, name);
		changeFather(reason, name,dt);
		
		rt.setName(name);
		rt.setFatherName(father);
		rt.setMin(min);
		rt.setMax(max);
		rt.setRank(rank);
		
		
		allDetail.addElement(dt);
		new ReasonDB(username, passWord).updateReason(reason, rt);
		new DetailDB(username, passWord).addDetail(dt);
		
		buildTree();
	}
	
	@Override
	public void removeReason(String reason){
		int pos=super.findReasonIndex(reason);
		ReasonTreeNodeType now=(ReasonTreeNodeType) allReason.get(pos);
		
		DetailType dt = getRemoveDetail(reason, now);
		changeFather(reason, "root",dt);
		
		allReason.remove(pos);
		allDetail.addElement(dt);
		new ReasonDB(username, passWord).deleteReason(reason);
		new DetailDB(username, passWord).addDetail(dt);
	}
	
	public void addReason(String father, String name,double min, double max, int rank){
		ReasonTreeNodeType rt=new ReasonTreeNodeType();
		rt.setName(name);
		rt.setMin(min);
		rt.setMax(max);
		rt.setRank(rank);
		rt.setFatherName(father);
		rt.update();
		
		DetailType dt = getAddDetail(father, name, min, max);
		
		allDetail.addElement(dt);
		allReason.addElement(rt);
		new ReasonDB(username, passWord).addReason(rt);
		new DetailDB(username, passWord).addDetail(dt);
	}
	
	@Override
	public void backup(DetailType last){
		if (last.getEvent().equals("add reason tree node")){
			allReason.remove(super.findReasonIndex(last.getReason()));
			if (allReason.size()!=0){
				new ReasonDB(username, passWord).deleteReason(last.getReason());				
			}else{
				HHD.cleanFile(new ReasonDB(username, passWord).getAimPath());
			}
		}else if (last.getEvent().equals("remove reason tree node")){
			ReasonTreeNodeType node=solveDetail(last);
			allReason.add(node);
			new ReasonDB(username, passWord).addReason(node);
			solve(last,node.getName());
		}else if (last.getEvent().equals("change reason tree node")){
			ReasonTreeNodeType node=solveDetail(last);
			int pos=findReasonIndex(last.getReason());
			allReason.setElementAt(node, pos);
			new ReasonDB(username, passWord).updateReason(last.getReason(), node);
			solve(last,node.getName());
		}else if (last.getEvent().equals("income")){
			addExpenditure(last.getReason(), last.getValue());
		}else if (last.getEvent().equals("expenditure")){
			addExpenditure(last.getReason(), last.getValue());
		}
	}
	
	private void solve(DetailType last,String father){
		for (int i=0;i<allReason.size();i++){
			if (last.getExtraMessage(allReason.get(i).getName()).equals("yes")){
				((ReasonTreeNodeType) allReason.get(i)).setFatherName(father);
				new ReasonDB(username, passWord).updateReason(allReason.get(i).getName(), allReason.get(i));
			}
		}
	}

	private DetailType getAddDetail(String father, String name, double min,
			double max) {
		DetailType dt=new DetailType();
		dt.setEvent("add reason tree node");
		dt.setReason(name);
		dt.setTime(new Date());
		dt.setType("");
		dt.setValue(0);
		dt.addExtra("father", father);
		dt.addExtra("min", min+"");
		dt.addExtra("max", max+"");
		return dt;
	}
	
	private DetailType getChangeDetail(ReasonTreeNodeType now, String father,
			String name) {
		DetailType dt=new DetailType();
		dt.setEvent("change reason tree node");
		dt.setReason(name);
		dt.setTime(new Date());
		dt.setType("");
		dt.setValue(0);
		
		saveTreeNode(now, dt);
		return dt;
	}
	
	private void changeFather(String aimFather, String newFatherName, DetailType dt) {
		for (int i=0;i<allReason.size();i++){
			ReasonTreeNodeType now=(ReasonTreeNodeType) allReason.get(i);
			if (now.getFather().equals(aimFather)){
				dt.addExtra(now.getName(), "yes");
				now.setFatherName(newFatherName);
			}
		}
	}
	
	private DetailType getRemoveDetail(String reason, ReasonTreeNodeType now) {
		DetailType dt=new DetailType();
		
		dt.setEvent("remove reason tree node");
		dt.setReason(reason);
		dt.setTime(new Date());
		dt.setType("");
		dt.setValue(0);
		
		saveTreeNode(now, dt);
		
		return dt;
	}

	private void saveTreeNode(ReasonTreeNodeType now, DetailType dt) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
		dt.addExtra("past father", now.getFather());
		dt.addExtra("past name", now.getName());
		dt.addExtra("past min", now.getMin()+"");
		dt.addExtra("past max", now.getMax()+"");
		dt.addExtra("past income", now.getIncome()+"");
		dt.addExtra("past expenditure", now.getExpenditure()+"");
		dt.addExtra("past update", sdf.format(now.getUpdateTime().getTime()));
		dt.addExtra("past rank", now.getRank()+"");
	}
	
	private ReasonTreeNodeType solveDetail(DetailType last) {
		String name=last.getExtraMessage("past name");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
		double in=Double.valueOf(last.getExtraMessage("past income"));
		double out=Double.valueOf(last.getExtraMessage("past expenditure"));
		double min=Double.valueOf(last.getExtraMessage("past min"));
		double max=Double.valueOf(last.getExtraMessage("past max"));
		int rank=Integer.valueOf(last.getExtraMessage("past rank"));
		Calendar update=Calendar.getInstance();
		try {
			update.setTime(sdf.parse(last.getExtraMessage("past update")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String father=last.getExtraMessage("past father");
		ReasonTreeNodeType rt=new ReasonTreeNodeType();
		rt.setFatherName(father);
		rt.setExpenditure(out);
		rt.setIncome(in);
		rt.setMax(max);
		rt.setMin(min);
		rt.setName(name);
		rt.setUpdateTime(update);
		rt.setRank(rank);
		return rt;
	}
	
	private void doExpenditure(String reason, double value) {
		int now=findReasonIndex(reason);
		while (now!=-1){
			ReasonTreeNodeType node=(ReasonTreeNodeType) allReason.get(now);
			node.setExpenditure(node.getExpenditure()+value);
			now=node.getFatherPos();
		}
	}

	private void doIncome(String reason, double value) {
		int now=findReasonIndex(reason);
		while (now!=-1){
			ReasonTreeNodeType node=(ReasonTreeNodeType) allReason.get(now);
			node.setIncome(node.getIncome()+value);
			now=node.getFatherPos();
		}
	}
}
