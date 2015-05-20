package logic.check;

import java.util.Date;

import logic.ListenerManager;
import logic.event.AbstractSteep;

public class AfterTimeChecker extends AbstractSteep {
	
	Date time,now;
	
	public AfterTimeChecker() {
		super("after time");
	}
	
	public void setValue(Date now,Date time){
		this.time=time;
		this.now=now;
	}

	@Override
	public boolean action() {
		if (time.before(now)){
			ListenerManager.setErrorMessage("This time before now time!");
			ListenerManager.UIAction();
			return false;
		}
		return true;
	}

}
