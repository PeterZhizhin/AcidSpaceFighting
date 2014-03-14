package com.AcidSpaceCompany.AcidSpaceFighting.RPSystem.Achivements;

public class Achive {

    private String title;
    private String about;


    public String getTitle() {
        return title;
    }

    public String getText() {
        return about;
    }

    public Achive(String name, String about){
       title=name;
       this.about=about;
    }

    public void print() {
        System.out.println("[Achive] Print: "+title+" "+about);
    }


}
