package database;

import java.util.Vector;

import type.Type;

public abstract class AbstractDataBase {
	
	static public String root;
	protected String aimPath;
	
	public static String getTypeMessage(Vector <String> message){
		for (int i=0;i<message.size();i++){
			if (message.get(i).equals("[type name]")){
				return message.get(i+1);
			}
		}
		return null;
	}
	
	public void setAimFile(String file){
		this.aimPath=AbstractDataBase.root+"/"+file;
	}
	
	public String getAimFile(){
		return this.aimPath;
	}
	
	public void clean(){
		HHD.cleanFile(aimPath);
	}
	
	public abstract void addItem(Type type);
	
	public abstract Type getNewType();
	
	public abstract Vector <Type> load();
	
}
