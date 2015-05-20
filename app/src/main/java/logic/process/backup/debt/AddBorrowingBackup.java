package logic.process.backup.debt;

import logic.action.money.ExpenditureAction;

public class AddBorrowingBackup extends AddDebtBackup {
	
	public AddBorrowingBackup() {
		super("add borrowing");
	}
	
	@Override
	public void backup() {
		super.backup();
		ExpenditureAction ea=new ExpenditureAction();
		ea.setValue(detail.getType(), detail.getValue());
		ea.run();
	}

}
