package database;

import java.util.*;

import type.ReasonTreeNodeType;
import type.ReasonType;

public class ReasonDB extends DataBase {

	public ReasonDB(String name, String passWord) {
		super(name, passWord);
		this.aimPath=new String(Root+"/"+this.name+"/"+reasonPath);
	}
	
	public Vector <ReasonType> loadReason(){
		Vector <ReasonType> ans=new Vector<ReasonType>();
		Vector <Vector <String>> all=this.loadFile();
		for (int i=0;i<all.size();i++){
			ReasonType rt;
			if (getTypeMessage(all.get(i)).equals("reason type")){
				rt=new ReasonType();
			}else{
				rt=new ReasonTreeNodeType();
			}
			rt.solveTypeMessage(all.get(i));
			ans.add(rt);
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
