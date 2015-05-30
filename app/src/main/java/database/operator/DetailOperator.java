package database.operator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Vector;

import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import type.DetailType;
import type.IDType;
import type.Type;
import database.ExcelWriter;
import database.noPassword.NoPasswordDataBase;
import database.password.DetailDataBase;
import database.password.PasswordDataBase;

public class DetailOperator extends TypeOperator implements DataOperator {
	
	public DetailOperator() {
		super("detail");
	}
	
	@Override
	public void loadData() {
		super.loadData();
		this.sort();
	};

	@Override
	protected void loadDataBase() {
		DetailDataBase ans=new DetailDataBase() {
			@Override
			public Type getNewType() {
				return new DetailType();
			}
		};
		ans.setAimFile("/"+username+"/"+keeperName+".txt");
		ans.setPassword(password);
		this.db=ans;
	}
	
	public DetailType getLastDetail(){
		DetailType ans=new DetailType();
		if (allType.size()==0) return ans;
		else return (DetailType) allType.get(allType.size()-1);
	}
	
	public void removeLastDetail() {
		if (allType.size()==0) return ;
		
		allType.removeElementAt(allType.size()-1);
		((DetailDataBase) this.db).removeLastItem();
	}
	
	public Vector <DetailType> getRangeDetail(Date from,Date to){
		Vector <DetailType> ans=new Vector<DetailType>();
		for (int i=0;i<allType.size();i++){
			DetailType now=(DetailType) allType.get(i);
			if (from.before(now.getTime())&&to.after(now.getTime())){
				ans.add(now);
			}
		}
		return ans;
	}
	
	public void sort(){
		DetailType[] a = new DetailType[allType.size()];
		allType.toArray(a);
		Arrays.sort(a, 0, a.length, new Comparator<DetailType>(){
			@Override
			public int compare(DetailType o1, DetailType o2) {
				if (o1.getTime().before(o2.getTime())) return -1;
				else if (o1.getTime().after(o2.getTime())) return 1;
				else return 0;
			}
		});
		allType.removeAllElements();
		for (int i=0;i<a.length;i++){
			allType.addElement(a[i]);
		}
	}
	
	public void export(final String path){
		NoPasswordDataBase db=new NoPasswordDataBase() {
			@Override
			public IDType getNewType() {return null;}
			
			@Override
			public void addItem(Type type) {
				aimPath=path;
				super.addItem(type);
			}
		};
		
		for (int i=0;i<allType.size();i++){
			db.addItem(allType.get(i));
		}
	}
	
	public void saveBackup() {
		PasswordDataBase db = new PasswordDataBase() {
			@Override
			public IDType getNewType() {return null;}
		};
		db.setAimFile("/"+username+"/"+keeperName+".back");
		db.setPassword(password);
		for (int i=0;i<allType.size();i++) {
			DetailType now = (DetailType) allType.get(i);
			if (now.getEvent().equals("pack money type")) continue;
			db.addItem(now);
		}
	}
	
	public void exportExcel(String path){
		try {
			ExcelWriter ew=new ExcelWriter(path);
			int p=ew.createPage("detail");
			ew.setMergeCells(p, 0, 0, 4, 0);
			ew.setRowHeight(p, 0, 600);
			ew.setColumnWidth(p, 0, 15);
			ew.setColumnWidth(p, 1, 20);
			ew.setColumnWidth(p, 2, 15);
			ew.setColumnWidth(p, 3, 15);
			ew.setColumnWidth(p, 4, 50);
			WritableFont bold = new WritableFont(WritableFont.ARIAL,20,WritableFont.BOLD);
			WritableCellFormat titleFormate = new WritableCellFormat(bold);
			titleFormate.setAlignment(jxl.format.Alignment.CENTRE);
		    titleFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			ew.addCell(p, 0, 0, "all details table",titleFormate);
			ew.addCell(p, 0, 1, "time");
			ew.addCell(p, 1, 1, "event");
			ew.addCell(p, 2, 1, "money type");
			ew.addCell(p, 3, 1, "money value");
			ew.addCell(p, 4, 1, "reason");
			
			for (int i=0;i<allType.size();i++){
				DetailType now=(DetailType) allType.get(i);
				ew.addCell(p, 0, i+2, now.getTime().getTime());
				ew.addCell(p, 1, i+2, now.getEvent());
				ew.addCell(p, 2, i+2, now.getType());
				ew.addCell(p, 3, i+2, now.getValue());
				ew.addCell(p, 4, i+2, now.getReason());
			}
			
			ew.writeEnd();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void load(final String path){
		this.releaseData();
		NoPasswordDataBase db=new NoPasswordDataBase() {
			
			@Override
			public Type getNewType() {
				return new DetailType();
			}
			
			@Override
			public Vector<Type> load() {
				aimPath = path;
				return super.load();
			}
		};
		allType=db.load();
	}
	
	public void load(final String path, String password) {
		this.releaseData();
		PasswordDataBase db=new PasswordDataBase() {
			
			@Override
			public Type getNewType() {
				return new DetailType();
			}
			
			@Override
			public Vector<Type> load() {
				aimPath = path;
				return super.load();
			}
		};
		db.setPassword(password);
		allType=db.load();
	}

}
