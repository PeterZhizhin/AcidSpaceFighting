package com.AcidSpaceCompany.AcidSpaceFighting.RPSystem.Items;

import java.util.ArrayList;

//it IS arraylist
public class Bag {

    public ArrayList<Item> items;

    public void add(Item i) {
        items.add(i);
    }

    public void remove(int num) {
        items.remove(num);
    }

    public Item get(int num) {
        return items.get(num);
    }

    public int getSize() {
        return items.size();
    }

    public Bag(){
        items=new ArrayList<Item>();
    }

}
