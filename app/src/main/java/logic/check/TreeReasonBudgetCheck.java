package logic.check;

import database.operator.TreeReasonKeeper;
import logic.ListenerManager;
import logic.event.AbstractSteep;
import type.ReasonTreeNodeType;

public class TreeReasonBudgetCheck extends AbstractSteep {
	
	String name; double value;
	
	public TreeReasonBudgetCheck() {
		super("tree reason budget");
	}
	
	public void setValue(String name,double value){
		this.name=name;
		this.value=value;
	}

	@Override
	public boolean action() {
		if (name.equals("root")) return false;
		TreeReasonKeeper keeper=(TreeReasonKeeper) data.getData("reason");
		ReasonTreeNodeType now=(ReasonTreeNodeType) keeper.getItem(name);
		while (true){
			if (now.getExpenditure()+value > now.getMax()){
				ListenerManager.setErrorMessage("This reason is over-expenditure.");
				ListenerManager.UIAction();
				return false;
			}
			if (now.getFather().equals("root")) break;
			now=(ReasonTreeNodeType) keeper.getItem(now.getFather());
		}
		return true;
	}

}
