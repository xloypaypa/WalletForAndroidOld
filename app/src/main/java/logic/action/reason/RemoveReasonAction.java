package logic.action.reason;

import database.operator.ReasonOperator;
import logic.ListenerManager;
import logic.action.AbstractAction;

public class RemoveReasonAction extends AbstractAction {
	
	String name;
	
	public RemoveReasonAction() {
		super("remove reason");
	}
	
	public void setValue(String name){
		this.name=name;
	}

	@Override
	public void run() {
		ReasonOperator keeper=(ReasonOperator) data.getData("reason");
		keeper.delete(name);
		ListenerManager.setOKMessage();
	}

}
