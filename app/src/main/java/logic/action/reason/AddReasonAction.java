package logic.action.reason;

import database.operator.ReasonKeeper;
import logic.ListenerManager;
import logic.action.AbstractAction;
import type.ReasonType;

public class AddReasonAction extends AbstractAction {
	
	String name;
	
	public AddReasonAction() {
		super("add reason");
	}
	
	public void setValue(String name){
		this.name=name;
	}

	@Override
	public void run() {
		ReasonKeeper keeper=(ReasonKeeper) data.getData("reason");
		ReasonType reason=new ReasonType();
		reason.setName(name); reason.setExpenditure(0); reason.setIncome(0);
		keeper.add(reason);
		ListenerManager.setOKMessage();
	}

}
