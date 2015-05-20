package logic.process.backup.reason;

import logic.action.reason.AddReasonAction;
import logic.action.reason.ReasonExpenditureAction;
import logic.action.reason.ReasonIncomeAction;
import logic.process.backup.AbstractBackup;

public class RemoveReasonBackup extends AbstractBackup {
	
	public RemoveReasonBackup() {
		super("remove reason");
	}

	@Override
	public void backup() {
		AddReasonAction ara=new AddReasonAction();
		ara.setValue(detail.getReason());
		ara.run();
		
		ReasonIncomeAction ria=new ReasonIncomeAction();
		ria.setValue(detail.getReason(), Double.valueOf(detail.getExtraMessage("past reason income")));
		ria.run();
		
		ReasonExpenditureAction rea=new ReasonExpenditureAction();
		rea.setValue(detail.getReason(), Double.valueOf(detail.getExtraMessage("past reason expenditure")));
		rea.run();
	}

}
