package web;

import java.util.Vector;

public class Node {
	public int port;
	public String path,savePath;
	public boolean download;
	public long length,part;
	
	public String toString(){
		String ans=new String();
		ans+="[port]\n"+port+"\n";
		ans+="[path]\n"+path+"\n";
		ans+="[savePath]\n"+savePath+"\n";
		ans+="[length]\n"+length+"\n";
		ans+="[part]\n"+part+"\n";
		if (download){
			ans+="[download]\n";
		}else{
			ans+="[upload]\n";
		}
		return ans;
	}
	
	public void solve(String message){
		char[] a=message.toCharArray();
		Vector <String> ans=new Vector<String>();
		String buffer=new String();
		for (int i=0;i<a.length;i++){
			if (a[i]=='\n'){
				ans.add(buffer);
				buffer=new String();
			}else{
				buffer+=a[i];
			}
		}
		
		port=Integer.valueOf(ans.get(1));
		path=ans.get(3);
		savePath=ans.get(5);
		length=Long.valueOf(ans.get(7));
		part=Long.valueOf(ans.get(9));
		if (ans.get(10).equals("[download]")){
			download=true;
		}else{
			download=false;
		}
	}
}
