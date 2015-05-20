package logic.process.backup.reason;

import logic.action.reason.RenameReasonAction;
import logic.process.backup.AbstractBackup;

public class RenameReasonBackup extends AbstractBackup {
	
	public RenameReasonBackup() {
		super("rename reason");
	}

	@Override
	public void backup() {
		RenameReasonAction rra=new RenameReasonAction();
		rra.setValue(detail.getReason(), detail.getExtraMessage("past name"));
		rra.run();
	}

}
