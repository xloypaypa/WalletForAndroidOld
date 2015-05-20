package logic.action.detail;

import java.util.Vector;

import database.operator.TreeReasonKeeper;
import type.ReasonTreeNodeType;
import type.Type;

public class DetailSaveTreeReasonAction extends DetailSaveReasonAction {
	
	public void saveReason(String name){
		ReasonTreeNodeType reason=(ReasonTreeNodeType) super.getReason(name);
		super.saveBaseMessage(reason);
		
		detail.addExtra("past reason father", reason.getFather());
		detail.addExtra("past reason min", reason.getMin()+"");
		detail.addExtra("past reason max", reason.getMax()+"");
		detail.addExtra("past reason rank", reason.getRank()+"");
		
		TreeReasonKeeper keeper=(TreeReasonKeeper) data.getData("reason");
		Vector <Type> all=keeper.getAllItem();
		for (int i=0;i<all.size();i++){
			ReasonTreeNodeType now=(ReasonTreeNodeType) all.get(i);
			if (now.getFather().equals(detail.getReason())){
				detail.addExtra("have kid reason", now.getName());
			}
		}
	}

}
