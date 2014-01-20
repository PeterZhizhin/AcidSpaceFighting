package com.company;

import com.company.Graphic.Background;
import com.company.Graphic.Camera;
import com.company.Graphic.GUI;

/**
 * User class which helps allows you to make update() draw() cycle easily
 * It has FPS inside title, and you should also use deltaTime
 *
 * Пользовательский класс для отрисовки. С помощью него легко реализовать цикл update() draw() без лишнего мусора
 * Из фич: подсчет FPS и вывод в заголовок, возможность использования deltaTime (независимость выполнения логики от цикла обновления)
 */
public class Window extends BasicWindow {

     public Window()
     {
         super(1000, 700, 10000, "Sample");
         World.init();
         Camera.init();
         startWorking();
     }

     @Override
     protected void update(int deltaTime)
     {
         World.update(deltaTime/1000f);
         GUI.update();
     }

     @Override
     protected void draw()
     {
         Background.draw();
         World.draw();
         GUI.draw();
     }
}
