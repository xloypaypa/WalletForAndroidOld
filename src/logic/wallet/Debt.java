package logic.wallet;

import java.text.*;
import java.util.*;

import type.*;
import database.*;

public class Debt extends Wallet {
	public DebtType getLastDebt(){
		DebtType ans=new DebtType();
		for (int i=0;i<allDebt.size();i++){
			if (ans.getStartingTime().after(allDebt.get(i).getStartingTime())){
				ans=allDebt.get(i);
			}
		}
		return ans;
	}
	public DebtType getLastDebtByDeadline(){
		DebtType ans=new DebtType();
		for (int i=0;i<allDebt.size();i++){
			if (ans.getDeadline().after(allDebt.get(i).getDeadline())){
				ans=allDebt.get(i);
			}
		}
		return ans;
	}
	public DebtType getDebtByID(int ID){
		return allDebt.get(super.findDebtIndex(ID));
	}
	public Vector <String> getAllCreditorName(){
		Vector <String> ans=new Vector<String>();
		for (int i=0;i<allDebt.size();i++){
			ans.add(allDebt.get(i).getCreditor());
		}
		return ans;
	}
	public void addDebt(String creditor,double value, Calendar deadline, String rateType, double rate, String moneyType){
		DebtType debt = addDebtWithoutStartingTime(creditor, value, deadline,rateType, rate);
		DetailType detail = addDebtDetail(value, moneyType, rate, rateType, debt);
		
		Calendar nowTime=Calendar.getInstance(); nowTime.setTime(new Date());
		
		debt.setStartingTime(nowTime);
		super.setDetailTime(nowTime, detail);
		
		new DebtDB(username, passWord).addDebt(debt);
		allDebt.addElement(debt);
		super.addDetail(detail);
		super.typeAddValue(moneyType, value);
	}
	public void addDebt(String creditor,double value, Calendar deadline, String rateType, double rate, String moneyType, Calendar time){
		DebtType debt = addDebtWithoutStartingTime(creditor, value, deadline,rateType, rate);
		DetailType detail = addDebtDetail(value, moneyType, rate, rateType, debt);
		
		debt.setStartingTime(time);
		super.setDetailTime(time, detail);
		
		new DebtDB(username, passWord).addDebt(debt);
		allDebt.addElement(debt);
		super.addDetail(detail);
		super.typeAddValue(moneyType, value);
	}
	
	public void repay(int debtID,double value,String typeName){
		DebtType debt=allDebt.get(super.findDebtIndex(debtID));
		DetailType detail = repayDebtDetail(value, typeName, debt);
		
		debt.repayment(value);
		checkDebtNeedExist(debtID, debt, detail);
		super.setDetailTime(debt.getLastUpdateTime(), detail);
		
		super.addDetail(detail);
		super.typeAddValue(typeName, -value);
	}
	public void repay(int debtID,double value,String typeName,Calendar time){
		DebtType debt=allDebt.get(super.findDebtIndex(debtID));
		DetailType detail = repayDebtDetail(value, typeName, debt);
		
		debt.repayment(value,time);
		checkDebtNeedExist(debtID, debt, detail);
		super.setDetailTime(time, detail);
		
		super.addDetail(detail);
		super.typeAddValue(typeName, -value);
	}
	public void delete(int debtID,String reason){
		DetailType detail = deleteDebtDetail(debtID, reason);
		
		super.setDetailTime(detail);
		
		super.addDetail(detail);
		new DebtDB(username, passWord).deleteDebt(debtID);
		allDebt.remove(super.findDebtIndex(debtID));
	}
	public void delete(int debtID,String reason,Calendar time){
		DetailType detail = deleteDebtDetail(debtID, reason);
		
		super.setDetailTime(time,detail);
		
		super.addDetail(detail);
		new DebtDB(username, passWord).deleteDebt(debtID);
		allDebt.remove(super.findDebtIndex(debtID));
	}
	
	public void updateAll(){
		for (int i=0;i<allDebt.size();i++){
			allDebt.get(i).updateValue();
		}
	}
	
