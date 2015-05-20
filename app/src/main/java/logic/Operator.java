package logic;

import java.util.Date;

import logic.logic.BackupLogic;
import logic.logic.CostLogic;
import logic.logic.DebtLogic;
import logic.logic.DoDetailLogic;
import logic.logic.ReasonLogic;
import logic.logic.TreeReasonLogic;
import logic.logic.TypeLogic;
import logic.logic.UserLogic;
import database.HHD;
import database.operator.DebtKeeper;
import database.operator.DetailKeeper;
import database.operator.MoneyKeeper;
import database.operator.ReasonKeeper;
import database.operator.TreeReasonKeeper;
import database.operator.UserData;
import database.operator.UserPublicData;

public class Operator extends Logic {
	
	private static TypeLogic type;
	private static CostLogic cost;
	private static ReasonLogic reason;
	private static DebtLogic debt;
	private static BackupLogic backup;
	private static DoDetailLogic dodetail;
	
	public static void login(String name,String pass){
		if (UserLogic.login(name, pass)){
			data=new UserData();
			data.addDataKeeper(new MoneyKeeper());
			data.addDataKeeper(new DebtKeeper());
			data.addDataKeeper(new DetailKeeper());
			
			type=new TypeLogic();
			cost=new CostLogic();
			debt=new DebtLogic();
			backup=new BackupLogic();
			dodetail=new DoDetailLogic();
			
			if (UserPublicData.getUserReason(name).equals("tree")){
				reason=new TreeReasonLogic();
				data.addDataKeeper(new TreeReasonKeeper());
			}else{
				reason=new ReasonLogic();
				data.addDataKeeper(new ReasonKeeper());
			}
			
			data.loadAllData();
		}
	}
	
	public static void register(String name,String pass,String repeat,String reason){
		UserLogic.register(name, pass, repeat, reason);
	}
	
	public static void addMoneyType(String name){
		type.addType(name);
	}
	
	public static void renameMoneyType(String past,String name){
		type.renameType(past, name);
	}
	
	public static void removeMoneyType(String name){
		type.removeType(name);
	}
	
	public static void income(String type,double value,String reason){
		cost.income(type, value,reason);
	}
	
	public static void expenditure(String type,double value,String reason){
		cost.expenditure(type, value,reason);
	}
	
	public static void transfer(String from, String to, double value){
		cost.transfer(from, to, value);
	}
	
	public static void addReason(String name){
		reason.addReason(name);
	}
	
	public static void renameReason(String past,String name){
		reason.renameReason(past, name);
	}
	
	public static void removeReason(String name){
		reason.removeReason(name);
	}
	
	public static void addTreeReason(String name,String father,double min,double max,int rank){
		((TreeReasonLogic)reason).addReason(name, father, min, max, rank);
	}
	
	public static void renameTreeReason(String past,String name,String father,double min,double max,int rank){
		((TreeReasonLogic)reason).renameReason(past, name, father, min, max, rank);
	}
	
	public static void addBorrowing(String credior,double value,String moneyType,Date deadline,double rate,String rateType){
		debt.addBorrowing(credior, value, moneyType, deadline, rate, rateType);
	}
	
	public static void repayBorrowing(int id,double value,String moneyType){
		debt.repayBorrowing(id, value, moneyType);
	}
	
	public static void addLoan(String credior,double value,String moneyType,Date deadline,double rate,String rateType){
		debt.addLoan(credior, value, moneyType, deadline, rate, rateType);
	}
	
	public static void repayLoan(int id,double value,String moneyType){
		debt.repayLoan(id, value, moneyType);
	}
	
	public static void backup(){
		backup.runProcess();
	}
	
	public static void doDetail(String path){
		dodetail.setPath(path);
	}
	
	public static void export(String path){
		HHD.cleanFile(path+"/detail.txt");
		DetailKeeper keeper=(DetailKeeper) data.getData("detail");
		keeper.export(path+"/detail.txt");
	}
	
	public static void exportExcel(String path){
		HHD.cleanFile(path+"/detail.xls");
		DetailKeeper keeper=(DetailKeeper) data.getData("detail");
		keeper.exportExcel(path+"/detail.xls");
	}
	
}
