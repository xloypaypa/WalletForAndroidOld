package logic.action.money;

import database.operator.MoneyKeeper;
import logic.ListenerManager;
import logic.action.AbstractAction;
import type.MoneyType;

public class TransferAction extends AbstractAction {
	
	String from,to; double value;
	
	public TransferAction() {
		super("transfer");
	}
	
	public void setValue(String from,String to,double value){
		this.from=from;
		this.to=to;
		this.value=value;
	}

	@Override
	public void run() {
		MoneyKeeper keeper=(MoneyKeeper) data.getData("money");
		MoneyType m1=(MoneyType) keeper.getItem(from);
		MoneyType m2=(MoneyType) keeper.getItem(to);
		m1.setValue(m1.getValue()-value);
		m2.setValue(m2.getValue()+value);
		keeper.update(from, m1);
		keeper.update(to, m2);
		ListenerManager.setOKMessage();
	}

}
