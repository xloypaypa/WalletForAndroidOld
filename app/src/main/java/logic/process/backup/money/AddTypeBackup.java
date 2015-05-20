package logic.process.backup.money;

import logic.action.money.RemoveTypeAction;
import logic.process.backup.AbstractBackup;

public class AddTypeBackup extends AbstractBackup {
	
	public AddTypeBackup() {
		super("add type");
	}

	@Override
	public void backup() {
		RemoveTypeAction rta=new RemoveTypeAction();
		rta.setType(detail.getType());
		rta.run();
	}

}
