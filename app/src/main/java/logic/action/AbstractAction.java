package logic.action;

import logic.event.AbstractSteep;

public abstract class AbstractAction extends AbstractSteep {
	
	public AbstractAction(String actionName) {
		super(actionName);
	}
	
	@Override
	public boolean action() {
		run();
		return true;
	}
	
	public abstract void run();
}
