package logic.action.money;

import database.operator.MoneyKeeper;
import logic.ListenerManager;
import logic.action.AbstractAction;
import type.MoneyType;

public class AddTypeAction extends AbstractAction {
	
	public AddTypeAction() {
		super("add type");
	}
	
	String value;
	
	public void setName(String name){
		this.value=name;
	}

	@Override
	public void run() {
		MoneyKeeper keeper=(MoneyKeeper) data.getData("money");
		MoneyType money=new MoneyType();
		money.setType(value); money.setValue(0);
		keeper.add(money);
		ListenerManager.setOKMessage();
	}

}
