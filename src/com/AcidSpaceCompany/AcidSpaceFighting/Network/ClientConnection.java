package com.AcidSpaceCompany.AcidSpaceFighting.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientConnection {

    private String message=null;
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
        message=s;
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
                out.close();
                in.close();
                isWorking.set(false);
                close.run();

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }).start();


        new Thread(() -> {
            try {

                while (isWorking.get()) {

                    if (message!=null) {
                        out.println(message);
                        message=null;
                    }

                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }).start();
    }


}
