package com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Effects;

public interface Effect {

    public void draw();
    public void update(float deltaTime);
    public boolean noNeedMore();
    public void destroy();
    public int getEfectType();

}
