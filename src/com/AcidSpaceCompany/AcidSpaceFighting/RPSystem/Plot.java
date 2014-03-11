package com.AcidSpaceCompany.AcidSpaceFighting.RPSystem;

public class Plot {

    public static void init() {

        Condition c = new Condition() {
            public boolean getResult() {
                return DataWrapper.getX()>10000 && !PlotBase.getConditionWasTrueInPast(0);
            }
        };

        Event e = new Event() {
            public void activate() {
                DataWrapper.addAchive(0);
            }
        };

        PlotBase.addEventAndCondition(e, c);

        System.out.println("[Plot] I nitialised");
    }

}
