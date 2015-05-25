package database.operator;

import type.Type;

public interface IDDataOperator extends DataOperator {
	public void delete(String id);
	public boolean itemExist(String id);
	public Type getItem(String id);
	public void update(String id, Type type);
}
