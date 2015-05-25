package logic.action.money;

import database.operator.MoneyOperator;
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
		MoneyOperator keeper=(MoneyOperator) data.getData("money");
		MoneyType money=new MoneyType();
		money.setType(value); money.setValue(0);
		keeper.add(money);
		ListenerManager.setOKMessage();
	}

}
