package logic.action.debt;

import database.operator.DebtKeeper;
import logic.ListenerManager;
import type.DebtType;

public class AddBorrowingAction extends AddDebtAction {

	@Override
	public void run() {
		DebtKeeper keeper=(DebtKeeper) data.getData("debt");
		DebtType debt=new DebtType();
		setDebtMessage(keeper, debt);
		debt.setDebtType("borrowing");
		keeper.add(debt);
		ListenerManager.setOKMessage();
	}

}
