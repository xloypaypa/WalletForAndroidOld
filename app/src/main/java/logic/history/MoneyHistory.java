package logic.history;

import java.util.Vector;

import type.DetailType;
import type.MoneyHistoryType;

public class MoneyHistory extends History {
	protected int getAimType(String type, Vector <MoneyHistoryType> history){
		for (int i=0;i<history.size();i++){
			if (history.get(i).getName().equals(type)) return i;
		}
		return -1;
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
				tod.setTime(now.getTime());
				ans.get(pos).addHistory(tod);
				
				DetailType fromd=new DetailType();
				pos=getAimType(now.getExtraMessage("from type"), ans);
				fromd.setEvent("transfer out"); fromd.setValue(now.getValue()); fromd.setType(now.getType());
				fromd.setTime(now.getTime());
				ans.get(pos).addHistory(fromd);
			}else{
				int pos=getAimType(now.getType(),ans); if (pos==-1) continue;
				ans.get(pos).addHistory(now);
			}
		}
		return ans;
	}
	
	public void update(){
		moneyhistory=getHistoricalType();
	}
}
