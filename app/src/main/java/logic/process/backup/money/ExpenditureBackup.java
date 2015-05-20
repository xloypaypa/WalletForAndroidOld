package logic.process.backup.money;

import database.operator.UserPublicData;
import logic.action.money.IncomeAction;
import logic.action.reason.ReasonExpenditureAction;
import logic.action.reason.TreeReasonExpenditureAction;
import logic.process.backup.AbstractBackup;

public class ExpenditureBackup extends AbstractBackup {

	public ExpenditureBackup() {
		super("expenditure");
	}

	@Override
	public void backup() {
		IncomeAction ia=new IncomeAction();
		ia.setValue(detail.getType(), detail.getValue());
		ia.run();
		
		ReasonExpenditureAction rea;
		if (UserPublicData.getUserType().equals("tree")) {
			rea=new TreeReasonExpenditureAction();
		}else{
			rea=new ReasonExpenditureAction();
		}
		rea.setValue(detail.getReason(), -detail.getValue());
		rea.run();
	}

}
