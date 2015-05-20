package logic.check;

import database.operator.ReasonKeeper;
import logic.ListenerManager;
import logic.event.AbstractSteep;
import type.ReasonTreeNodeType;

public class TreeReasonLoopCheck extends AbstractSteep {
	
	String from,to;
	
	public TreeReasonLoopCheck() {
		super("tree reason loop");
	}
	
	public void setValue(String from,String to){
		this.from=from;
		this.to=to;
	}

	@Override
	public boolean action() {
		if (to.equals("root")) return true;
		
		if (from.equals(to)) {
			ListenerManager.setErrorMessage("The father of node shouldn't be itself.");
			ListenerManager.UIAction();
			return false;
		}
		
		ReasonKeeper keeper=(ReasonKeeper) data.getData("reason");
		
		ReasonTreeNodeType now=(ReasonTreeNodeType) keeper.getItem(to);
		if (now.getFather().equals("root")) return true;
		now=(ReasonTreeNodeType) keeper.getItem(now.getFather());
		while (true){
			if (now.getName().equals(from)){
				ListenerManager.setErrorMessage("Reason shouldn't have loop.");
				ListenerManager.UIAction();
				return false;
			}
			else if (now.getFather().equals("root")) break;
			now=(ReasonTreeNodeType) keeper.getItem(now.getFather());
		}
		return true;
	}

}
