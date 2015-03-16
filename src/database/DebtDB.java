package database;

import java.util.*;

import type.*;

public class DebtDB extends DataBase {
	public DebtDB(String name, String passWord) {
		super(name, passWord);
		this.aimPath=new String(Root+"/"+this.name+"/"+debtPath);
	}
	public Vector <DebtType> loadDebt(){
		Vector <DebtType> ans=new Vector<DebtType>();
		Vector <Vector<String>> all=this.loadFile();
		for (int i=0;i<all.size();i++){
			DebtType now=new DebtType();
			now.solveTypeMessage(all.get(i));
			ans.add(now);
		}
		return ans;
	}
	public void addDebt(DebtType dt){
		HHD.addWriteFile(aimPath, dt.getAllMessage(), passWord);
	}
	public void deleteDebt(int id){
		Vector <DebtType> now=this.loadDebt();
		String ans=new String();
		for (int i=0;i<now.size();i++){
			if (now.get(i).getID()!=id){
				ans+=now.get(i).getAllMessage()+"\r\n";
			}else{
				if (now.size()==1){
					HHD.cleanFile(Root+"/"+name+"/debt.txt");
					return ;
				}
			}
		}
		HHD.writeFile(aimPath, ans, passWord);
	}
	public void changeDebt(int id,DebtType dt){
		Vector <DebtType> now=this.loadDebt();
		String ans=new String();
		for (int i=0;i<now.size();i++){
			if (now.get(i).getID()!=id){
				ans+=now.get(i).getAllMessage()+"\r\n";
			}else{
				ans+=dt.getAllMessage()+"\r\n";
			}
		}
		HHD.writeFile(aimPath, ans, passWord);
	}
}
