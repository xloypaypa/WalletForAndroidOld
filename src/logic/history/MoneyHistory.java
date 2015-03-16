package logic.history;

import logic.wallet.Detail;

public class MoneyHistory extends History {
	
	public void update(){
		moneyhistory=new Detail().getHistoricalType();
	}
}
