package logic.action.reason;

import database.operator.ReasonOperator;
import logic.ListenerManager;
import logic.action.AbstractAction;
import type.ReasonType;

public class RenameReasonAction extends AbstractAction {
	
	String past,name;
	
	public RenameReasonAction() {
		super("rename reason");
	}
	
	public void setValue(String past,String name){
		this.past=past;
		this.name=name;
	}

	@Override
	public void run() {
		ReasonOperator keeper=(ReasonOperator) data.getData("reason");
		ReasonType reason=(ReasonType) keeper.getItem(past);
		reason.setName(name);
		keeper.update(past, reason);
		ListenerManager.setOKMessage();
	}

}
