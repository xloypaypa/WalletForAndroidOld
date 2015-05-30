package database.operator;

import type.ReasonTreeNodeType;
import type.IDType;
import database.password.DataBase;

public class TreeReasonOperator extends ReasonOperator {
	
	@Override
	public void loadData() {
		super.loadData();
		buildTree();
	}
	
	@Override
	public boolean itemExist(String id) {
		if (id.equals("root")) return true;
		else return super.itemExist(id);
	}
	
	public void buildTree(){
		for (int i=0;i<allType.size();i++){
			ReasonTreeNodeType now=(ReasonTreeNodeType) allType.get(i);
			now.clearKid();
		}
		for (int i=0;i<allType.size();i++){
			String father=((ReasonTreeNodeType) allType.get(i)).getFather();
			int fPos=getPos(father);
			((ReasonTreeNodeType) allType.get(i)).setFatherPos(fPos);
			if (fPos!=-1){
				((ReasonTreeNodeType) allType.get(fPos)).addKid(i);
			}
		}
	}
	
	protected void loadDataBase() {
		DataBase ans=new DataBase() {
			@Override
			public IDType getNewType() {
				return new ReasonTreeNodeType();
			}
		};
		ans.setAimFile("/"+username+"/"+keeperName+".txt");
		ans.setPassword(password);
		this.db=ans;
	}
	
}
