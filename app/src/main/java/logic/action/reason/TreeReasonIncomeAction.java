package logic.action.reason;

import database.operator.ReasonKeeper;
import logic.ListenerManager;
import type.ReasonTreeNodeType;

public class TreeReasonIncomeAction extends ReasonIncomeAction {
	
	@Override
	public void run() {
		ReasonKeeper keeper=(ReasonKeeper) data.getData("reason");
		ReasonTreeNodeType reason=(ReasonTreeNodeType) keeper.getItem(name);
		while (true){
			reason.setIncome(reason.getIncome()+value);
			keeper.update(reason.getName(), reason);
			if (reason.getFather().equals("root")) break;
			reason=(ReasonTreeNodeType) keeper.getItem(reason.getFather());
		}
		ListenerManager.setOKMessage();
	}

}
