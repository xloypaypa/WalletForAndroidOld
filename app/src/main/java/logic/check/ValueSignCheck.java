package logic.check;

import logic.ListenerManager;
import logic.event.AbstractSteep;

public class ValueSignCheck extends AbstractSteep {
	
	double value;
	
	public ValueSignCheck() {
		super("value sign");
	}
	
	public void setValue(double value){
		this.value=value;
	}

	@Override
	public boolean action() {
		if (value<0){
			ListenerManager.setErrorMessage("The value should be more than zero.");
			ListenerManager.UIAction();
			return false;
		}
		else return true;
	}

}
