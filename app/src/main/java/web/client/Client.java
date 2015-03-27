package web.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import web.Node;

public class Client {
	public static int fileSize;
	public static int chance;
	Socket client;
	String ip;
	int port;
	FileMessageClient fmc;
	FileClient fc;
	
	public Client(String ip, int port){
		this.ip=ip;
		this.port=port;
	}
	
	public void connect() throws UnknownHostException, IOException{
		client=new Socket(ip, port);
	}
	
	public Node send(Node node) throws IOException{
		OutputStream out;
		out = client.getOutputStream();
		out.write(node.toString().getBytes());
		out.flush();
		out.close();
		client.close();
		
		if (node.part>=0){
			fc=new FileClient();
			fc.setNeed(node);
			fc.ip=this.ip;
			fc.start();
		}
		
		if (node.part==-1&&node.download){
			fmc=new FileMessageClient();
			fmc.setNeed(node);
			fmc.ip=this.ip;
			fmc.run();
			
			return fmc.need;
		}
		return node;
	}
	
	public boolean isAlive(){
		return fc.isAlive();
	}
}
