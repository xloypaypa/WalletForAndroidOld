package logic.process.backup.money;

import logic.action.money.ExpenditureAction;
import logic.action.money.IncomeAction;
import logic.process.backup.AbstractBackup;

public class TransferBackup extends AbstractBackup {
	
	public TransferBackup() {
		super("transfer");
	}

	@Override
	public void backup() {
		ExpenditureAction ea=new ExpenditureAction();
		ea.setValue(detail.getType(), detail.getValue());
		ea.run();
		
		IncomeAction ia=new IncomeAction();
		ia.setValue(detail.getExtraMessage("from type"), detail.getValue());
		ia.run();
	}

}
