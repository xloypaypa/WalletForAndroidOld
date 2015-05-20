package database.operator;

import type.Type;

public interface IDDataKeeper extends DataKeeper {
	public void delete(String id);
	public boolean itemExist(String id);
	public Type getItem(String id);
	public void update(String id, Type type);
}
