package web.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import web.Node;

public class Server {
	public static int fileSize;
	ServerSocket server;
	FileMessageServer fms;
	FileServer fs;
	int port;
	public Server(int port){
		this.port=port;
		buildServer();
	}
	private void buildServer() {
		while (true){
			try {
				server=new ServerSocket(port);
				break;
			} catch (IOException e) {
				port=(int) Math.round(Math.random()*(65534-1025)+1025);
			}
		}
	}
	
	public int getPort(){
		return port; 
	}
	
	public void listen(){
		while (true){
			try {
				Socket socket=server.accept();
				
				InputStream in=socket.getInputStream();
				String ans=new String();
				byte[] now=new byte[64];
				while (true){
					int len=in.read(now);
					if (len==-1) break;
					ans+=new String(now,0,len);
				}
				Node node=new Node();
				node.solve(ans);
				in.close();
				socket.close();
				
				if (node.part>=0) {
					fs=new FileServer();
					fs.setNeed(node);
					fs.start();
				}
				
				if (node.part==-1&&node.download){
					fms=new FileMessageServer();
					fms.setNeed(node);
					fms.start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean isAlive(){
		return fs.isAlive();
	}
}
