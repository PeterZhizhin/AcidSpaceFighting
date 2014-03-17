package com.AcidSpaceCompany.AcidSpaceFighting;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Camera;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.ComplexModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.Model;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

/**
 * Created by peter on 16.03.14.
 */
public class CreativeMode {

    private static ComplexModel activeModel;
    private static Point previousCameraPosition;
    private static boolean isDragged;
    private static Model draggedModel;

    private static Point translateDeltaMouseWorld(Point dS)
    {
        return Camera.translateDSDisplayWorld(dS);
    }

    private static void mousePressed(Point pressedMousePosition)
    {
        //Получаем позицию события в мировой СК
        Point eventPosition = Camera.translateWindowWorld(pressedMousePosition);
        //Теперь определяем, нажали мы на какой-то элемент корабля или же нет.
        Model pressed = World.getModel(eventPosition);
        if (pressed!=null)
        {
            isDragged=true;
            draggedModel = pressed;
        }
    }

    private static void mouseReleased()
    {
        if (isDragged)
        {
            isDragged=false;
            draggedModel=null;
        }
    }

    private static void updateMouseButtons()
    {
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

    public static void updateCreativeMode()
    {
        updateMouseButtons();
        if (isDragged)
            draggedModel.moveGeometricModel(
                    translateDeltaMouseWorld(
                            new Point(Mouse.getDX(), Mouse.getDY())));
    }


    private static void activate(){
       //Устанавливаем модель для пересборки
        activeModel = World.getPlayerShip();
       //Получаем позицию камеры чтобы потом к ней вернуться (после отключения режима)
        previousCameraPosition = new Point(Camera.getX(), Camera.getY());
       //Перетаскиваем камеру к центру этой модели (ну а вдруг)
        Camera.setPosition(activeModel.getCenter());
    }
    private static void deactivate(){
        //Возвращаем камеру на то место, где она была до активации
        Camera.setPosition(previousCameraPosition);
        //Присоединяем к текущей комплексной модели
        //Обновляем текущую комплексную модель
    }
    public static void setCreativeMode(boolean state)
    {
        if (state)
            activate();
        else
            deactivate();
    }
}
