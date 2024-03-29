package logic.action.reason;

import database.operator.ReasonOperator;
import logic.ListenerManager;
import logic.action.AbstractAction;
import type.ReasonType;

public class ReasonIncomeAction extends AbstractAction {
	
	String name; double value;
	
	public ReasonIncomeAction() {
		super("reason income");
	}
	
	public void setValue(String name,double value){
		this.name=name;
		this.value=value;
	}

	@Override
	public void run() {
		ReasonOperator keeper=(ReasonOperator) data.getData("reason");
		ReasonType reason=(ReasonType) keeper.getItem(name);
		reason.setIncome(reason.getIncome()+value);
		keeper.update(name, reason);
		ListenerManager.setOKMessage();
	}

}
