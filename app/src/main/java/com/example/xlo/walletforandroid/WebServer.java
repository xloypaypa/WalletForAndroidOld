package com.example.xlo.walletforandroid;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;

import web.Node;
import web.client.Client;
import web.server.Server;

/**
 * Created by LT on 2015/3/21.
 *
 */
public class WebServer extends Service {

    String ip;
    int port;

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public void runServer(){
        new Thread(){
            @Override
            public void run(){
                Server server=new Server(8899);
                Server.fileSize=1000000;
                port=server.getPort();
                SettingFragment.port.setText("port: "+port);
                server.listen();
            }
        }.start();
    }

    public void stopServer(){
        new Thread(){
            public void run(){
                Client client=new Client(ip, port);
                Client.chance=5; Client.fileSize=1000000;
                Node node=new Node();
                int part;
                node.path="shutdown";
                try {
                    client.connect();
                    client.send(node);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public int getPort(){
        return port;
    }

    public class MyBinder extends Binder {

        public WebServer getService(){
            return WebServer.this;
        }
    }
}
