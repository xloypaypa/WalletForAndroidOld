package logic;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Vector;

import type.DetailType;
import web.Node;
import web.client.Client;
import database.DataBase;
import database.DebtDB;
import database.DetailDB;
import database.HHD;
import database.MoneyDB;
import database.ReasonDB;

public class Web extends User {
	
	static String[] myPath;
	static String[] phonePath;
	
	static String ip;
	static int port;
	
	private static int getPort(){
		ServerSocket ss;
		try {
			ss = new ServerSocket(0);
			int port=ss.getLocalPort();
			ss.close();
			return port;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static void getPath(){
		myPath=new String[]{new MoneyDB(username, passWord).getAimPath(),
				new DebtDB(username, passWord).getAimPath(),
				new DetailDB(username, passWord).getAimPath(),
				new ReasonDB(username, passWord).getAimPath()};
		
		phonePath=new String[]{"/storage/sdcard0/Wallet/"+username+"/user.txt",
				"/storage/sdcard0/Wallet/"+username+"/debt.txt",
				"/storage/sdcard0/Wallet/"+username+"/detail.txt",
				"/storage/sdcard0/Wallet/"+username+"/reason.txt"};
		
//		phonePath=new String[]{"E:/test_space/"+username+"/user.txt",
//				"E:/test_space/"+username+"/debt.txt",
//				"E:/test_space/"+username+"/detail.txt",
//				"E:/test_space/"+username+"/reason.txt"};
	}
	
	public static void clearTemp(){
		HHD.deleteFolder(DataBase.Root+"/temp");
	}
	
	public static void setIP(String ip){
		Web.ip=ip;
	}
	
	public static void setPort(int port){
		Web.port=port;
	}
	
	public static void saveToTemp(){
		myPath=new String[]{new MoneyDB("temp", passWord).getAimPath(),
				new DebtDB("temp", passWord).getAimPath(),
				new DetailDB("temp", passWord).getAimPath(),
				new ReasonDB("temp", passWord).getAimPath()};
		download();
	}
	
	public static void autoChooseDownOrUpLoad(){
		Vector<DetailType> phone=new DetailDB("temp", passWord).loadDetail();
		
		getPath();
		
		if (allDetail.size()==0){
			copyTempFile();
			return ;
		}else if (phone.size()==0) {
			upload();
			return ;
		}
		
		DetailType phoneLast=phone.lastElement();
		DetailType nowLast=allDetail.lastElement();
		if (nowLast.getTime().after(phoneLast.getTime())){
			upload();
		}else{
			copyTempFile();
		}
	}

	private static void copyTempFile() {
		if (HHD.fileExiste(DataBase.Root+"/temp/user.txt")){
			HHD.copyFile(DataBase.Root+"/temp/user.txt", myPath[0]);
		}else{
			HHD.cleanFile(myPath[0]);
		}
		
		if (HHD.fileExiste(DataBase.Root+"/temp/debt.txt")){
			HHD.copyFile(DataBase.Root+"/temp/debt.txt", myPath[1]);
		}else{
			HHD.cleanFile(myPath[1]);
		}
		
		if (HHD.fileExiste(DataBase.Root+"/temp/detail.txt")){
			HHD.copyFile(DataBase.Root+"/temp/detail.txt", myPath[2]);
		}else{
			HHD.cleanFile(myPath[2]);
		}
		
		if (HHD.fileExiste(DataBase.Root+"/temp/reason.txt")){
			HHD.copyFile(DataBase.Root+"/temp/reason.txt", myPath[3]);
		}else{
			HHD.cleanFile(myPath[3]);
		}
	}
	
	public static boolean upload(){
		Client client=new Client(ip, port);
		Client.chance=5; Client.fileSize=1000000;
		
		for (int i=0;i<4;i++){
			Node node=new Node();
			int part;
			node.download=false;
			node.path=myPath[i];
			node.length=HHD.getFileLength(myPath[i]);
			part=(int) (node.length/Client.fileSize); if (node.length%Client.fileSize==0) part--;
			node.savePath=phonePath[i];
			
			for (int j=0;j<=part;j++){
				node.part=j;
				node.port=getPort();
				try {
					client.connect();
					client.send(node);
				} catch (IOException e) {
					return false;
				}
				
				while (client.isAlive());
			}
		}
		return true;
	}
	
	public static boolean download(){
		Client client=new Client(ip, port);
		Client.chance=5; Client.fileSize=1000000;
		
		for (int i=0;i<4;i++){
			Node node=new Node();
			int part;
			
			node.download=true;
			node.part=-1;
			
			node.path=phonePath[i];
			node.savePath=myPath[i];
			
			node.port=getPort();
			
			try {
				client.connect();
				node=client.send(node);
			} catch (IOException e1) {
				return false;
			}
			part=(int) (node.length/Client.fileSize); if (node.length%Client.fileSize==0) part--;
			
			
			for (int j=0;j<=part;j++){
				node.part=j;
				node.port=getPort();
				try {
					client.connect();
					client.send(node);
					while (client.isAlive());
				} catch (IOException e) {
					return false;
				}
				
				while (client.isAlive());
			}
		}
		return true;
	}
}
