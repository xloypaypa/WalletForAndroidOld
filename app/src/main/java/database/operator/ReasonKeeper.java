package database.operator;

import type.ReasonType;
import type.Type;
import database.password.DataBase;

public class ReasonKeeper extends IDTypeKeeper {

	public ReasonKeeper() {
		super("reason");
	}

	protected void loadDataBase() {
		DataBase ans=new DataBase() {
			@Override
			public Type getNewType() {
				return new ReasonType();
			}
		};
		ans.setAimFile("/"+username+"/"+keeperName+".txt");
		ans.setPassword(password);
		this.db=ans;
	}

}
