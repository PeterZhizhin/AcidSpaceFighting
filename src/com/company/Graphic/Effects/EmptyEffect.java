package com.company.Graphic.Effects;

import com.company.Graphic.Posteffect;

public class EmptyEffect implements Posteffect {


    public EmptyEffect() {
    }

    @Override
    public void draw() {
    }

    @Override
    public void update(float deltaTime) {
    }

    @Override
    public boolean noNeedMore() {
        return true;
    }
}
