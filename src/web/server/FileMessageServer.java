package web.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import web.Node;
import database.HHD;

public class FileMessageServer extends Thread {
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
			need.length=HHD.getFileLength(need.path);
			
			OutputStream out=socket.getOutputStream();
			out.write(need.toString().getBytes());
			out.flush();
			out.close();
			socket.close();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
