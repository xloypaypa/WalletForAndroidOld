package database.operator;

import type.UserUpdateTime;
import database.password.UserUpdateDataBase;

public class UserUpdateOperator extends TypeOperator implements DataOperator {

	public UserUpdateOperator() {
		super("user update");
	}
	
	public UserUpdateTime getSetting() {
		if (allType.size()==0) {
			add(new UserUpdateTime());
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
