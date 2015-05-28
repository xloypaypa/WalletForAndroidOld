package database.noPassword;

import java.util.Vector;

import database.HHD;
import database.IDDataBase;
import type.IDType;
import type.Type;
import type.UserMessage;

public class PublicDataBase extends NoPasswordDataBase implements IDDataBase {
	
	public PublicDataBase(){
		this.aimPath=root+"/all user.txt";
	}

	@Override
	public void removeItem(String id) {
		Vector <Type> now=this.load();
		String ans=new String();
		for (int i=0;i<now.size();i++){
			if (((IDType) now.get(i)).getTypeID().equals(id)){
				continue;
			}else{
				ans+=now.get(i).format()+"\r\n";
			}
		}
		HHD.writeFile(aimPath, ans);
	}

	@Override
	public void updateItem(String id, IDType type) {
		Vector <Type> now=this.load();
		String ans=new String();
		for (int i=0;i<now.size();i++){
			if (((IDType) now.get(i)).getTypeID().equals(id)){
				ans+=type.format()+"\r\n";
			}else{
				ans+=now.get(i).format()+"\r\n";
			}
		}
		HHD.writeFile(aimPath, ans);
	}

	@Override
	public IDType getNewType() {
		return new UserMessage();
	}
}
