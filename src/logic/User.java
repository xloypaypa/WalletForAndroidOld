package logic;

import java.util.Vector;

import logic.history.MoneyHistory;
import logic.history.ReasonHistory;
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
	
	protected static Vector <DetailType> allDetail=new Vector<DetailType>();
	protected static Vector <UserMessage> allUser=new Vector<UserMessage>();
	
	public void loadUser(){
		allUser=new DataBase("", "").loadAllUser();
	}
	
	public void addUser(String name, String pass){
		UserMessage um=new UserMessage(name, MD5.encode(pass));
		allUser.addElement(um);
		new DataBase("","").addUser(um);
	}
	
	public void login(String name, String pass){
		username=name;
		passWord=pass;
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
	
	public void backup(){
		if (allDetail.size()==0) return ;
		
		DetailType last=allDetail.get(allDetail.size()-1);
		Cost cost=new Cost();
		cost.backup(last);
		Debt debt=new Debt();
		debt.backup(last);
		Money money=new Money();
		money.backup(last);
		ReasonHistory rh=new ReasonHistory();
		rh.backup(last);
		
		allDetail.remove(allDetail.size()-1);
		new DetailDB(username, passWord).backupDetail();
		new MoneyHistory().update();
	}
}
