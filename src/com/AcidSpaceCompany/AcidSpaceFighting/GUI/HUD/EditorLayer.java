package com.AcidSpaceCompany.AcidSpaceFighting.GUI.HUD;

import com.AcidSpaceCompany.AcidSpaceFighting.GUI.Form;
import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Camera;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.Model;
import com.AcidSpaceCompany.AcidSpaceFighting.World;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class EditorLayer {

    private static boolean isDragged;
    private static boolean isContainsActive;//Входит ли перетаскиваемая модель в комплексную активную
    private static Model draggedModel;
    private static Form q;

    private static void mousePressed(Point pressedMousePosition) {
        //Получаем позицию события в мировой СК
        Point eventPosition = Camera.translateWindowWorld(pressedMousePosition);
        //Теперь определяем, нажали мы на какой-то элемент корабля или же нет.
        Model pressed = World.getModel(eventPosition);
        if (pressed!=null && (!pressed.getIsComplex() | (isContainsActive=pressed.equals(World.getPlayerShip()))))
        {
            isDragged=true;
            draggedModel = pressed.getIsComplex() ? pressed.getModelUnderPoint(eventPosition) : pressed;
        }
    }

    private static void mouseReleased() {
        if (isDragged)
        {
            if (isContainsActive)
                World.getPlayerShip().recalculateModels();
            else
                if (World.getPlayerShip().isNeededToConnect(draggedModel)) {
                    World.getPlayerShip().add(draggedModel);
                    World.getPlayerShip().additionsFinished();
                }

            isDragged=false;
            draggedModel=null;
        }
    }

    private static void updateMouseButtons() {
        //Получаем событие нажатия левой кнопки мыши
        while (Mouse.next())
            //Событие ЛКМ
            if (Mouse.getEventButton()==0)
                if (Mouse.getEventButtonState())
                    //Кнопку нажали
                    mousePressed(new Point(Mouse.getEventX(), Display.getHeight()-Mouse.getEventY()));
                else
                    //Кнопку отжали
                    mouseReleased();
    }

    public static void update() {
        updateMouseButtons();
        if (isDragged)      {
            draggedModel.moveGeometricModel(
                    new Point(
                            Camera.untranslateDistance(Mouse.getDX()),
                            Camera.untranslateDistance(-Mouse.getDY())
                    ));
        }
        q.update();
    }

    public static void draw() {
         q.draw();
    }

    public static void init() {
        q=new EditorForm();
    }
}
