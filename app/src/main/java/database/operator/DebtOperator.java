package database.operator;

import type.DebtType;
import type.Type;
import database.password.DataBase;

public class DebtOperator extends IDTypeOperator {

	public DebtOperator() {
		super("debt");
	}

	protected void loadDataBase() {
		DataBase ans=new DataBase() {
			@Override
			public Type getNewType() {
				return new DebtType();
			}
		};
		ans.setAimFile("/"+username+"/"+keeperName+".txt");
		ans.setPassword(password);
		this.db=ans;
	}

}
