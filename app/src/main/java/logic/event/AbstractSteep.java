package logic.event;

import logic.Logic;

public abstract class AbstractSteep extends Logic {
	
	String className;
	
	public AbstractSteep(String checkName) {
		this.className=checkName;
	}
	
	public boolean isThisCheck(String name){
		if (name.equals(this.className)) return true;
		else return false;
	}
	
	public abstract boolean action();
	
}
