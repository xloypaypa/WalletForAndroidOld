package database.viewer;

import java.util.Vector;

import database.operator.TypeKeeper;
import logic.Logic;
import type.Type;

public class DataViewer extends Logic {
	
	protected TypeKeeper keeper;
	
	public void loadData(String name){
		keeper=(TypeKeeper) data.getData(name);
	}
	
	public Vector<Type> getAllItem(){
		return keeper.getAllItem();
	}
	
}
