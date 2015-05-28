package logic.action.detail;

import java.util.Date;
import java.util.Vector;

import type.DetailType;
import type.MoneyType;
import type.Type;
import type.UserUpdateTime;
import database.operator.DetailOperator;
import database.operator.MoneyOperator;
import database.operator.ReasonOperator;
import database.operator.UserUpdateOperator;
import logic.action.AbstractAction;

public class UserUpdateAction extends AbstractAction {

	public UserUpdateAction() {
		super("user update");
	}

	@Override
	public void run() {
		ReasonOperator reasonOperator = (ReasonOperator) data.getData("reason");
		reasonOperator.updateAllReason();
		
		DetailOperator detailOperator = (DetailOperator) data.getData("detail");
		detailOperator.saveBackup();
		detailOperator.clean();
		
		MoneyOperator moneyOperator = (MoneyOperator) data.getData("money");
		Vector<Type> all = moneyOperator.getAllItem();
		for (int i=0;i<all.size();i++){
			MoneyType now = (MoneyType) all.get(i);
			DetailType detail = new DetailType();
			detail.setEvent("pack money type");
			detail.setReason("");
			detail.setTime(new Date());
			detail.setType(now.getType());
			detail.setValue(now.getValue());
			detailOperator.add(detail);
		}
		
		UserUpdateOperator userUpdateOperator = (UserUpdateOperator) data.getData("user update");
		UserUpdateTime updateTime = userUpdateOperator.getSetting();
		updateTime.getNextTime();
		userUpdateOperator.add(updateTime);
	}

}
