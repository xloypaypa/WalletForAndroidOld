package logic.process;

import type.DetailType;
import logic.Logic;

public abstract class AbstractProcess extends Logic {
	
	String processName;
	protected DetailType detail;
	
	public AbstractProcess(String processName) {
		this.processName=processName;
	}
	
	public boolean isThisProcess(String name) {
		if (this.processName.equals(name)) return true;
		else return false;
	}
	
	public void setDetail(DetailType detail) {
		this.detail=detail;
	}	
	
	public abstract void process();

}