	public void backup(DetailType last){
		if (last.getEvent().equals("add debt")){
			super.typeAddValue(last.getType(), -last.getValue());
			int id=Integer.valueOf(last.getExtraMessage("debt id"));
			new DebtDB(username, passWord).deleteDebt(id);
			allDebt.remove(super.findDebtIndex(id));
		}else if (last.getEvent().equals("repay debt")){
			super.typeAddValue(last.getType(), last.getValue());
			int debtID=Integer.valueOf(last.getExtraMessage("past id"));
			DebtType debt = solvePastDetail(last);
			if (super.debtExist(debtID)){
				allDebt.setElementAt(debt, super.findDebtIndex(debtID));
			}else{
				allDebt.add(debt);
				new DebtDB(username, passWord).addDebt(debt);
			}
		}else if (last.getEvent().equals("delete debt")){
			DebtType debt = solvePastDetail(last);
			allDebt.add(debt);
			new DebtDB(username, passWord).addDebt(debt);
		}
	}
	private DebtType solvePastDetail(DetailType last) {
		DebtType debt=new DebtType();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		debt.setID(Integer.valueOf(last.getExtraMessage("past id")));
		debt.setCreditor(last.getExtraMessage("past creditor"));
		Calendar deadline=Calendar.getInstance();
		Calendar lastUpdate=Calendar.getInstance();
		Calendar starting=Calendar.getInstance();
		try {
			deadline.setTime(sdf.parse(last.getExtraMessage("past deadline")));
			lastUpdate.setTime(sdf.parse(last.getExtraMessage("past update time")));
			starting.setTime(sdf.parse(last.getExtraMessage("past starting time")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		debt.setDeadline(deadline);
		debt.setLastUpdateTime(lastUpdate);
		debt.setRate(last.getExtraMessage("past rate type"), Double.valueOf(last.getExtraMessage("past rate")));
		debt.setStartingTime(starting);
		debt.setValue(Double.valueOf(last.getExtraMessage("past value")));
		return debt;
	}
	
	private DetailType repayDebtDetail(double value, String typeName,
			DebtType debt) {
		DetailType detail=new DetailType();
		
		detail.setEvent("repay debt");
		detail.setReason("");
		detail.setType(typeName);
		detail.setValue(value);
		detailSaveDebt(debt, detail);
		return detail;
	}
	private void detailSaveDebt(DebtType debt, DetailType detail) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		detail.addExtra("past id", debt.getID()+"");
		detail.addExtra("past creditor", debt.getCreditor());
		detail.addExtra("past value", debt.getValue()+"");
		detail.addExtra("past starting time", sdf.format(debt.getStartingTime().getTime()));
		detail.addExtra("past update time", sdf.format(debt.getLastUpdateTime().getTime()));
		detail.addExtra("past deadline", sdf.format(debt.getDeadline().getTime()));
		detail.addExtra("past rate type", debt.getRate().getType());
		detail.addExtra("past rate", debt.getRate().getRate()+"");
	}
	
	private int maxID(){
		int ans=0;
		for (int i=0;i<allDebt.size();i++){
			if (allDebt.get(i).getID()>ans){
				ans=allDebt.get(i).getID();
			}
		}
		return ans;
	}
	private DebtType addDebtWithoutStartingTime(String creditor, double value,
			Calendar deadline, String rateType, double rate) {
		DebtType debt=new DebtType();
		debt.setCreditor(creditor);
		debt.setDeadline(deadline);
		debt.setID(maxID()+1);
		debt.setRate(rateType, rate);
		debt.setValue(value);
		return debt;
	}
	private DetailType addDebtDetail(double value, String moneyType, double rate, String rateType,
			DebtType debt) {
		DetailType detail=new DetailType();
		detail.setEvent("add debt");
		detail.setReason("");
		detail.setType(moneyType);
		detail.setValue(value);
		detail.addExtra("debt id", debt.getID()+"");
		detail.addExtra("debt rate", rate+"");
		detail.addExtra("debt rate type", rateType);
		return detail;
	}
	private DetailType deleteDebtDetail(int debtID, String reason) {
		DebtType debt=allDebt.get(super.findDebtIndex(debtID));
		DetailType detail=new DetailType();
		detail.setEvent("delete debt");
		detail.setReason(reason);
		detail.setType("");
		detail.setValue(0);
		detailSaveDebt(debt, detail);
		return detail;
	}
	private void checkDebtNeedExist(int debtID, DebtType debt, DetailType detail) {
		if (debt.getValue()<0.001){
			new DebtDB(username, passWord).deleteDebt(debtID);
			allDebt.remove(super.findDebtIndex(debtID));
			detail.addExtra("last operator", "delete");
		}else{
			new DebtDB(username, passWord).changeDebt(debtID, debt);
			detail.addExtra("last operator", "change");
		}
	}
}
