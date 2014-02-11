package com.company;

import java.util.ArrayList;

import static com.company.PTS.Task.printBottomLine;
import static com.company.PTS.Task.printTopLine;

public class PTS {

    private static enum author {ShirinkinArseny, ZhizhinPeter, None}
    private static enum state {Planned, InDevelopment, Done}
    private static enum type {Bug, NewFeature, Change}

    static class Task {

        private author a;
        private state s;
        private type t;
        private String action;

        public Task (state s2, author a2, type t2, String a3){
            a=a2;
            s=s2;
            t=t2;
            action=a3;
        }

        private String format(String s, int length) {
            if (s.length()<length) s+=' ';
            else if (s.length()>length)
            {
                s=s.substring(0, length-3)+">>>";
            }
            while (s.length()<length)
                s+='∙';
            return s;
        }

        public void print() {
                System.out.println("╟ Author: "+format(a.name(), 16)+" │  State: "+format(s.name(), 13)
                                 +" │  Type: "+format(t.name(), 10)+" │  Action: "+format(action, 50)+" ╢");
        }

        public static void printTopLine() {
            System.out.println("╔══════════════════════════╤═══════════════════════╤═══════════════════╤═════════════════════════════════════════════════════════════╗");
        }

        public static void printBottomLine() {
            System.out.println("╚══════════════════════════╧═══════════════════════╧═══════════════════╧═════════════════════════════════════════════════════════════╝");
        }

        public void printIfNotDone() {
            if (s!=state.Done)
                print();
        }
    }

    public static void print() {
        System.out.println("PTS (Poehavshaya Sistema Taskov) started.\n");

        ArrayList<Task> tasks=new ArrayList<Task>();

        tasks.add(new Task(state.Planned, author.None, type.NewFeature, "Потеря массы пули"));
        tasks.add(new Task(state.Planned, author.None, type.NewFeature, "Новая деталь-локатор"));
        tasks.add(new Task(state.Planned, author.None, type.NewFeature, "Новая деталь-броня"));
        tasks.add(new Task(state.Planned, author.None, type.NewFeature, "Новая деталь-энергоблок"));
        tasks.add(new Task(state.Planned, author.None, type.NewFeature, "Распределение энергии"));
        tasks.add(new Task(state.Planned, author.None, type.NewFeature, "Присоединение деталей на ходу"));
        tasks.add(new Task(state.Planned, author.None, type.NewFeature, "GUI с параментрами движения корабля (скорость, угловая скорость, текущая сила на двигателях)"));

        tasks.add(new Task(state.Done, author.ZhizhinPeter, type.NewFeature, "Разрушения"));
        tasks.add(new Task(state.Done, author.ZhizhinPeter, type.Bug, "Метеориты (вероятно, не только они) рисуются не там, где они расположены"));
        tasks.add(new Task(state.Done, author.ShirinkinArseny, type.Change, "Выстрелы в сторону"));
        tasks.add(new Task(state.Done, author.ShirinkinArseny, type.Change, "Отдача от выстрелов"));
        tasks.add(new Task(state.Done, author.ShirinkinArseny, type.Change, "Перевести Point на Vector2f"));
        tasks.add(new Task(state.Done, author.ShirinkinArseny, type.Bug, "Адекватная анимация шлейфа (бисекция кривая)"));
        tasks.add(new Task(state.Done, author.ShirinkinArseny, type.NewFeature, "Перевод на растровую графику"));
        tasks.add(new Task(state.Done, author.ShirinkinArseny, type.Bug, "Взрыв динамитного модуля лагает сильнее обычного взрыва"));

        printTopLine();
        for (Task s: tasks) {
            s.print();
        }
        printBottomLine();

        System.out.println("\n");
    }

}
