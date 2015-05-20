package logic.action.detail;

import database.operator.MoneyKeeper;
import type.MoneyType;

public class DetailSaveMoneyAction extends WriteDetailAction {
	
	public void saveMoney(String name){
		MoneyType money=getMoney(name);
		saveBaseMessage(money);
	}

	private void saveBaseMessage(MoneyType money) {
		detail.addExtra("past money name", money.getType());
		detail.addExtra("past money value", money.getValue()+"");
	}

	private MoneyType getMoney(String name) {
		MoneyKeeper keeper=(MoneyKeeper) data.getData("money");
		return (MoneyType) keeper.getItem(name);
	}

}
