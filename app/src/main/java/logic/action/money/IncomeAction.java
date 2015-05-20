package logic.action.money;

import database.operator.MoneyKeeper;
import logic.ListenerManager;
import logic.action.AbstractAction;
import type.MoneyType;

public class IncomeAction extends AbstractAction {
	
	String type; double value;
	
	public IncomeAction() {
		super("income");
	}
	
	public void setValue(String type,double value){
		this.type=type;
		this.value=value;
	}

	@Override
	public void run() {
		MoneyKeeper keeper=(MoneyKeeper) data.getData("money");
		MoneyType money=(MoneyType) keeper.getItem(type);
		money.setType(type); money.setValue(money.getValue()+value);
		keeper.update(type, money);
		ListenerManager.setOKMessage();
	}

}
