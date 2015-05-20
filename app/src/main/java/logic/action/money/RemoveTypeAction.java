package logic.action.money;

import database.operator.MoneyKeeper;
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
		MoneyKeeper keeper=(MoneyKeeper) data.getData("money");
		keeper.delete(type);
		ListenerManager.setOKMessage();
	}

}
