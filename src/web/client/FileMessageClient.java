package web.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import web.Node;

public class FileMessageClient extends Thread {
	String ip;
	Node need;
	public void setNeed(Node node){
		this.need=node;
	}
	
	public void run(){
		boolean flag=true;
		int t=Client.chance;
		while (flag){
			try {
				Socket socket=new Socket(ip,need.port);
				
				InputStream in=socket.getInputStream();
				String ans=new String();
				byte[] now=new byte[64];
				while (true){
					int len=in.read(now);
					if (len==-1) break;
					ans+=new String(now,0,len);
				}
				need=new Node();
				need.solve(ans);
				in.close();
				socket.close();
				flag=false;
			} catch (IOException e) {
				System.out.println("message "+e.getMessage());
				t--;
				if (t==0) break;
			}
		}
	}
}
