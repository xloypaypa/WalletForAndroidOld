package logic.wallet;

import java.util.*;

import database.MoneyDB;
import type.*;

public class Money extends Wallet {
	public Vector <String> getAllTypeName(){
		Vector <String> ans=new Vector<String>();
		for (int i=0;i<allMoney.size();i++){
			ans.add(allMoney.get(i).getType());
		}
		return ans;
	}
	public void addType(String typeName){
		MoneyType money = createType(typeName);
		
		DetailType detail = addTypeDetail(typeName);
		super.setDetailTime(detail);
		
		super.addDetail(detail);
		allMoney.addElement(money);
		new MoneyDB(username, passWord).addNewType(typeName);
	}
	
	public void addType(String typeName,Calendar time){
		MoneyType money = createType(typeName);
		
		DetailType detail = addTypeDetail(typeName);
		super.setDetailTime(time,detail);
		
		super.addDetail(detail);
		allMoney.addElement(money);
		new MoneyDB(username, passWord).addNewType(typeName);
	}
	public void renameType(String pastName,String newName){
		int index=super.findMoneyIndex(pastName);
		MoneyType money=allMoney.get(index);
		money.setType(newName);
		
		DetailType detail = renameTypeDetail(newName,pastName);
		
		super.setDetailTime(detail);
		
		super.addDetail(detail);
		new MoneyDB(username, passWord).changeTypeName(pastName, newName);
	}
	public void renameType(String pastName,String newName,Calendar time){
		int index=super.findMoneyIndex(pastName);
		MoneyType money=allMoney.get(index);
		money.setType(newName);
		
		DetailType detail = renameTypeDetail(newName,pastName);
		
		super.setDetailTime(time,detail);
		
		super.addDetail(detail);
		new MoneyDB(username, passWord).changeTypeName(pastName, newName);
	}
	public void removeType(String typeName){
		DetailType detail = removeTypeDetail(typeName,allMoney.get(super.findMoneyIndex(typeName)).getValue());
		
		super.setDetailTime(detail);
		
		super.addDetail(detail);
		allMoney.remove(super.findMoneyIndex(typeName));
		new MoneyDB(username, passWord).removeType(typeName);
	}
	public void removeType(String typeName,Calendar time){
		DetailType detail = removeTypeDetail(typeName,allMoney.get(super.findMoneyIndex(typeName)).getValue());
		
		super.setDetailTime(time,detail);
		
		super.addDetail(detail);
		allMoney.remove(super.findMoneyIndex(typeName));
		new MoneyDB(username, passWord).removeType(typeName);
	}
	
	public void backup(DetailType last){
		if (last.getEvent().equals("rename type")){
			int index=super.findMoneyIndex(last.getType());
			MoneyType money=allMoney.get(index);
			money.setType(last.getExtraMessage("past name"));
			new MoneyDB(username, passWord).changeTypeName(last.getType(), last.getExtraMessage("past name"));
		}else if (last.getEvent().equals("remove type")){
			MoneyType money=new MoneyType();
			money.setType(last.getType());
			money.setValue(last.getValue());
			allMoney.add(money);
			new MoneyDB(username, passWord).addNewType(money);
		}else if (last.getEvent().equals("add money type")){
			allMoney.remove(super.findMoneyIndex(last.getType()));
			new MoneyDB(username, passWord).removeType(last.getType());
		}
	}
	
	private DetailType removeTypeDetail(String typeName,double val) {
		DetailType detail=new DetailType();
		detail.setEvent("remove type");
		detail.setReason("");
		detail.setType(typeName);
		detail.setValue(val);
		return detail;
	}
	private DetailType renameTypeDetail(String newName, String pastName) {
		DetailType detail=new DetailType();
		detail.setEvent("rename type");
		detail.setReason("");
		detail.setType(newName);
		detail.setValue(0);
		detail.addExtra("past name", pastName);
		return detail;
	}
	private DetailType addTypeDetail(String typeName) {
		DetailType detail=new DetailType();
		detail.setEvent("add money type");
		detail.setReason("");
		detail.setType(typeName);
		detail.setValue(0);
		return detail;
	}
	private MoneyType createType(String typeName) {
		MoneyType money=new MoneyType();
		money.setType(typeName);
		money.setValue(0);
		return money;
	}
}
