package logic.process.backup.reason;

import logic.action.reason.RemoveReasonAction;
import logic.process.backup.AbstractBackup;

public class AddReasonBackup extends AbstractBackup {
	
	public AddReasonBackup() {
		super("add reason");
	}

	@Override
	public void backup() {
		RemoveReasonAction rra=new RemoveReasonAction();
		rra.setValue(detail.getReason());
		rra.run();
	}

}
