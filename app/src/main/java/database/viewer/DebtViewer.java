package database.viewer;

import java.util.Vector;

import type.DebtType;
import type.Type;

public class DebtViewer extends IDDataViewer {
	
	public Vector<DebtType> getDebtType(String type){
		Vector <Type> all=this.getAllItem();
		Vector <DebtType> ans=new Vector<>();
		for (int i=0;i<all.size();i++){
			DebtType now=(DebtType) all.get(i);
			if (now.getDebtType().equals(type)){
				ans.addElement((DebtType) all.get(i));
			}
		}
		return ans;
	}

}
