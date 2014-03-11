package com.AcidSpaceCompany.AcidSpaceFighting.RPSystem.Achivements;

import java.util.ArrayList;

//it IS arraylist
public class AchiveBase {

    public ArrayList<Achive> items;

    public void add(Achive i) {
        items.add(i);
    }

    public void remove(int num) {
        items.remove(num);
    }

    public Achive get(int num) {
        return items.get(num);
    }

    public int getSize() {
        return items.size();
    }

    public AchiveBase(){
        items=new ArrayList<Achive>();
    }

}
