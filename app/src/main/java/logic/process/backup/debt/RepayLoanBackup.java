package logic.process.backup.debt;

import logic.action.money.ExpenditureAction;

public class RepayLoanBackup extends RepayDebtBackup {
	
	public RepayLoanBackup() {
		super("repay loan");
	}
	
	@Override
	public void backup() {
		super.backup();
		ExpenditureAction ea=new ExpenditureAction();
		ea.setValue(detail.getType(), detail.getValue());
		ea.run();
	}

}
