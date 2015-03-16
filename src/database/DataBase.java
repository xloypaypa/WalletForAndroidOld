package database;

import type.UserMessage;

import java.util.*;

public class DataBase {
	static public String Root;
	protected String name, passWord, detailPath, debtPath, moneyPath, reasonPath;
	protected String aimPath;
	public DataBase(String user,String passWord) {
		this.name=new String(user);
		this.passWord=new String(passWord);
		this.detailPath=new String("detail.txt");
		this.debtPath=new String("debt.txt");
		this.moneyPath=new String("user.txt");
		this.reasonPath=new String("reason.txt");
		this.aimPath=Root+"/all user.txt";
	}
	
	public String getAimPath(){
		return this.aimPath;
	}
	
	public Vector < Vector<String> > loadFile(){
		Vector <String> file=HHD.readFile(aimPath, passWord);
		return solveFile(file);
	}
	
	public Vector < Vector<String> > loadNotEncryptedFile(){
		Vector <String> file=HHD.readFile(aimPath);
		return solveFile(file);
	}
	
	public void addUser(UserMessage um){
		HHD.addWriteFile(aimPath, um.getAllMessage());
	}
	
	public Vector <UserMessage> loadAllUser(){
		Vector <UserMessage> ans=new Vector <UserMessage>();
		Vector <Vector <String>> file=this.loadNotEncryptedFile();
		
		for (int i=0;i<file.size();i++){
			UserMessage mu=new UserMessage();
			mu.solveTypeMessage(file.get(i));
			ans.add(mu);
		}
		return ans;
	}
	
	public static String getTypeMessage(Vector <String> message){
		for (int i=0;i<message.size();i++){
			if (message.get(i).equals("[type name]")){
				return message.get(i+1);
			}
		}
		return null;
	}
	
	public Vector<Vector<String>> solveFile(Vector<String> file) {
		Vector < Vector <String> > ans=new Vector<Vector<String>>();
		Vector <String> message=new Vector<String>();
		for (int i=0;i<file.size();i++){
			if (file.get(i).equals("[end]")){
				ans.add(message);
				message=new Vector<String>();
			}else if (file.get(i).equals("[begin]")){
				message=new Vector<String>();
			}else{
				message.add(file.get(i));
			}
		}
		return ans;
	}
	
	public void clean(){
		HHD.cleanFile(aimPath);
	}
}
