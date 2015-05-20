package logic.analysis;

import java.util.Vector;

import database.operator.MoneyKeeper;
import database.operator.TreeReasonKeeper;
import database.viewer.TreeReasonViewer;
import type.MoneyType;
import type.ReasonTreeNodeType;
import type.Type;
import logic.Logic;

public class Analyst extends Logic {
	
	TreeReasonKeeper keeper=(TreeReasonKeeper) data.getData("reason");
	int level;
	int day[]=new int[6];
	
	public void solve(){
		MoneyKeeper keeper=(MoneyKeeper) data.getData("money");
		Vector<Type> allTypes=keeper.getAllItem();
		double money=0;
		for (int i=0;i<allTypes.size();i++){
			money+=((MoneyType) allTypes.get(i)).getValue();
		}
		
		TreeReasonViewer tree=new TreeReasonViewer(); tree.loadData("reason");
		Vector <ReasonTreeNodeType> roots=tree.getRoots();
		double sum;
		
		for (level=5;level>=0;level--){
			sum=0;
			for (int i=0;i<roots.size();i++){
				sum+=getValue(roots.get(i));
			}
			day[level]=(int) Math.floor(30*(money/sum));
			if (sum==0) day[level]=100;
		}
	}
	
	public int getLevelDay(int pos){
		if (pos>5) pos=5;
		if (pos<0) pos=0;
		return day[pos];
	}
	
	public int getWarningLevel(){
		for (int i=5;i>=1;i--){
			if (day[i]<=7) return i;
		}
		return 0;
	}

	private double getValue(ReasonTreeNodeType now) {
		double ans=0;
		TreeReasonViewer tree=new TreeReasonViewer(); tree.loadData("reason");
		Vector <ReasonTreeNodeType> kid=tree.getKid(now);
		
		for (int i=0;i<kid.size();i++){
			ans+=getValue(kid.get(i));
		}
		
		if (now.getRank()>=level&&now.getMin()>ans){
			ans=now.getMin();
		}
		return ans;
	}
}
