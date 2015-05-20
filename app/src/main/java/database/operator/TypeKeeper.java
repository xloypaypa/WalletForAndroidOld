package database.operator;

import java.util.Vector;

import database.AbstractDataBase;
import type.Type;

public abstract class TypeKeeper extends UserPublicData implements DataKeeper {
	
	String keeperName;
	
	protected Vector <Type> allType=new Vector<>();
	protected AbstractDataBase db;
	
	public TypeKeeper(String keeperName) {
		this.keeperName=keeperName;
		loadDataBase();
	}
	
	public Vector <Type> getAllItem(){
		return allType;
	}
	
	final public String getDataFilePath(){
		return db.getAimFile();
	}
	
	@Override
	public void clean(){
		releaseData();
		db.clean();
	}

	@Override
	public void add(Type type) {
		allType.addElement(type);
		db.addItem(type);
	}

	@Override
	public void loadData() {
		allType=db.load();
	}

	@Override
	public void releaseData() {
		allType.removeAllElements();
	}
	
	@Override
	public boolean isThisKeeper(String keeper) {
		if (keeper.equals(keeperName)) return true;
		else return false;
	}
	
	@Override
	public String getKeeperName(){
		return keeperName;
	}
	
	protected abstract void loadDataBase();

}
