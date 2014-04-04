package com.AcidSpaceCompany.AcidSpaceFighting.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientConnection {

    private ArrayList<String> messages=new ArrayList<>();
    private String lastInput;
    private Runnable event;
    private Runnable close;
    AtomicBoolean isWorking=new AtomicBoolean(true);

    public boolean getIsWorking() {
         return isWorking.get();
    }

    public void setOnCloseEvent(Runnable r) {
        close=r;
    }

    public void setOnInputEvent(Runnable r) {
        event=r;
    }

    public String getLastInputMessage() {
        return lastInput;
    }

    public void sendMessage(String s) {
        messages.add(s);
    }

    public ClientConnection(Socket client) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        new Thread(() -> {
            try {
                String input;
                while ((input = in.readLine()) != null) {

                    lastInput=input;
                    event.run();

                }
            } catch (Exception e) {
                System.err.println("[ClientConnection] Error pt1 : "+e);
            }
            try {
                in.close();
            } catch (IOException e) {
                System.err.println("[ClientConnection] Error pt 2: "+e);
            }
            out.close();
            isWorking.set(false);
            close.run();
        }).start();


        new Thread(() -> {
            try {

                while (isWorking.get()) {

                    for (int i=0; i<messages.size(); i++)
                    {
                        out.println(messages.get(0));
                        messages.remove(0);
                    }

                }
            } catch (Exception e) {
                System.err.println("[ClientConnection] Error pt3: "+e);}
        }).start();
    }


}
