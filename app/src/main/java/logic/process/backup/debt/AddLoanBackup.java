package logic.process.backup.debt;

import logic.action.money.IncomeAction;

public class AddLoanBackup extends AddDebtBackup {
	
	public AddLoanBackup() {
		super("add loan");
	}
	
	@Override
	public void backup() {
		super.backup();
		IncomeAction ia=new IncomeAction();
		ia.setValue(detail.getType(), detail.getValue());
		ia.run();
	}

}
