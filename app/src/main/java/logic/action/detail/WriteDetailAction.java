package logic.action.detail;

import java.util.Date;

import database.operator.DetailOperator;
import type.DetailType;
import logic.action.AbstractAction;

public class WriteDetailAction extends AbstractAction {
	
	DetailType detail;
	
	public WriteDetailAction() {
		super("write detail");
		detail=new DetailType();
	}
	
	public void setValue(Date time,String event,String moneyType,double value,String reason){
		detail.setTime(time);
		detail.setEvent(event);
		detail.setType(moneyType);
		detail.setValue(value);
		detail.setReason(reason);
	}
	
	public void addExtra(String title,String message){
		detail.addExtra(title, message);
	}

	@Override
	public void run() {
		DetailOperator keeper=(DetailOperator) data.getData("detail");
		keeper.add(detail);
	}

}
