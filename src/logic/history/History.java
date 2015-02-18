package logic.history;

import java.util.*;

import logic.User;
import logic.wallet.Detail;

import database.ReasonDB;
import type.*;

public class History extends User {
	static Vector <MoneyHistoryType> moneyhistory=new Vector<MoneyHistoryType>();
	static Vector <ReasonType> allReason=new Vector<ReasonType>();
	
	public void loadHistory(){
		moneyhistory=new Detail().getHistoricalType();
		allReason=new ReasonDB(username, passWord).loadReason();
	}
	
	public Vector <MoneyHistoryType> getMoneyHistory(){
		return moneyhistory;
	}
	
	public Vector <ReasonType> getReasonType(){
		return allReason;
	}
	
	public boolean reasonExist(String name){
		for (int i=0;i<allReason.size();i++){
			if (allReason.get(i).getName().equals(name)) return true;
		}
		return false;
	}
	
	public int findReasonIndex(String name){
		for (int i=0;i<allReason.size();i++){
			if (allReason.get(i).getName().equals(name)) return i;
		}
		return -1;
	}
}
