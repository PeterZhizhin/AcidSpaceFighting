package com.AcidSpaceCompany.AcidSpaceFighting.GUI.HUD;

import com.AcidSpaceCompany.AcidSpaceFighting.GUI.Form;
import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Camera;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.TextureDrawer;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.GeometricModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.Model;
import com.AcidSpaceCompany.AcidSpaceFighting.OurWorld;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class EditorLayer {

    private static boolean isDragged;
    private static boolean isContainsActive;//Входит ли перетаскиваемая модель в комплексную активную
    private static Model draggedModel;
    private static Form q;
    private static GeometricModel rollPlate;
    private static Point[] rollArea;

    private static void mousePressed(Point pressedMousePosition) {
        Point eventPosition = Camera.untranslatePoint(pressedMousePosition);
        Model pressed = OurWorld.getModel(eventPosition);
        if (!rollPlate.contains(new Point(Mouse.getX(), Display.getHeight() - Mouse.getY()))) {
        if (pressed!=null && (!pressed.getIsComplex() | (isContainsActive=pressed.equals(OurWorld.getPlayerShip())))) {
            isDragged=true;
            if (draggedModel!=null)
                draggedModel.unselect();
            draggedModel = pressed.getIsComplex() ? pressed.getModelUnderPoint(eventPosition) : pressed;
            draggedModel.select();
        }
        else {
            if (draggedModel!=null) {
                draggedModel.unselect();
                draggedModel = null;
            }
        }
        }
    }

    private static void mouseReleased() {
        if (isDragged) {
            if (isContainsActive)
                OurWorld.getPlayerShip().recalculateModels();
            else
                if (OurWorld.getPlayerShip().isNeededToConnect(draggedModel)) {
                    OurWorld.getPlayerShip().add(draggedModel);
                    OurWorld.getPlayerShip().additionsFinished();
                }
            isDragged=false;
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

    public static void deactivate()
    {
        if (draggedModel!=null) draggedModel.unselect();
        System.out.println("[EditorLayer] Exiting editor");
    }

    public static void update() {
        updateMouseButtons();
        Point p = new Point(Mouse.getX(), Display.getHeight() - Mouse.getY());
        if (draggedModel != null && rollPlate.contains(p)
                && Mouse.isButtonDown(0)) {
            draggedModel.setAngle((float) Point.getAngle(
                    new Point(draggedModel.getCenter().x + 100f, draggedModel.getCenter().y),
                    draggedModel.getCenter(),
                    Camera.untranslatePoint(p)
            ));
        }

        else
        if (isDragged)      {
            draggedModel.moveGeometricModel(
                    new Point(
                            Camera.untranslateDistance(Mouse.getDX()),
                            Camera.untranslateDistance(-Mouse.getDY())
                    ));
        }
        q.update();

        if (draggedModel!=null) {
            Point center=Camera.getTranslatedPoint(draggedModel.getCenter().x,
                    draggedModel.getCenter().y);
            double dxs1=Math.cos(draggedModel.getAngle()-0.4f)*20f;
            double dxe1=dxs1*20f;
            double dys1=Math.sin(draggedModel.getAngle()-0.4f)*20f;
            double dye1=dys1*20f;
            double dxs2=Math.cos(draggedModel.getAngle()+0.4f)*20f;
            double dxe2=dxs2*20f;
            double dys2=Math.sin(draggedModel.getAngle()+0.4f)*20f;
            double dye2=dys2*20f;
            rollArea[0].set(center.x+(float)dxs1, center.y+(float)dys1);
            rollArea[1].set(center.x+(float)dxe1, center.y+(float)dye1);
            rollArea[2].set(center.x+(float)dxe2, center.y+(float)dye2);
            rollArea[3].set(center.x+(float)dxs2, center.y+(float)dys2);
        }
    }

    public static void draw() {
        if (draggedModel!=null) {
            TextureDrawer.drawRoundSegment(rollArea);
        }
         q.draw();
    }

    public static void init() {
        q=new EditorForm();
        rollArea=new Point[]{new Point(), new Point(), new Point(), new Point()};
        rollPlate=new GeometricModel(rollArea);
    }
}
