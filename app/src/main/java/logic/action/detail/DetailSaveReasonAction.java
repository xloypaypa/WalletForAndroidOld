package logic.action.detail;

import database.operator.ReasonOperator;
import type.ReasonType;

public class DetailSaveReasonAction extends WriteDetailAction {
	
	public void saveReason(String name){
		ReasonType reason = getReason(name);
		saveBaseMessage(reason);
	}

	protected ReasonType getReason(String name) {
		ReasonOperator keeper=(ReasonOperator) data.getData("reason");
		ReasonType reason=(ReasonType) keeper.getItem(name);
		return reason;
	}

	protected void saveBaseMessage(ReasonType reason) {
		detail.addExtra("past reason name", reason.getName());
		detail.addExtra("past reason income", reason.getIncome()+"");
		detail.addExtra("past reason expenditure", reason.getExpenditure()+"");
	}

}
