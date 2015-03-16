package type;

import java.util.Vector;

public class Type implements TypeInterface {
	
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
	public String getExtraMessage(String title){
		for (int i=0;i<extra.size();i++){
			if (extra.get(i).getTitle().equals(title)) return extra.get(i).getMessage();
		}
		return new String();
	}

	@Override
	public String format() {
		String ans=new String();
		for (int i=0;i<extra.size();i++){
			ans+=extra.get(i).format();
		}
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
}
