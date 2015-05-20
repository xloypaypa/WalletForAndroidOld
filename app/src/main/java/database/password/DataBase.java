package database.password;

import java.util.Vector;

import database.HHD;
import database.IDDataBase;
import type.Type;

public abstract class DataBase extends PasswordDataBase implements IDDataBase {

	@Override
	public void removeItem(String id) {
		Vector <Type> now=this.load();
		String ans=new String();
		for (int i=0;i<now.size();i++){
			if (now.get(i).getTypeID().equals(id)){
				continue;
			}else{
				ans+=now.get(i).getAllMessage()+"\r\n";
			}
		}
		HHD.writeFile(aimPath, ans,passWord);
	}

	@Override
	public void updateItem(String id, Type type) {
		Vector <Type> now=this.load();
		String ans=new String();
		for (int i=0;i<now.size();i++){
			if (now.get(i).getTypeID().equals(id)){
				ans+=type.getAllMessage()+"\r\n";
			}else{
				ans+=now.get(i).getAllMessage()+"\r\n";
			}
		}
		HHD.writeFile(aimPath, ans,passWord);
	}
	
}
