package database.operator;

import type.ReasonType;
import type.IDType;
import database.password.DataBase;

public class ReasonOperator extends IDTypeOperator {

	public ReasonOperator() {
		super("reason");
	}
	
	public void updateAllReason() {
		for (int i=0;i<allType.size();i++) {
			ReasonType now = (ReasonType) allType.get(i);
			now.update();
			update(now.getTypeID(), now);
		}
	}

	protected void loadDataBase() {
		DataBase ans=new DataBase() {
			@Override
			public IDType getNewType() {
				return new ReasonType();
			}
		};
		ans.setAimFile("/"+username+"/"+keeperName+".txt");
		ans.setPassword(password);
		this.db=ans;
	}

}
