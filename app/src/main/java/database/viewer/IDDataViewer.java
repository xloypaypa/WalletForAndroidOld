package database.viewer;

import java.util.Vector;

import database.operator.IDDataOperator;
import type.Type;

public class IDDataViewer extends DataViewer {
	
	public Vector<String> getAllID(){
		Vector<String> ans=new Vector<>();
		Vector<Type> all=this.getAllItem();
		for (int i=0;i<all.size();i++){
			ans.add(all.get(i).getTypeID());
		}
		return ans;
	}
	
	public Type getItem(String id){
		return ((IDDataOperator) keeper).getItem(id);
	}

}
