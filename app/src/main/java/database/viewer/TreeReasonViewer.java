package database.viewer;

import java.util.Vector;

import type.ReasonTreeNodeType;
import type.ReasonType;
import type.Type;

public class TreeReasonViewer extends IDDataViewer {
	
	public double solveIncome(ReasonTreeNodeType node) {
		double ans=node.getIncome();
		Vector <Integer> kids=node.getKid();
		for (int i=0;i<kids.size();i++){
			ReasonType now=(ReasonType) keeper.getAllItem().get(kids.get(i));
			ans-=now.getIncome();
		}
		return ans;
	}
	
	public double solveExpenditure(ReasonTreeNodeType node) {
		double ans=node.getExpenditure();
		Vector <Integer> kids=node.getKid();
		for (int i=0;i<kids.size();i++){
			ReasonType now=(ReasonType) keeper.getAllItem().get(kids.get(i));
			ans-=now.getExpenditure();
		}
		return ans;
	}
	
	public Vector <ReasonTreeNodeType> getRoots(){
		Vector <ReasonTreeNodeType> ans=new Vector<ReasonTreeNodeType>();
		Vector<Type> allType=keeper.getAllItem();
		for (int i=0;i<allType.size();i++){
			if(((ReasonTreeNodeType) allType.get(i)).getFather().equals("root")){
				ans.add((ReasonTreeNodeType) allType.get(i));
			}
		}
		return ans;
	}
	
	public Vector <ReasonTreeNodeType> getKid(ReasonTreeNodeType now){
		Vector <ReasonTreeNodeType> ans=new Vector<ReasonTreeNodeType>();
		Vector<Type> allReason=keeper.getAllItem();
		Vector <Integer> kids=now.getKid();
		for (int i=0;i<kids.size();i++){
			ans.addElement((ReasonTreeNodeType) allReason.get(kids.get(i)));
		}
		return ans;
	}

}
