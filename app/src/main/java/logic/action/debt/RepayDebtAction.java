package logic.action.debt;

import database.operator.DebtOperator;
import type.DebtType;
import logic.ListenerManager;
import logic.action.AbstractAction;

public class RepayDebtAction extends AbstractAction {
	
	protected int id;
	protected double value;
	
	public RepayDebtAction() {
		super("repay debt");
	}
	
	public void setValue(int id,double value){
		this.id=id;
		this.value=value;
	}
	
	@Override
	public void run() {
		DebtOperator keeper=(DebtOperator) data.getData("debt");
		DebtType debt=(DebtType) keeper.getItem(id+"");
		debt.repayment(value);
		if (debt.getMaxRepay()>1e-3) keeper.update(id+"", debt);
		else keeper.delete(id+"");
		ListenerManager.setOKMessage();
	}

}
