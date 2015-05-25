package logic.action.debt;

import database.operator.DebtOperator;
import logic.ListenerManager;
import type.DebtType;

public class AddLoanAction extends AddDebtAction {

	@Override
	public void run() {
		DebtOperator keeper=(DebtOperator) data.getData("debt");
		DebtType debt=new DebtType();
		setDebtMessage(keeper, debt);
		debt.setDebtType("loan");
		keeper.add(debt);
		ListenerManager.setOKMessage();
	}

}
