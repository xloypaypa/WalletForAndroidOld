package database;

import type.IDType;

public interface IDDataBase {
	
	public abstract void removeItem(String id);
	
	public abstract void updateItem(String id, IDType type);

}
