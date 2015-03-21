package web.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Vector;

import web.Node;
import database.HHD;

public class FileClient extends Thread {
	String ip;
	Node need;
	Socket client;
	public void setNeed(Node node){
		this.need=node;
	}
	
	@Override
	public void run(){
		boolean flag=true;
		int t=Client.chance;
		while (flag){
			try {
				client=new Socket(ip, need.port);
				if (!need.download){
					int len=(int) Math.min(Client.fileSize, need.length-need.part*Client.fileSize);
					byte[] file=HHD.readByte(need.path, need.part*Client.fileSize, len);
					
					OutputStream out=client.getOutputStream();
					out.write(file);
					out.flush();
					out.close();
				}else{
					InputStream in=client.getInputStream();
					byte[] now=new byte[64];
					Vector <Byte> ans=new Vector<Byte>();
					while (true){
						int len=in.read(now);
						if (len==-1) break;
						for (int i=0;i<len;i++) ans.add(now[i]);
					}
					
					byte[] file=new byte[ans.size()];
					for (int i=0;i<file.length;i++) file[i]=ans.get(i);
					
					if (need.part==0) HHD.cleanFile(need.savePath);
					HHD.addByte(need.savePath, file);
					in.close();
				}
				client.close();
				flag=false;
			} catch (IOException e) {
				System.out.println("file "+e.getMessage());
				t--;
				if (t==0) break;
			}
		}
	}
}
