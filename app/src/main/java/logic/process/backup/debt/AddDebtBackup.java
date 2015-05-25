package logic.process.backup.debt;

import database.operator.DebtOperator;
import logic.process.backup.AbstractBackup;

public class AddDebtBackup extends AbstractBackup {

	public AddDebtBackup(String processName) {
		super(processName);
	}

	@Override
	public void backup() {
		DebtOperator keeper=(DebtOperator) data.getData("debt");
		keeper.delete(detail.getExtraMessage("debt id"));
	}

}
