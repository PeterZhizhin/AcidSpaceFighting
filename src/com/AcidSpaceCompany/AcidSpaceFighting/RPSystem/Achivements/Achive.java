package com.AcidSpaceCompany.AcidSpaceFighting.RPSystem.Achivements;

public class Achive {

    private String title;
    private String about;
    private int iconNumber;

    public Achive(String name, String about, int icon){
       title=name;
       this.about=about;
       iconNumber=icon;
    }

    public void print() {
        System.out.println("[Achive] Print: "+title+" "+about);
    }


}
