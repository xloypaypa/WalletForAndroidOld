package database.operator;

import database.IDDataBase;
import type.Type;

public abstract class IDTypeKeeper extends TypeKeeper implements IDDataKeeper {
	
	public IDTypeKeeper(String keeperName) {
		super(keeperName);
	}

	@Override
	public void delete(String id) {
		int pos=getPos(id);
		if (pos==-1) return ;
		allType.remove(pos);
		((IDDataBase) db).removeItem(id);
	}

	@Override
	public void update(String id, Type type) {
		int pos=getPos(id);
		if (pos==-1) return ;
		
		allType.setElementAt(type, pos);
		((IDDataBase) db).updateItem(id, type);
	}

	@Override
	public boolean itemExist(String id) {
		return getPos(id)!=-1;
	}

	@Override
	public Type getItem(String id) {
		int pos=getPos(id);
		if (pos!=-1){
			return allType.get(pos);
		}else{
			return null;
		}
	}
	
	protected int getPos(String id){
		for (int i=0;i<allType.size();i++){
			if (allType.get(i).getTypeID().equals(id)) return i;
		}
		return -1;
	}

}
