package logic.action.reason;

import java.util.Vector;

import database.operator.ReasonKeeper;
import logic.ListenerManager;
import type.ReasonTreeNodeType;
import type.Type;

public class RemoveTreeReasonAction extends RemoveReasonAction {
	
	@Override
	public void run() {
		super.run();
		
		ReasonKeeper keeper=(ReasonKeeper) data.getData("reason");
		Vector <Type> all=keeper.getAllItem();
		for (int i=0;i<all.size();i++){
			ReasonTreeNodeType now=(ReasonTreeNodeType) all.get(i);
			if (now.getFather().equals(name)){
				now.setFatherName("root");
				keeper.update(now.getName(), now);
			}
		}
		
		ListenerManager.setOKMessage();
	}

}
