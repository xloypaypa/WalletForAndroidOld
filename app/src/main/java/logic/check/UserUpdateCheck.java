package logic.check;

import java.util.Date;

import database.operator.UserUpdateOperator;
import type.UserUpdateTime;
import logic.event.AbstractSteep;

public class UserUpdateCheck extends AbstractSteep {

	public UserUpdateCheck() {
		super("user update");
	}

	@Override
	public boolean action() {
		UserUpdateOperator operator = (UserUpdateOperator) data.getData("user update");
		UserUpdateTime userUpdateTime = operator.getSetting();
		return userUpdateTime.needUpdate(new Date());
	}

}
