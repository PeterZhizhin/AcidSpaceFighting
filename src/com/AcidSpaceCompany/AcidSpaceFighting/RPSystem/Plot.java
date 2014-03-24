package com.AcidSpaceCompany.AcidSpaceFighting.RPSystem;

import com.AcidSpaceCompany.AcidSpaceFighting.RPSystem.Achivements.Achive;

public class Plot {

    public static void init() {
        PlotBase.init();

        Condition c = () -> DataWrapper.getX()>10000 && !PlotBase.getConditionWasTrueInPast(0);
        Event e = () -> {
            DataWrapper.addAchive(new Achive("FAR FAR", "Your x > 10k!"));
            DataWrapper.enableEditor();
        };
        PlotBase.addEventAndCondition(e, c);


        Condition c2 = () -> !PlotBase.getConditionWasTrueInPast(1);
        Event e2 = () ->  DataWrapper.showMessage(new Message("Заголовок", "Мелкий текст!"));
        PlotBase.addEventAndCondition(e2, c2);
    }

}
