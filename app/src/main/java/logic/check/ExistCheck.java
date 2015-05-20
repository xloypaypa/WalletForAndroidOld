package logic.check;

import database.operator.IDDataKeeper;
import logic.ListenerManager;
import logic.event.AbstractSteep;

public class ExistCheck extends AbstractSteep {
	
	String id;
	IDDataKeeper keeper;
	
	public ExistCheck() {
		super("exist");
	}
	
	public void setID(String id){
		this.id=id;
	}
	
	public void setKeeper(IDDataKeeper keeper){
		this.keeper=keeper;
	}

	@Override
	public boolean action() {
		if (keeper.itemExist(id)){
			ListenerManager.setExistentError("name");
			ListenerManager.UIAction();
			return false;
		}
		return true;
	}

}
