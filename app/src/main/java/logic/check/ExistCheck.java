package logic.check;

import database.operator.IDDataOperator;
import logic.ListenerManager;
import logic.event.AbstractSteep;

public class ExistCheck extends AbstractSteep {
	
	String id;
	IDDataOperator keeper;
	
	public ExistCheck() {
		super("exist");
	}
	
	public void setID(String id){
		this.id=id;
	}
	
	public void setKeeper(IDDataOperator keeper){
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
