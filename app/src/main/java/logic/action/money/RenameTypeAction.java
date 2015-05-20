package logic.action.money;

import database.operator.MoneyKeeper;
import logic.ListenerManager;
import logic.action.AbstractAction;
import type.MoneyType;

public class RenameTypeAction extends AbstractAction {
	
	public RenameTypeAction() {
		super("rename type");
	}
	
	String past,now;
	
	public void setValue(String past,String now){
		this.past=past;
		this.now=now;
	}

	@Override
	public void run() {
		MoneyKeeper keeper=(MoneyKeeper) data.getData("money");
		MoneyType money=(MoneyType) keeper.getItem(past);
		money.setType(now);
		keeper.update(past, money);
		ListenerManager.setOKMessage();
	}

}
