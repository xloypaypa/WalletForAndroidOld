package logic.process.backup.debt;

import logic.action.money.IncomeAction;

public class RepayBorrowingBackup extends RepayDebtBackup {
	
	public RepayBorrowingBackup() {
		super("repay borrowing");
	}
	
	@Override
	public void backup() {
		super.backup();
		IncomeAction ia=new IncomeAction();
		ia.setValue(detail.getType(), detail.getValue());
		ia.run();
	}

}
