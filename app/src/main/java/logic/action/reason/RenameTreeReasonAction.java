package logic.action.reason;

import java.util.Vector;

import database.operator.ReasonOperator;
import logic.ListenerManager;
import type.ReasonTreeNodeType;
import type.Type;

public class RenameTreeReasonAction extends RenameReasonAction {
	
	String father;
	double min,max;
	int rank;
	
	public void setValue(String father,double min,double max,int rank){
		this.father=father;
		this.min=min;
		this.max=max;
		this.rank=rank;
	}

	@Override
	public void run() {
		ReasonOperator keeper=(ReasonOperator) data.getData("reason");
		ReasonTreeNodeType reason=(ReasonTreeNodeType) keeper.getItem(past);
		reason.setFatherName(father);
		reason.setMax(max);
		reason.setMin(min);
		reason.setName(name);
		reason.setRank(rank);
		keeper.update(past, reason);
		
		Vector <Type> all=keeper.getAllItem();
		for (int i=0;i<all.size();i++){
			ReasonTreeNodeType now=(ReasonTreeNodeType) all.get(i);
			if (now.getFather().equals(past)){
				now.setFatherName(name);
				keeper.update(now.getName(), now);
			}
		}
		
		ListenerManager.setOKMessage();
	}

}
