package database.password;

import database.HHD;
import type.Type;
import type.UserUpdateTime;

public class UserUpdateDataBase extends PasswordDataBase {
	
	@Override
	public void addItem(Type type) {
		HHD.writeFile(aimPath, type.format(), passWord);
	}

	@Override
	public Type getNewType() {
		return new UserUpdateTime();
	}

}
