package logic.process.backup;

import database.operator.DetailKeeper;
import logic.process.AbstractProcess;

public abstract class AbstractBackup extends AbstractProcess {

	public AbstractBackup(String processName) {
		super(processName);
	}

	@Override
	public void process() {
		this.backup();
		
		DetailKeeper keeper=(DetailKeeper) data.getData("detail");
		keeper.removeLastDetail();
	}
	
	public abstract void backup();

}
