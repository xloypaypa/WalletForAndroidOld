package logic.action.reason;

import database.operator.ReasonOperator;
import logic.ListenerManager;
import logic.action.AbstractAction;
import type.ReasonType;

public class ReasonExpenditureAction extends AbstractAction {

	String name; double value;
	
	public ReasonExpenditureAction() {
		super("reason expenditure");
	}
	
	public void setValue(String name,double value){
		this.name=name;
		this.value=value;
	}

	@Override
	public void run() {
		ReasonOperator keeper=(ReasonOperator) data.getData("reason");
		ReasonType reason=(ReasonType) keeper.getItem(name);
		reason.setExpenditure(reason.getExpenditure()+value);
		keeper.update(name, reason);
		ListenerManager.setOKMessage();
	}

}
