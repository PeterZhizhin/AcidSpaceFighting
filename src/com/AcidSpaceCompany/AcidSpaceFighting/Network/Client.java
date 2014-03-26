package com.AcidSpaceCompany.AcidSpaceFighting.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

class Client {

    public static void main(String[] args) {

        try {

            Socket clientSocket;
            clientSocket = new Socket("127.0.0.1", 1234);
            BufferedReader inu = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            AtomicBoolean isWorking=new AtomicBoolean(true);

            new Thread(() -> {
                String fuser;
                try {
                    while ((fuser = inu.readLine()) != null) {

                        out.println(fuser);

                    }
                out.close();
                in.close();
                inu.close();
                clientSocket.close();
                    isWorking.set(false);
                    System.out.println("Stop client");
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }).start();

            new Thread(() -> {

                try {
                    while (isWorking.get()) {

                        String fserver = in.readLine();
                        if (fserver==null) break;
                        System.out.println(fserver);

                    }
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }).start();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}