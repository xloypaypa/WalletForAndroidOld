package database;

import java.util.*;

import type.MoneyType;

public class MoneyDB extends DataBase {
	public MoneyDB(String name, String passWord) {
		super(name, passWord);
		this.aimPath=new String(Root+"/"+this.name+"/"+moneyPath);
	}
	
	public Vector<MoneyType> loadUser(){
		Vector <MoneyType> ans=new Vector <MoneyType>();
		
		Vector <String> file=new Vector<String>();
		file=HHD.readFile(aimPath, passWord);
		
		Vector <String> message=new Vector<String>();
		for (int i=0;i<file.size();i++){
			if (file.get(i).equals("[end]")){
				MoneyType mt=new MoneyType();
				mt.solveTypeMessage(message);
				ans.add(mt);
			}else if (file.get(i).equals("[begin]")){
				message=new Vector<String>();
			}else{
				message.add(file.get(i));
			}
		}
		return ans;
	}
	public void changeTypeVal(MoneyType moneyType){
		Vector <MoneyType> now=this.loadUser();
		String ans=new String();
		for (int i=0;i<now.size();i++){
			if (now.get(i).getType().equals(moneyType.getType())){
				ans+=moneyType.getAllMessage()+"\r\n";
			}else{
				ans+=now.get(i).getAllMessage()+"\r\n";
			}
		}
		HHD.writeFile(aimPath, ans, passWord);
	}
	public void addNewType(String typeName){
		MoneyType mt=new MoneyType();
		mt.setType(typeName); mt.setValue(0);
		HHD.addWriteFile(aimPath, mt.getAllMessage(), passWord);
	}
	public void addNewType(MoneyType money){
		HHD.addWriteFile(aimPath, money.getAllMessage(), passWord);
	}
	public void removeType(String typeName){
		Vector <MoneyType> now=this.loadUser();
		String ans=new String();
		for (int i=0;i<now.size();i++){
			if (!now.get(i).getType().equals(typeName)){
				ans+=now.get(i).getAllMessage()+"\r\n";
			}else{
				if (now.size()==1){
					HHD.cleanFile(this.aimPath);
					return ;
				}
			}
		}
		HHD.writeFile(aimPath, ans, passWord);
	}
	public void changeTypeName(String type,String newName){
		Vector <MoneyType> now=this.loadUser();
		String ans=new String();
		for (int i=0;i<now.size();i++){
			if (now.get(i).getType().equals(type)){
				now.get(i).setType(newName);
			}
		}
		for (int i=0;i<now.size();i++){
			ans+=now.get(i).getAllMessage()+"\r\n";
		}
		HHD.writeFile(aimPath, ans, passWord);
	}
}