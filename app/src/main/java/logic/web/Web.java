package logic.web;

import java.io.File;
import java.util.Vector;

import logic.Logic;
import net.client.Client;
import type.DetailType;
import net.type.Node;
import type.Type;
import database.AbstractDataBase;
import database.HHD;
import database.operator.DetailOperator;
import database.operator.UserPublicData;
import database.viewer.DataViewer;

public class Web extends UserPublicData {
	
	static String[] myPath;
	static String[] phonePath;
	
	static String ip;
	static int port;
	
	public static void getPath(){
		loadMyPath("temp");
		
		phonePath=new String[]{"/storage/sdcard0/Wallet/"+username+"/money.txt",
				"/storage/sdcard0/Wallet/"+username+"/debt.txt",
				"/storage/sdcard0/Wallet/"+username+"/detail.txt",
				"/storage/sdcard0/Wallet/"+username+"/reason.txt"};
	}
	
	public static void clearTemp(){
		HHD.deleteFolder(AbstractDataBase.root+"/temp");
	}
	
	public static void setIP(String ip){
		Web.ip=ip;
	}
	
	public static void setPort(int port){
		Web.port=port;
	}
	
	public static void saveToTemp(){
		loadMyPath("temp");
		download();
	}
	
	public static void autoChooseDownOrUpLoad(){
		File folder=new File(AbstractDataBase.root+"/temp");
		if (!folder.exists()) folder.mkdirs();
		
		getPath();
		download();
		
		DetailOperator detail=new DetailOperator(); detail.load(AbstractDataBase.root+"/"+"temp"+"/detail.txt", password);
		Vector<Type> phone=detail.getAllItem();
		DataViewer data=new DataViewer(); data.loadData("detail");
		Vector<Type> allDetail=data.getAllItem();
		
		loadMyPath(username);
		if (allDetail.size()==0){
			copyTempFile();
		}else if (phone.size()==0) {
			upload();
		}else {
			DetailType phoneLast=(DetailType) phone.lastElement();
			DetailType nowLast=(DetailType) allDetail.lastElement();
			if (nowLast.getTime().after(phoneLast.getTime())){
				upload();
			}else{
				copyTempFile();
			}
		}
		clearTemp();
		Logic.data.reloadAllData();
	}

	private static void copyTempFile() {
		if (HHD.fileExiste(AbstractDataBase.root+"/temp/money.txt")){
			HHD.copyFile(AbstractDataBase.root+"/temp/money.txt", myPath[0]);
		}else{
			HHD.cleanFile(myPath[0]);
		}
		
		if (HHD.fileExiste(AbstractDataBase.root+"/temp/debt.txt")){
			HHD.copyFile(AbstractDataBase.root+"/temp/debt.txt", myPath[1]);
		}else{
			HHD.cleanFile(myPath[1]);
		}
		
		if (HHD.fileExiste(AbstractDataBase.root+"/temp/detail.txt")){
			HHD.copyFile(AbstractDataBase.root+"/temp/detail.txt", myPath[2]);
		}else{
			HHD.cleanFile(myPath[2]);
		}
		
		if (HHD.fileExiste(AbstractDataBase.root+"/temp/reason.txt")){
			HHD.copyFile(AbstractDataBase.root+"/temp/reason.txt", myPath[3]);
		}else{
			HHD.cleanFile(myPath[3]);
		}
	}
	
	public static boolean upload(){
		Client client=new Client(ip, port);
		Client.chance=5;
		
		for (int i=0;i<4;i++){
			try {
				Node node,ret;
				node=new Node(); ret=new Node();
				File file=new File(myPath[i]);
				node.setCommand("upload");
				node.setFrom(0);
				node.setTo(file.length());
				node.setLength(file.length());
				node.setPath(myPath[i]);
				node.setSavePath(phonePath[i]);
				node.setPort(0);
				
				client.connect();
				ret=client.send(node);
				
				node.setPort(ret.getPort());
				client.startFileClient(node);
				
				while (!client.isEnd());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public static boolean download(){
		Client client=new Client(ip, port);
		Client.chance=5;
		
		for (int i=0;i<4;i++){
			try {
				Node node,ret;
				node=new Node(); ret=new Node();
				
				node.setCommand("get file length");
				node.setFrom(0);
				node.setTo(0);
				node.setPort(0);
				node.setLength(0);
				node.setSavePath(myPath[i]);
				node.setPath(phonePath[i]);
				
				client.connect();
				ret=client.send(node);
				
				node.setCommand("download");
				node.setFrom(0);
				node.setTo(ret.getLength());
				node.setLength(ret.getLength());
				
				client.connect();
				ret=client.send(node);
				
				node.setPort(ret.getPort());
				client.startFileClient(node);
				
				while (!client.isEnd());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	protected static void loadMyPath(String name) {
		myPath=new String[]{AbstractDataBase.root+"/"+name+"/money.txt",
				AbstractDataBase.root+"/"+name+"/debt.txt",
				AbstractDataBase.root+"/"+name+"/detail.txt",
				AbstractDataBase.root+"/"+name+"/reason.txt"};
	}
}
