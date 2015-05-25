package logic.action.reason;

import database.operator.ReasonOperator;
import logic.ListenerManager;
import type.ReasonTreeNodeType;

public class TreeReasonExpenditureAction extends ReasonExpenditureAction {

	@Override
	public void run() {
		ReasonOperator keeper=(ReasonOperator) data.getData("reason");
		ReasonTreeNodeType reason=(ReasonTreeNodeType) keeper.getItem(name);
		while (true){
			reason.setExpenditure(reason.getExpenditure()+value);
			keeper.update(reason.getName(), reason);
			if (reason.getFather().equals("root")) break;
			reason=(ReasonTreeNodeType) keeper.getItem(reason.getFather());
		}
		ListenerManager.setOKMessage();
	}
	
}
