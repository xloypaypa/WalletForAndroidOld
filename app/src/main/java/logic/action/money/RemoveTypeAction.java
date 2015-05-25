package logic.action.money;

import database.operator.MoneyOperator;
import logic.ListenerManager;
import logic.action.AbstractAction;

public class RemoveTypeAction extends AbstractAction {
	
	String type;
	
	public RemoveTypeAction() {
		super("remove type");
	}
	
	public void setType(String type){
		this.type=type;
	}

	@Override
	public void run() {
		MoneyOperator keeper=(MoneyOperator) data.getData("money");
		keeper.delete(type);
		ListenerManager.setOKMessage();
	}

}
