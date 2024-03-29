package database.operator;

import type.MoneyType;
import type.Type;
import database.password.DataBase;

public class MoneyOperator extends IDTypeOperator {
	
	public MoneyOperator() {
		super("money");
	}

	protected void loadDataBase() {
		DataBase ans=new DataBase() {
			@Override
			public Type getNewType() {
				return new MoneyType();
			}
		};
		ans.setAimFile("/"+username+"/"+keeperName+".txt");
		ans.setPassword(password);
		this.db=ans;
	}

}
