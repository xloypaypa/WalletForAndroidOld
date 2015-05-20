package logic.action.debt.withTime;

import database.operator.DebtKeeper;
import logic.ListenerManager;
import type.DebtType;

public class AddLoanWithTimeAction extends AddDebtWithTimeAction {

	@Override
	public void run() {
		DebtKeeper keeper=(DebtKeeper) data.getData("debt");
		DebtType debt=new DebtType();
		setDebtMessage(keeper, debt);
		debt.setStartingTime(time);
		debt.setLastUpdateTime(time);
		debt.setDebtType("loan");
		keeper.add(debt);
		ListenerManager.setOKMessage();
	}

}
