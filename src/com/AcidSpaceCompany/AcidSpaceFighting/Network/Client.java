package com.AcidSpaceCompany.AcidSpaceFighting.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {

    private String messageForSending=null;
    private String lastInputString=null;
    private Runnable onInput;

    public void sendMessage(String s) {
        messageForSending=s;
    }

    public void setOnInputEvent(Runnable r) {
        onInput=r;
    }

    public String getLastInput() {
        return lastInputString;
    }

    public Client(String ip, int port) {

        try {

            Socket clientSocket;
            clientSocket = new Socket(ip, port);
            BufferedReader inu = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            AtomicBoolean isWorking=new AtomicBoolean(true);

            new Thread(() -> {
                try {
                    while (isWorking.get()) {
                        if (messageForSending!=null) {
                            out.println(messageForSending);
                            messageForSending = null;
                        }
                    }
                    out.close();
                    in.close();
                    inu.close();
                    clientSocket.close();
                    isWorking.set(false);
                    System.out.println("[Client] Stop client");
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }).start();

            new Thread(() -> {

                try {
                    while (isWorking.get()) {

                        lastInputString= in.readLine();
                        if (lastInputString!=null)
                        onInput.run();
                    }
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }).start();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Client("127.0.0.1", 1234);
    }

}