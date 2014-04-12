package com.AcidSpaceCompany.AcidSpaceFighting;

public class WorldLocal extends World {

    public WorldLocal() {
        super();
    }

    public void update(float deltaTime) {
        super.update(deltaTime);

        if (!activatedByUserModels.isEmpty()) {
            float[] actived = new float[activatedByUserModels.size()];
            int num = 0;
            for (int i : activatedByUserModels) {
                actived[num] = i;
                num++;
            }

            activeModels.clear();
            syncActivation(actived);
            activatedByUserModels.clear();
        }
        else
            syncActivation(new float[]{-1});
    }

}