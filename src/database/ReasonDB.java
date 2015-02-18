package database;

import java.util.*;

import type.ReasonType;

public class ReasonDB extends DataBase {

	public ReasonDB(String name, String passWord) {
		super(name, passWord);
		this.aimPath=new String(Root+"/"+this.name+"/"+reasonPath);
	}
	
	public Vector <ReasonType> loadReason(){
		Vector <ReasonType> ans=new Vector<ReasonType>();
		
		Vector <String> file=new Vector<String>();
		file=HHD.readFile(aimPath, passWord);
		
		return solveReason(ans, file);
	}
	
	public static Vector<ReasonType> solveReason(Vector<ReasonType> ans,
			Vector<String> file) {
		Vector <String> message=new Vector<String>();
		for (int i=0;i<file.size();i++){
			if (file.get(i).equals("[end]")){
				ReasonType rt=new ReasonType();
				rt.solveTypeMessage(message);
				ans.add(rt);
			}else if (file.get(i).equals("[begin]")){
				message=new Vector<String>();
			}else{
				message.add(file.get(i));
			}
		}
		return ans;
	}
	
	public void addReason(ReasonType rt){
		HHD.addWriteFile(aimPath, rt.getAllMessage(), passWord);
	}
	
	public void updateReason(String name, ReasonType rt){
		Vector <ReasonType> now=this.loadReason();
		String ans=new String();
		for (int i=0;i<now.size();i++){
			if (now.get(i).getName().equals(name)){
				ans+=rt.getAllMessage()+"\r\n";
			}else{
				ans+=now.get(i).getAllMessage()+"\r\n";
			}
		}
		HHD.writeFile(aimPath, ans, passWord);
	}
	
	public void deleteReason(String name){
		Vector <ReasonType> now=this.loadReason();
		String ans=new String();
		for (int i=0;i<now.size();i++){
			if (now.get(i).getName().equals(name)) continue;
			ans+=now.get(i).getAllMessage()+"\r\n";
		}
		HHD.writeFile(aimPath, ans, passWord);
	}

}
