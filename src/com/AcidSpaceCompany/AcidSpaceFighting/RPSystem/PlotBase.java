package com.AcidSpaceCompany.AcidSpaceFighting.RPSystem;

import java.util.ArrayList;

public class PlotBase {

    //todo: нормальные названия методов

    private static ArrayList<Event> events=new ArrayList<>();
    private static ArrayList<Condition> conditions=new ArrayList<>();
    private static ArrayList<Boolean> conditionsHappended=new ArrayList<>();
    private static ArrayList<Integer> conditionToEventConnection=new ArrayList<>();

    public static void init()
    {
        events = new ArrayList<>();
        conditions = new ArrayList<>();
        conditionsHappended = new ArrayList<>();
        conditionToEventConnection = new ArrayList<>();
    }

    public static int addEvent(Event e) {
         events.add(e);
        return events.size()-1;
    }

    public static void addCondition(Condition e) {
        conditions.add(e);
        conditionsHappended.add(false);
    }

    public static void connectEventToLastAddedCondition(int eventNumber) {
        conditionToEventConnection.add(eventNumber);
    }

    public static void addEventAndCondition(Event e, Condition c) {
        addCondition(c);
        connectEventToLastAddedCondition(addEvent(e));
    }

    public static boolean getConditionWasTrueInPast(int conditionNumber) {
        return conditionsHappended.get(conditionNumber);
    }

    public static void reCheck() {
        for (int i=0; i<conditions.size(); i++) {
            if (conditions.get(i).getResult())
            {
                events.get(conditionToEventConnection.get(i)).activate();
                conditionsHappended.set(i, true);
            }
        }
    }




}
