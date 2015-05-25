package database.password;

import java.util.Vector;

import database.HHD;
import type.Type;

public abstract class DetailDataBase extends PasswordDataBase {
	
	public void removeLastItem(){
		Vector <Type> all=this.load();
		if (all.size()==0) return ;
		
		String ans=new String();
		for (int i=0;i<all.size()-1;i++) {
			ans+=all.get(i).format();
		}
		HHD.writeFile(aimPath, ans, passWord);
	}
	
}
