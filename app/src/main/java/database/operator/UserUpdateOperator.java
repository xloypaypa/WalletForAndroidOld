package database.operator;

import java.util.Date;

import type.UserUpdateTime;
import database.password.UserUpdateDataBase;

public class UserUpdateOperator extends TypeOperator implements DataOperator {

	public UserUpdateOperator() {
		super("user update");
	}
	
	public UserUpdateTime getSetting() {
		if (allType.size()==0) {
			UserUpdateTime userUpdateTime = new UserUpdateTime();
			userUpdateTime.getNextTime(new Date());
			add(userUpdateTime);
		}
		return (UserUpdateTime) this.allType.get(0);
	}

	@Override
	protected void loadDataBase() {
		UserUpdateDataBase ans = new UserUpdateDataBase();
		ans.setAimFile("/"+username+"/"+keeperName+".txt");
		ans.setPassword(password);
		this.db = ans;
	}

}
