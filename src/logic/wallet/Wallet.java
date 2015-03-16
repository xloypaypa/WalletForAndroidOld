package logic.wallet;

import java.util.*;

import logic.User;
import logic.history.MoneyHistory;
import database.*;
import type.*;

public class Wallet extends User {
	static Vector <DebtType> allDebt=new Vector<DebtType>();
	static Vector <MoneyType> allMoney=new Vector<MoneyType>();
	
	public void loadWallet(){
		allDebt=new DebtDB(username, passWord).loadDebt();
		allDetail=new DetailDB(username, passWord).loadDetail();
		allMoney=new MoneyDB(username, passWord).loadUser();
	}
	public void releaseWallet(){
		allDebt=new Vector<DebtType>();
		allDetail=new Vector<DetailType>();
		allMoney=new Vector<MoneyType>();
	}
	
	public Vector <DebtType> getDebt(){
		return allDebt;
	}
	public Vector <DetailType> getDetail(){
		return allDetail;
	}
	public Vector <MoneyType> getMoney(){
		return allMoney;
	}
	
	public double getTotalMoney(){
		double ans=0;
		for (int i=0;i<allMoney.size();i++){
			ans+=allMoney.get(i).getValue();
		}
		return ans;
	}
	public double getTotalDebt(){
		double ans=0;
		for (int i=0;i<allDebt.size();i++){
			ans+=allDebt.get(i).getValue();
		}
		return ans;
	}
	
	public int findMoneyIndex(String type){
		for (int i=0;i<allMoney.size();i++){
			if (allMoney.get(i).getType().equals(type)) return i;
		}
		return -1;
	}
	public int findDebtIndex(int debtID){
		for (int i=0;i<allDebt.size();i++){
			if (allDebt.get(i).getID()==debtID) return i;
		}
		return -1;
	}
	
	public boolean debtExist(int debtID){
		for (int i=0;i<allDebt.size();i++){
			if (allDebt.get(i).getID()==debtID) return true;
		}
		return false;
	}
	public boolean moneyTypeExist(String type){
		for (int i=0;i<allMoney.size();i++){
			if (allMoney.get(i).getType().equals(type)) return true;
		}
		return false;
	}
	
	public double getMoney(String name){
		for (int i=0;i<allMoney.size();i++){
			if (allMoney.get(i).getType().equals(name)) return allMoney.get(i).getValue();
		}
		return 0;
	}
	
	public void addDetail(DetailType dt){
		allDetail.addElement(dt);
		new DetailDB(username, passWord).addDetail(dt);
		new MoneyHistory().update();
	}
	
	protected void typeAddValue(String name, double val) {
		int index=this.findMoneyIndex(name);
		allMoney.get(index).setValue(allMoney.get(index).getValue()+val);
		new MoneyDB(username, passWord).changeTypeVal(allMoney.get(index));
	}
}
