package logic.check;

import logic.ListenerManager;
import logic.event.AbstractSteep;


public class NameCheck extends AbstractSteep {
	
	String value;
	
	public NameCheck() {
		super("name");
	}
	
	public void setName(String name){
		this.value=name;
	}

	@Override
	public boolean action() {
		if (value.length()==0){
			ListenerManager.setNeedInputError("type name");
			ListenerManager.UIAction();
			return false;
		}else if (value.contains("[")){
			ListenerManager.setLeftHalfBracketError();
			ListenerManager.UIAction();
			return false;
		}
		return true;
	}

}
