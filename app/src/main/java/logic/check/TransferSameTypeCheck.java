package logic.check;

import logic.ListenerManager;
import logic.event.AbstractSteep;

public class TransferSameTypeCheck extends AbstractSteep {
	
	String from, to;
	
	public TransferSameTypeCheck() {
		super("transfer same type");
	}
	
	public void setValue(String from, String to){
		this.from = from;
		this.to = to;
	}

	@Override
	public boolean action() {
		if (from.equals(to)){
			ListenerManager.setErrorMessage("From type and aim type should not be same!");
			ListenerManager.UIAction();
			return false;
		}
		return true;
	}

}
