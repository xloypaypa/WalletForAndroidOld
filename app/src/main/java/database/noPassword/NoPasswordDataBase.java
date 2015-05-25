package database.noPassword;

import java.util.Vector;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import type.Type;
import database.AbstractDataBase;
import database.HHD;

public abstract class NoPasswordDataBase extends AbstractDataBase {
	
	@Override
	public Vector<Type> load() {
		Vector <Type> ans=new Vector <>();
		Vector<String> file = HHD.readFile(aimPath);
		
		for (int i=0;i<file.size();i++){
			if (file.get(i).length()==0) continue;
			try {
				Element now = DocumentHelper.parseText(file.get(i)).getRootElement();
				Type mu=getNewType();
				mu.solveTypeMessage(now);
				ans.add(mu);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		return ans;
	}
	
	@Override
	public void addItem(Type type) {
		HHD.addWriteFile(aimPath, type.format());
	}

}
