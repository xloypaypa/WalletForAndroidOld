package com.example.xlo.walletforandroid;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import net.client.Client;
import net.server.Server;
import net.type.Node;

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
                port=server.getPort();
                SettingFragment.port.setText("port: " + port);
                server.listen();
            }
        }.start();
    }

    public void stopServer(){
        new Thread(){
            public void run(){
                try {
                    Client client;
                    client=new Client(ip, port);

                    Node node;
                    node=new Node();

                    node.setCommand("shutDown");
                    node.setPath("");
                    node.setPort(0);
                    node.setSavePath("");
                    client.connect();
                    client.send(node);
                }catch (Exception e) {
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
