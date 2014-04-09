package com.AcidSpaceCompany.AcidSpaceFighting.Network;

import com.AcidSpaceCompany.AcidSpaceFighting.GUI.HUD.HUD;

import java.io.IOException;
import java.net.*;

import java.util.ArrayList;


public class Server {

    private ArrayList<ServerConnection> clients;

    public void sendMessage(String s) {
       for (ServerConnection c: clients)
           c.sendMessage(s);
    }

    public Server(int port){
        new Thread(() -> {
            clients=new ArrayList<>();
            ServerSocket server;
            try {
                server = new ServerSocket(port);
                while (true) {
                    Socket client = server.accept();
                    ServerConnection cl = new ServerConnection(client);
                    clients.add(cl);
                    cl.setOnInputEvent(() -> {
                        System.out.println("[Server] Incoming message: " + cl.getLastInputMessage());
                        for (ServerConnection c : clients)
                            c.sendMessage("[" + client.getInetAddress() + "] " + cl.getLastInputMessage());
                    });
                    cl.setOnCloseEvent(() -> {
                        HUD.showMessage("Player disconnected!", client.getInetAddress().toString());
                        System.out.println("[Server] Disconnected client: " + client.getLocalAddress());
                        clients.remove(cl);
                    });
                    HUD.showMessage("New player!", client.getInetAddress().toString());
                    System.out.println("[Server] Got client: "+client.getInetAddress());



                }     }
            catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }).start();
    }

    public static void main(String[] args) throws IOException {
        new Server(1234);
    }

}