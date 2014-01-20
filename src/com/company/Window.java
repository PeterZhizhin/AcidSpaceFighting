package com.company;

/**
 * User class which helps allows you to make update() draw() cycle easily
 * It has FPS inside title, and you should also use deltaTime
 *
 * Пользовательский класс для отрисовки. С помощью него легко реализовать цикл update() draw() без лишнего мусора
 * Из фич: подсчет FPS и вывод в заголовок, возможность использования deltaTime (независимость выполнения логики от цикла обновления)
 */
public class Window extends BasicWindow {

    private World world;
    int delta = 0;

     public Window()
     {
         super(1000, 700, 10000, "Sample");
         world= new World();
         startWorking();
     }

     @Override
     protected void update(int deltaTime)
     {
         delta+=deltaTime;
         if (delta>10)
         {
             world.update();
             delta = 0;
         }

     }

     @Override
     protected void draw()
     {
         world.draw();
     }
}
