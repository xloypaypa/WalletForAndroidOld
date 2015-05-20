package logic.process.backup.money;

import database.operator.UserPublicData;
import logic.action.money.ExpenditureAction;
import logic.action.reason.ReasonIncomeAction;
import logic.action.reason.TreeReasonIncomeAction;
import logic.process.backup.AbstractBackup;

public class IncomeBackup extends AbstractBackup {

	public IncomeBackup() {
		super("income");
	}

	@Override
	public void backup() {
		ExpenditureAction ia=new ExpenditureAction();
		ia.setValue(detail.getType(), detail.getValue());
		ia.run();
		
		ReasonIncomeAction ria;
		if (UserPublicData.getUserType().equals("tree")) {
			ria=new TreeReasonIncomeAction();
		} else {
			ria=new ReasonIncomeAction();
		}
		ria.setValue(detail.getReason(), -detail.getValue());
		ria.run();
	}

}
