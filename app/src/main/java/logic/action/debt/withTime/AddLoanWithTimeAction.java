package logic.action.debt.withTime;

import database.operator.DebtOperator;
import logic.ListenerManager;
import type.DebtType;

public class AddLoanWithTimeAction extends AddDebtWithTimeAction {

	@Override
	public void run() {
		DebtOperator keeper=(DebtOperator) data.getData("debt");
		DebtType debt=new DebtType();
		setDebtMessage(keeper, debt);
		debt.setStartingTime(time);
		debt.setLastUpdateTime(time);
		debt.setDebtType("loan");
		keeper.add(debt);
		ListenerManager.setOKMessage();
	}

}
