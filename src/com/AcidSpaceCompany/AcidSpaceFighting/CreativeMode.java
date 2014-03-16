package com.AcidSpaceCompany.AcidSpaceFighting;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.Graphic.Camera;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.ComplexModel;
import org.lwjgl.input.Mouse;

/**
 * Created by peter on 16.03.14.
 */
public class CreativeMode {

    private static ComplexModel activeModel;
    private static Point previousCameraPosition;

    private static void updateMouse()
    {
        //Получаем событие нажатия левой кнопки мыши
        //По этому событию находим модель для движения

    }

    public static void updateCreativeMode()
    {

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
    }
    public static void setCreativeMode(boolean state)
    {
        if (state)
            activate();
        else
            deactivate();
    }
}
