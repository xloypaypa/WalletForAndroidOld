package database.viewer;

import java.util.Vector;

import database.operator.DetailKeeper;
import type.DetailType;
import type.MoneyHistoryType;
import type.Type;
import logic.Logic;

public class MoneyHistoryViewer extends Logic {
	
	public Vector <MoneyHistoryType> getHistoricalType(){
		DetailKeeper keeper=(DetailKeeper) data.getData("detail");
		return getHistoricalType(keeper.getAllItem());
	}
	public Vector <MoneyHistoryType> getHistoricalType(Vector<Type> detail){
		Vector <MoneyHistoryType> ans=new Vector<MoneyHistoryType>();
		for (int i=0;i<detail.size();i++){
			DetailType now=(DetailType) detail.get(i);
			if (now.getEvent().equals("add type")||now.getEvent().equals("pack money type")){
				MoneyHistoryType mht=new MoneyHistoryType(now.getType(), now.getValue());
				mht.addHistory(now);
				ans.addElement(mht);
			}else if (now.getEvent().equals("rename type")){
				int pos=getAimType(now.getExtraMessage("past type name"),ans);
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
	
	private int getAimType(String type, Vector <MoneyHistoryType> history){
		for (int i=0;i<history.size();i++){
			if (history.get(i).getName().equals(type)) return i;
		}
		return -1;
	}

}
