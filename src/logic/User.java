package logic;

import java.util.Vector;

import logic.history.MoneyHistory;
import logic.history.ReasonHistory;
import logic.history.TreeReasonHistory;
import logic.wallet.Cost;
import logic.wallet.Debt;
import logic.wallet.Money;
import database.DataBase;
import database.DetailDB;
import encryptionAlgorithm.MD5;
import type.*;

public class User {
	protected static String username;
	protected static String passWord;
	public static String userReason;
	
	protected static Vector <DetailType> allDetail=new Vector<DetailType>();
	protected static Vector <UserMessage> allUser=new Vector<UserMessage>();
	
	public void loadUser(){
		allUser=new DataBase("", "").loadAllUser();
	}
	
	public void addUser(String name, String pass, String userReason){
		UserMessage um=new UserMessage(name, MD5.encode(pass), userReason);
		allUser.addElement(um);
		new DataBase("","").addUser(um);
	}
	
	public void login(String name, String pass){
		username=name;
		passWord=pass;
		userReason=getUserReason(name);
	}
	
	public boolean checkUser(String name, String pass){
		for (int i=0;i<allUser.size();i++){
			if (allUser.get(i).getName().equals(name)&&allUser.get(i).getPassWord().equals(MD5.encode(pass))){
				return true;
			}
		}
		return false;
	}
	
	public boolean userExist(String name){
		for (int i=0;i<allUser.size();i++){
			if (allUser.get(i).getName().equals(name)){
				return true;
			}
		}
		return false;
	}
	
	public String getUserReason(String userName){
		for (int i=0;i<allUser.size();i++){
			if (allUser.get(i).getName().equals(userName)) return allUser.get(i).getReason();
		}
		return null;
	}
	
	public void backup(){
		if (allDetail.size()==0) return ;
		
		DetailType last=allDetail.get(allDetail.size()-1);
		Cost cost=new Cost();
		cost.backup(last);
		Debt debt=new Debt();
		debt.backup(last);
		Money money=new Money();
		money.backup(last);
		
		if (User.userReason.equals("normal")){
			ReasonHistory rh=new ReasonHistory();
			rh.backup(last);
		}else{
			TreeReasonHistory trh=new TreeReasonHistory();
			trh.backup(last);
		}
		
		allDetail.remove(allDetail.size()-1);
		new DetailDB(username, passWord).backupDetail();
		new MoneyHistory().update();
	}
}
