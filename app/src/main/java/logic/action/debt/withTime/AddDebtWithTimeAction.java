package logic.action.debt.withTime;

import java.util.Date;

import logic.action.debt.AddDebtAction;

public abstract class AddDebtWithTimeAction extends AddDebtAction {

	Date time;
	
	public void setTime(Date time){
		this.time=time;
	}

}
