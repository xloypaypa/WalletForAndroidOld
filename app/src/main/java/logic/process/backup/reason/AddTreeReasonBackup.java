package logic.process.backup.reason;

import logic.action.reason.RemoveTreeReasonAction;
import logic.process.backup.AbstractBackup;

public class AddTreeReasonBackup extends AbstractBackup {
	
	public AddTreeReasonBackup() {
		super("add tree reason");
	}

	@Override
	public void backup() {
		RemoveTreeReasonAction rtra=new RemoveTreeReasonAction();
		rtra.setValue(detail.getReason());
		rtra.run();
	}

}
