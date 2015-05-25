package logic.process.backup;

import database.operator.DetailOperator;
import logic.process.AbstractProcess;

public abstract class AbstractBackup extends AbstractProcess {

	public AbstractBackup(String processName) {
		super(processName);
	}

	@Override
	public void process() {
		this.backup();
		
		DetailOperator keeper=(DetailOperator) data.getData("detail");
		keeper.removeLastDetail();
	}
	
	public abstract void backup();

}
