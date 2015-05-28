package database.operator;

import type.IDType;

public interface IDDataOperator extends DataOperator {
	public void delete(String id);
	public boolean itemExist(String id);
	public IDType getItem(String id);
	public void update(String id, IDType type);
}
