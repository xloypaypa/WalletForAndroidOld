package database;

import java.util.Vector;

import type.DetailType;

public class DetailDB extends DataBase {
	public DetailDB(String name, String passWord) {
		super(name, passWord);
		this.aimPath=new String(Root+"/"+this.name+"/"+detailPath);
	}
	public Vector <DetailType> loadDetail(){
		Vector <DetailType> ans=new Vector <DetailType>();
		Vector <Vector <String>> all=this.loadFile();
		for (int i=0;i<all.size();i++){
			DetailType now=new DetailType();
			now.solveTypeMessage(all.get(i));
			ans.add(now);
		}
		return ans;
	}
	
	public void addDetail(DetailType detail){
		HHD.addWriteFile(aimPath, detail.getAllMessage(), passWord);
	}
	
	public void backupDetail(){
		Vector <DetailType> a=this.loadDetail();
		HHD.cleanFile(this.aimPath);
		for (int i=0;i<a.size()-1;i++){
			this.addDetail(a.get(i));
		}
	}
	public void changeTypeName(String type,String newName){
		Vector <DetailType> a=new Vector<DetailType>();
		a=this.loadDetail();
		for (int i=0;i<a.size();i++){
			if (a.get(i).getType().equals(type)){
				a.get(i).setType(newName);
			}
		}
		
		HHD.cleanFile(this.aimPath);
		for (int i=0;i<a.size();i++){
			this.addDetail(a.get(i));
		}
	}
	public void exportTxt(String to){
		Vector <DetailType> a=this.loadDetail();
		for (int i=0;i<a.size();i++){
			HHD.addWriteFile(to, a.get(i).getAllMessage()+"\r\n");
		}
	}
}
