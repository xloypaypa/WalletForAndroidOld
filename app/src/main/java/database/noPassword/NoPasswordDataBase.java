package database.noPassword;

import java.util.Vector;

import type.Type;
import database.AbstractDataBase;
import database.HHD;

public abstract class NoPasswordDataBase extends AbstractDataBase {
	
	@Override
	public Vector<Type> load() {
		Vector <Type> ans=new Vector <>();
		Vector <Vector <String>> file=this.loadNotEncryptedFile(aimPath);
		
		for (int i=0;i<file.size();i++){
			Type mu=getNewType();
			mu.solveTypeMessage(file.get(i));
			ans.add(mu);
		}
		return ans;
	}
	
	@Override
	public void addItem(Type type) {
		HHD.addWriteFile(aimPath, type.getAllMessage());
	}
	
	public Vector<Vector<String>> loadNotEncryptedFile(String path){
		Vector <String> file=HHD.readFile(aimPath);
		Vector < Vector <String> > ans=new Vector<Vector<String>>();
		Vector <String> message=new Vector<String>();
		for (int i=0;i<file.size();i++){
			if (file.get(i).equals("[end]")){
				ans.add(message);
				message=new Vector<String>();
			}else if (file.get(i).equals("[begin]")){
				message=new Vector<String>();
			}else{
				message.add(file.get(i));
			}
		}
		return ans;
	}

}
