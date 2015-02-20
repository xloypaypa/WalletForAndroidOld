package logic;

import java.util.Vector;

import type.ReasonTreeNodeType;
import logic.history.TreeReasonHistory;
import logic.wallet.Money;

public class Analyst {
	
	TreeReasonHistory trh=new TreeReasonHistory();
	int level;
	int day[]=new int[6];
	
	public void solve(){
		double money=new Money().getTotalMoney();
		Vector <ReasonTreeNodeType> roots=trh.getRoots();
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
		Vector <ReasonTreeNodeType> kid=trh.getKid(now.getName());
		
		for (int i=0;i<kid.size();i++){
			ans+=getValue(kid.get(i));
		}
		
		if (now.getRank()>=level&&now.getMin()>ans){
			ans=now.getMin();
		}
		return ans;
	}
}
