package logic.wallet;

import java.util.*;

import database.*;
import type.*;

public class Detail extends Wallet {
	public DetailType getLastDetail(){
		DetailType ans=new DetailType();
		if (allDetail.size()==0) return ans;
		else return allDetail.get(allDetail.size()-1);
	}
	public Vector <DetailType> getRangeDetail(Calendar from,Calendar to){
		Vector <DetailType> ans=new Vector<DetailType>();
		for (int i=0;i<allDetail.size();i++){
			DetailType now=allDetail.get(i);
			if (from.before(now.getTime())&&to.after(now.getTime())){
				ans.add(now);
			}
		}
		return ans;
	}
	public Vector <MoneyHistoryType> getHistoricalType(){
		return getHistoricalType(allDetail);
	}
	public Vector <MoneyHistoryType> getHistoricalType(Vector<DetailType> detail){
		Vector <MoneyHistoryType> ans=new Vector<MoneyHistoryType>();
		for (int i=0;i<detail.size();i++){
			DetailType now=detail.get(i);
			if (now.getEvent().equals("add money type")||now.getEvent().equals("pack money type")){
				MoneyHistoryType mht=new MoneyHistoryType(now.getType(), now.getValue());
				mht.addHistory(now);
				ans.addElement(mht);
			}else if (now.getEvent().equals("rename type")){
				int pos=getAimType(now.getExtraMessage("past name"),ans);
				ans.get(pos).addHistory(now);
			}else if (now.getEvent().equals("transfer")){
				int pos=getAimType(now.getType(), ans);
				DetailType tod=new DetailType();
				tod.setEvent("transfer in"); tod.setValue(now.getValue()); tod.setType(now.getType());
				tod.setTime(now.getTime().getTime());
				ans.get(pos).addHistory(tod);
				
				DetailType fromd=new DetailType();
				pos=getAimType(now.getExtraMessage("from type"), ans);
				fromd.setEvent("transfer out"); fromd.setValue(now.getValue()); fromd.setType(now.getType());
				fromd.setTime(now.getTime().getTime());
				ans.get(pos).addHistory(fromd);
			}else{
				int pos=getAimType(now.getType(),ans); if (pos==-1) continue;
				ans.get(pos).addHistory(now);
			}
		}
		return ans;
	}
	
	public void export(String path){
		new DetailDB(username, passWord).exportTxt(path);
	}
	
	public void pack(){
		HHD.cleanFile(new DetailDB(username, passWord).getAimPath());
		allDetail.clear();
		
		for (int i=0;i<allMoney.size();i++){
			DetailType now=new DetailType();
			now.setEvent("pack money type");
			now.setType(allMoney.get(i).getType());
			now.setValue(allMoney.get(i).getValue());
			super.setDetailTime(now);
			super.addDetail(now);
		}
		
		for (int i=0;i<allDebt.size();i++){
			DetailType now=new DetailType();
			now.setEvent("pack debt");
			now.setType("");
			now.setValue(allDebt.get(i).getValue());
			now.setTime(allDebt.get(i).getLastUpdateTime().getTime());
			now.addExtra("pack rate", allDebt.get(i).getRate().getRate()+"");
			now.addExtra("pack rate type", allDebt.get(i).getRate().getType());
			super.addDetail(now);
		}
	}
	
	protected int getAimType(String type, Vector <MoneyHistoryType> history){
		for (int i=0;i<history.size();i++){
			if (history.get(i).getName().equals(type)) return i;
		}
		return -1;
	}
	
}
