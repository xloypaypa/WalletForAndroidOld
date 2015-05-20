package database.operator;

import type.Type;

public interface DataKeeper {
	public boolean isThisKeeper(String keeper);
	public String getKeeperName();
	public void add(Type type);
	public void loadData();
	public void releaseData();
	public void clean();
}
