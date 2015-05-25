package database.operator;

import java.util.Vector;

public class UserData {
	Vector <DataOperator> allData=new Vector<>();
	
	public void addDataKeeper(DataOperator data){
		this.allData.addElement(data);
	}
	
	public void loadAllData(){
		for (int i=0;i<allData.size();i++){
			allData.get(i).loadData();
		}
	}
	
	public void cleanAllData(){
		for (int i=0;i<allData.size();i++){
			allData.get(i).clean();
		}
	}
	
	public void releaseAllData(){
		for (int i=0;i<allData.size();i++){
			allData.get(i).releaseData();
		}
	}
	
	public void reloadAllData(){
		for (int i=0;i<allData.size();i++){
			allData.get(i).releaseData();
			allData.get(i).loadData();
		}
	}
	
	public void loadData(String dataName){
		for (int i=0;i<this.allData.size();i++){
			if (this.allData.get(i).isThisKeeper(dataName)){
				allData.get(i).loadData();
			}
		}
	}
	
	public void releaseData(String dataName){
		for (int i=0;i<this.allData.size();i++){
			if (this.allData.get(i).isThisKeeper(dataName)){
				allData.get(i).releaseData();
			}
		}
	}
	
	public void reloadData(String dataName){
		for (int i=0;i<this.allData.size();i++){
			if (this.allData.get(i).isThisKeeper(dataName)){
				allData.get(i).releaseData();
				allData.get(i).loadData();
			}
		}
	}
	
	public void cleanData(String dataName){
		for (int i=0;i<this.allData.size();i++){
			if (this.allData.get(i).isThisKeeper(dataName)){
				allData.get(i).releaseData();
				allData.get(i).clean();
			}
		}
	}
	
	public DataOperator getData(String dataName){
		for (int i=0;i<this.allData.size();i++){
			if (this.allData.get(i).isThisKeeper(dataName)){
				return allData.get(i);
			}
		}
		return null;
	}
}
