package com.AcidSpaceCompany.AcidSpaceFighting.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Connection {

    private ArrayList<String> messages = new ArrayList<>();
    private String lastInput;
    private Runnable event;
    private Runnable close;
    AtomicBoolean isWorking = new AtomicBoolean(true);

    public boolean getIsWorking() {
        return isWorking.get();
    }

    public void setOnCloseEvent(Runnable r) {
        close = r;
    }

    public void setOnInputEvent(Runnable r) {
        event = r;
    }

    public String getLastInputMessage() {
        return lastInput;
    }

    public void sendMessage(String s) {
        messages.add(s);
    }

    public void startWorking(BufferedReader in, PrintWriter out) throws IOException {
        new Thread(() -> {
            try {
                String input;
                while ((input = in.readLine()) != null) {
                    lastInput = input;
                    event.run();
                    Thread.sleep(5);
                }
            } catch (IOException e) {
                System.err.println("[Connection] Error pt1 : " + e);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                in.close();
            } catch (IOException e) {
                System.err.println("[Connection] Error pt 2: " + e);
            }
            out.close();
            isWorking.set(false);
            if (close != null) close.run();
        }).start();


        new Thread(() -> {
            try {

                while (isWorking.get()) {

                    for (int i = 0; i < messages.size(); i++) {
                        out.println(messages.get(0));
                        messages.remove(0);
                    }

                    Thread.sleep(5);
                }
            } catch (InterruptedException e) {
                System.err.println("[ClientConnection] Error pt4: " + e);
            }
        }).start();
    }
}
