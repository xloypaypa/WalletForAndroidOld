package logic.check;

import database.operator.DebtKeeper;
import logic.ListenerManager;
import logic.event.AbstractSteep;
import type.DebtType;

public class DebtValueLimitCheck extends AbstractSteep {
	
	int id; double value;
	
	public DebtValueLimitCheck() {
		super("debt value limit");
	}
	
	public void setValue(int id,double value){
		this.id=id;
		this.value=value;
	}

	@Override
	public boolean action() {
		DebtKeeper keeper=(DebtKeeper) data.getData("debt");
		DebtType debt=(DebtType) keeper.getItem(id+"");
		if (debt.getMaxRepay()<value){
			ListenerManager.setDebtRepayLimitError(debt.getMaxRepay());
			ListenerManager.UIAction();
			return false;
		}
		return true;
	}

}
