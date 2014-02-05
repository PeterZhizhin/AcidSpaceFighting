package com.company;

import java.util.ArrayList;

public class PTS {

    public static void print() {
        System.out.println("PTS (Poehavshaya Sistema Taskov) started.");
        System.out.println("Samaya poehavshaya iz vseh. Srsly.");
        System.out.println("===============================");

        ArrayList<String> tasks=new ArrayList<String>();

        /*
        Syntax:
              [$state] [$autor] [$type] [$add date] [$developer] [$last update] $Text
              $state: PLANNED, IN DEVELOP, IN TESTING, DONE
              $type: BUG, NEW FEATURE, CHANGE,
              $autor: who invented this feature
              $developer: who develop this feature
         */

        tasks.add("[PLANNED]      [Shirinkin Arseny]     [CHANGE]      [5feb14]    [NONE]   [NONE]   Отрисовка целостности детали");
        tasks.add("[PLANNED]      [Shirinkin Arseny]     [CHANGE]      [5feb14]    [NONE]   [NONE]   Выстрелы в сторону");
        tasks.add("[PLANNED]      [Shirinkin Arseny]     [CHANGE]      [5feb14]    [NONE]   [NONE]   Отдача от выстрелов");
        tasks.add("[PLANNED]      [Shirinkin Arseny]     [NEW FEATURE] [5feb14]    [NONE]   [NONE]   Потеря массы пули");
        tasks.add("[PLANNED]      [Shirinkin Arseny]     [NEW FEATURE] [5feb14]    [NONE]   [NONE]   Разрушения");
        tasks.add("[PLANNED]      [Shirinkin Arseny]     [NEW FEATURE] [5feb14]    [NONE]   [NONE]   Новая деталь-локатор");
        tasks.add("[PLANNED]      [Shirinkin Arseny]     [NEW FEATURE] [5feb14]    [NONE]   [NONE]   Новая деталь-броня");
        tasks.add("[PLANNED]      [Shirinkin Arseny]     [NEW FEATURE] [5feb14]    [NONE]   [NONE]   Новая деталь-энергоблок");
        tasks.add("[PLANNED]      [Shirinkin Arseny]     [NEW FEATURE] [5feb14]    [NONE]   [NONE]   Распределение энергии");
        tasks.add("[PLANNED]      [Shirinkin Arseny]     [NEW FEATURE] [5feb14]    [NONE]   [NONE]   Присоединение деталей на ходу");
        tasks.add("[PLANNED]      [Shirinkin Arseny]     [CHANGE]      [5feb14]    [NONE]   [NONE]   Адекватная анимация шлейфа");
        tasks.add("[PLANNED]      [Shirinkin Arseny]     [NEW FEATURE] [5feb14]    [NONE]   [NONE]   Мощность двигателя и индикация");

        System.out.println("Tasks:");
        for (String s: tasks) {
            if (!s.startsWith("[DONE]"))
                System.out.println(s);
        }


        System.out.println("===============================");
    }

}
