package type;

import java.util.Vector;

public class Type implements TypeInterface {
	
	String id; 
	Vector <ExtraType> extra=new Vector<ExtraType>();
	
	public Vector <ExtraType> getExtra(){
		return new Vector <ExtraType>(extra);
	}
	
	public void addExtra(String title,String message){
		ExtraType et=new ExtraType(title,message);
		extra.addElement(et);
	}
	public boolean extraExist(String title){
		for (int i=0;i<extra.size();i++){
			if (extra.get(i).getTitle().equals(title)) return true;
		}
		return false;
	}
	
	public void removeExtra(String title){
		int pos=getIndex(title);
		if (pos!=-1){
			extra.remove(pos);
		}
	}
	
	public String getExtraMessage(String title){
		int pos=getIndex(title);
		if (pos!=-1) return extra.get(pos).getMessage();
		else return new String();
	}
	
	private int getIndex(String title){
		for (int i=0;i<extra.size();i++){
			if (extra.get(i).getTitle().equals(title)) return i;
		}
		return -1;
	}

	@Override
	public String format() {
		String ans=new String();
		for (int i=0;i<extra.size();i++){
			ans+=extra.get(i).format();
		}
		return ans;
	}
	
	public String getTypeMessage(String typeName){
		String ans=new String();
		ans+="[begin]\r\n";
		ans+="[type name]\r\n";
		ans+=typeName+"\r\n";
		ans+="[type item]\r\n";
		ans+=this.getTypeNumber()+"\r\n";
		return ans;
	}

	@Override
	public String getTypeMessage() {
		String ans=new String();
		ans+="[begin]\r\n";
		ans+="[type name]\r\n";
		ans+="type\r\n";
		ans+="[type item]\r\n";
		ans+=this.getTypeNumber()+"\r\n";
		return ans;
	}

	@Override
	public int getTypeNumber() {
		return extra.size()*2;
	}

	@Override
	public void solveTypeMessage(Vector<String> message) {
		extra=new Vector<ExtraType>();
		ExtraType now;
		for (int i=0;i<message.size();i++){
			if(message.get(i).equals("[extra title]")){
				now=new ExtraType();
				now.setTitle(message.get(i+1));
				now.setMessage(message.get(i+3));
				extra.add(now);
			}
		}
	}

	@Override
	public String getAllMessage() {
		return this.getTypeMessage()+this.format()+"[end]\r\n";
	}
	
	public String getTypeID(){
		return this.id;
	}
}
