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
		
		Vector <String> file=new Vector<String>();
		file=HHD.readFile(aimPath, passWord);
		
		return solveDetail(ans, file);
	}
	public static Vector<DetailType> solveDetail(Vector<DetailType> ans,
			Vector<String> file) {
		Vector <String> message=new Vector<String>();
		for (int i=0;i<file.size();i++){
			if (file.get(i).equals("[end]")){
				DetailType dt=new DetailType();
				dt.solveTypeMessage(message);
				ans.add(dt);
			}else if (file.get(i).equals("[begin]")){
				message=new Vector<String>();
			}else{
				message.add(file.get(i));
			}
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
