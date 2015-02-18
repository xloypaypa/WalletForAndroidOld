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
		
		Vector <String> file=new Vector<String>();
		file=HHD.readFile(aimPath, passWord);
		
		Vector <String> message=new Vector<String>();
		for (int i=0;i<file.size();i++){
			if (file.get(i).equals("[end]")){
				DebtType now=new DebtType();
				now.solveTypeMessage(message);
				ans.add(now);
			}else if (file.get(i).equals("[begin]")){
				message=new Vector<String>();
			}else{
				message.add(file.get(i));
			}
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
