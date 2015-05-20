package database;

import type.Type;

public interface IDDataBase {
	
	public abstract void removeItem(String id);
	
	public abstract void updateItem(String id, Type type);

}
