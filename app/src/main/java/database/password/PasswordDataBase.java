package database.password;

import java.util.Vector;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import type.Type;
import database.AbstractDataBase;
import database.HHD;

public abstract class PasswordDataBase extends AbstractDataBase {

	protected String passWord;
	
	@Override
	public Vector<Type> load() {
		Vector <Type> ans=new Vector <>();
		Vector<String> file = HHD.readFile(aimPath, passWord);
		
		for (int i=0;i<file.size();i++){
			if (file.get(i).length()==0) continue;
			try {
				Element now = DocumentHelper.parseText(file.get(i)).getRootElement();
				Type aim=getNewType();
				aim.solveTypeMessage(now);
				ans.add(aim);
			} catch (DocumentException e) {
				System.out.println("error: "+file.get(i));
				e.printStackTrace();
			}
		}
		return ans;
	}
	
	@Override
	public void addItem(Type type) {
		HHD.addWriteFile(aimPath, type.format(), passWord);
	}
	
	public void setPassword(String passWord){
		this.passWord=passWord;
	}

}
