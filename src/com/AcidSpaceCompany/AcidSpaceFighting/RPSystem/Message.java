package com.AcidSpaceCompany.AcidSpaceFighting.RPSystem;

public class Message {

    private String title;
    private String about;


    public String getTitle() {
        return title;
    }

    public String getText() {
        return about;
    }

    public Message(String name, String about){
        title=name;
        this.about=about;
    }

    public void print() {
        System.out.println("[Achive] Print: "+title+" "+about);
    }

}
