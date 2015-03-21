package web.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import web.Node;
import database.HHD;

public class FileServer extends Thread {
	Node need;
	ServerSocket server;
	public void setNeed(Node node){
		this.need=node;
	}
	
	@Override
	public void run(){
		try {
			server=new ServerSocket(need.port);
			Socket socket=server.accept();
			if (need.download){
				int len=(int) Math.min(Server.fileSize, need.length-need.part*Server.fileSize);
				byte[] file=HHD.readByte(need.path, need.part*Server.fileSize, len);
				
				OutputStream out=socket.getOutputStream();
				out.write(file);
				out.flush();
				out.close();
			}else{
				InputStream in=socket.getInputStream();
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
			socket.close();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
