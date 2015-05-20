package logic.check;

import database.operator.MoneyKeeper;
import logic.ListenerManager;
import logic.event.AbstractSteep;
import type.MoneyType;

public class MoneyEnoughCheck extends AbstractSteep {
	
	String type; double value;
	
	public MoneyEnoughCheck() {
		super("money enough");
	}
	
	public void setValue(String type,double value){
		this.type=type;
		this.value=value;
	}

	@Override
	public boolean action() {
		MoneyKeeper keeper=(MoneyKeeper) data.getData("money");
		MoneyType money=(MoneyType) keeper.getItem(type);
		if (money.getValue()<value){
			ListenerManager.setErrorMessage("Don't have enough money.");
			ListenerManager.UIAction();
			return false;
		}
		return true;
	}

}
