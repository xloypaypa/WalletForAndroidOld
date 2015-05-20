package logic.check;

import logic.ListenerManager;
import logic.event.AbstractSteep;


public class TreeReasonMinMaxCheck extends AbstractSteep {
	
	double min,max;
	
	public TreeReasonMinMaxCheck() {
		super("tree reason min max");
	}
	
	public void setValue(double min, double max) {
		this.min=min;
		this.max=max;
	}

	@Override
	public boolean action() {
		if (min>max) {
			ListenerManager.setErrorMessage("Min expenditure should be lower than the max.");
			ListenerManager.UIAction();
			return false;
		}
		return true;
	}

}
