package database.operator;

import java.util.Vector;

import database.AbstractDataBase;
import database.noPassword.PublicDataBase;
import encryptionAlgorithm.MD5;
import type.Type;
import type.UserMessage;

public class UserPublicData {
	protected static String username,password,userType;
	
	protected static Vector <UserMessage> allUser=new Vector<UserMessage>();
	protected static AbstractDataBase db=new PublicDataBase();
	
	public static void loadUser(){
		Vector <Type> ans=db.load();
		allUser.removeAllElements();
		for (int i=0;i<ans.size();i++){
			allUser.addElement((UserMessage) ans.get(i));
		}
	}
	
	public static void releaseUser(){
		allUser.removeAllElements();
	}
	
	public static void addUser(UserMessage user){
		allUser.addElement(user);
		db.addItem(user);
	}
	
	public static void login(String name, String pass){
		username=name;
		password=pass;
		userType=getUserReason(name);
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
	
	public static String getUserType(){
		return userType;
	}
}
