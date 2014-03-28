package com.AcidSpaceCompany.AcidSpaceFighting.Network;

import java.io.IOException;
import java.net.*;

import java.util.ArrayList;


public class Server {

    private ArrayList<ClientConnection> clients;

    public void sendMessage(String s) {
       for (ClientConnection c: clients)
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
                    ClientConnection cl = new ClientConnection(client);
                    clients.add(cl);
                    cl.setOnInputEvent(() -> {
                        System.out.println("[Server] Incoming message: " + cl.getLastInputMessage());
                        for (ClientConnection c : clients)
                            c.sendMessage("["+client.getInetAddress()+"] "+cl.getLastInputMessage());
                    });
                    cl.setOnCloseEvent(() -> {
                        System.out.println("[Server] Disconnected client: "+client.getLocalAddress());
                        clients.remove(cl);
                    });
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