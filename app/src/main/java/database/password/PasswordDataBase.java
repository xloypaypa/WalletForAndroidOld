package database.password;

import java.util.Vector;

import type.Type;
import database.AbstractDataBase;
import database.HHD;

public abstract class PasswordDataBase extends AbstractDataBase {

	protected String passWord;
	
	@Override
	public Vector<Type> load() {
		Vector <Type> ans=new Vector <>();
		Vector <Vector <String>> file=this.loadFile(aimPath);
		
		for (int i=0;i<file.size();i++){
			Type aim=getNewType();
			aim.solveTypeMessage(file.get(i));
			ans.add(aim);
		}
		return ans;
	}
	
	@Override
	public void addItem(Type type) {
		HHD.addWriteFile(aimPath, type.getAllMessage(), passWord);
	}
	
	public void setPassword(String passWord){
		this.passWord=passWord;
	}
	
	protected Vector<Vector<String>> loadFile(String path){
		Vector <String> file=HHD.readFile(aimPath, passWord);
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
