package database.viewer;

import java.util.Vector;

import database.operator.TypeOperator;
import logic.Logic;
import type.Type;

public class DataViewer extends Logic {
	
	protected TypeOperator keeper;
	
	public void loadData(String name){
		keeper=(TypeOperator) data.getData(name);
	}
	
	public Vector<Type> getAllItem(){
		return keeper.getAllItem();
	}
	
}
