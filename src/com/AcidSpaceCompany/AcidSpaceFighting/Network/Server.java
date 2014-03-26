package com.AcidSpaceCompany.AcidSpaceFighting.Network;

import java.io.IOException;
import java.net.*;

import java.util.ArrayList;


class Server {

    private ArrayList<ClientConnection> clients;

    public Server(int port) throws IOException {
        clients=new ArrayList<>();
        ServerSocket server = new ServerSocket(port);
        try {
            while (true) {
                Socket client = server.accept();
                System.out.println("Got client");
                ClientConnection cl = new ClientConnection(client);
                clients.add(cl);
                cl.setEvent(() -> {
                    for (ClientConnection c : clients)
                        c.sendMessage(cl.getLastInputMessage());
                });
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        new Server(1234);
    }

}