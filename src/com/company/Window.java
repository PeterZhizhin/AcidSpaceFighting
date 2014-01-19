package com.company;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

/**
 * User class which helps allows you to make update() draw() cycle easily
 * It has FPS inside title, and you should also use deltaTime
 *
 * Пользовательский класс для отрисовки. С помощью него легко реализовать цикл update() draw() без лишнего мусора
 * Из фич: подсчет FPS и вывод в заголовок, возможность использования deltaTime (независимость выполнения логики от цикла обновления)
 */
public class Window extends BasicWindow {

    World world = new World();
    int updateTimer = 0;

     public Window(int width, int heigth, int frameRate, String title)
     {
         super.initDisplay(width, heigth, frameRate, title);
         //Your initialization
         //Ваша инициазизация
         try {
             Mouse.create();
         } catch (LWJGLException e) {
             e.printStackTrace();
         }

         goToUpdateDrawCycle(frameRate);
     }

     @Override
     protected void update(int deltaTime)
     {
        updateTimer+=deltaTime;
        if (updateTimer > 10)
        {
            world.update();
            updateTimer = 0;
        }
     }

     @Override
     protected void draw()
     {
         glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
         world.draw();
     }

     @Override
     protected void destroy()
     {
         Mouse.destroy();
         System.out.println("Exiting");
     }
}
