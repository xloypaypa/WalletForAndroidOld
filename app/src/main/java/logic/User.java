package logic;

import java.util.Date;
import java.util.Vector;

import logic.history.History;
import logic.history.MoneyHistory;
import logic.history.ReasonHistory;
import logic.history.TreeReasonHistory;
import logic.wallet.Cost;
import logic.wallet.Debt;
import logic.wallet.Money;
import logic.wallet.Wallet;
import database.DataBase;
import database.DebtDB;
import database.DetailDB;
import database.MoneyDB;
import database.ReasonDB;
import encryptionAlgorithm.MD5;
import type.*;

public class User {
	protected static String username;
	protected static String passWord;
	public static String userReason;
	
	protected static Vector <DetailType> allDetail=new Vector<DetailType>();
	protected static Vector <UserMessage> allUser=new Vector<UserMessage>();
	
	private static LogicListener userListener;
	
	public static void loadUser(){
		allUser=new DataBase("", "").loadAllUser();
	}
	
	public static void reloadUserData(){
		Wallet wallet=new Wallet();
		History history=new History();
		wallet.loadWallet();
		history.loadHistory();
		if (!User.userReason.equals("normal")){
			history.build();
		}
	}
	
	public static void releaseUserData(){
		Wallet wallet=new Wallet();
		History history=new History();
		wallet.releaseWallet();
		history.releaseHistory();
	}
	
	public static void addUser(String name, String pass, String userReason){
		UserMessage um=new UserMessage(name, MD5.encode(pass), userReason);
		allUser.addElement(um);
		new DataBase("","").addUser(um);
	}
	
	public static void login(String name, String pass){
		username=name;
		passWord=pass;
		userReason=getUserReason(name);
		reloadUserData();
	}
	
	public static Vector <String> getAllUserName(){
		Vector <String> ans=new Vector<String>();
		for (int i=0;i<allUser.size();i++){
			ans.add(allUser.get(i).getName());
		}
		return ans;
	}
	
	public static boolean checkUser(String name, String pass){
		for (int i=0;i<allUser.size();i++){
			if (allUser.get(i).getName().equals(name)&&allUser.get(i).getPassWord().equals(MD5.encode(pass))){
				return true;
			}
		}
		return false;
	}
	
	public static boolean userExist(String name){
		for (int i=0;i<allUser.size();i++){
			if (allUser.get(i).getName().equals(name)){
				return true;
			}
		}
		return false;
	}
	
	public static String getUserReason(String userName){
		for (int i=0;i<allUser.size();i++){
			if (allUser.get(i).getName().equals(userName)) return allUser.get(i).getReason();
		}
		return null;
	}
	
	public static String getNowUser(){
		return username;
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
	
	public void solveDetail(Vector <DetailType> details){
		User.releaseUserData();
		new DebtDB(username, passWord).clean();
		new DetailDB(username, passWord).clean();
		new MoneyDB(username, passWord).clean();
		new ReasonDB(username, passWord).clean();
		for (int i=0;i<details.size();i++){
			DetailType now=details.get(i);
			new Cost().doDetail(now);
			new Debt().doDetail(now);
			new Money().doDetail(now);
			
			if (User.userReason.equals("normal")){
				new ReasonHistory().doDetail(now);;
			}else{
				new TreeReasonHistory().doDetail(now);
			}
		}
	}
	
	public void setUserListener(LogicListener listener){
		userListener=listener;
	}
	
	public void checkExtra(DetailType now,String check){
		if (userListener==null){
			return ;
		}
		
		if (now.extraExist(check)) return ;
		
		CheckExtraMessageListener l=(CheckExtraMessageListener) userListener;
		l.setDetail(now);
		l.setCheck(check);
		l.logicAction();
	}
	
	protected void setDetailTime(DetailType dt) {
		dt.setTime(new Date());
	}
	protected void setDetailTime(Date time, DetailType dt) {
		dt.setTime(time);
	}
}
