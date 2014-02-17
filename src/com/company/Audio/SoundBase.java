package com.company.Audio;

public class SoundBase {

    private static Sound music;
    private static Sound []engine;
    private static Sound explosion;
    private static Sound connect;

    private static int num=0;
    public static void playEngine() {
        engine[num].play();
        num++;
        if (num>=engine.length) num=0;
    }

    public static void playExplosion() {
        explosion.play();
    }

    public static void playConnect() {
        connect.play();
    }

    public static void playMusic() {
        music.play();
    }

    public static void init() {

        music = new Sound("ambience02.wav");
        music.setIsLooped(true);
        music.setVolume(0.7f);

        //todo: make it not stupid
        engine=new Sound[50];
        for (int i=0; i<50; i++) {
        engine[i] = new Sound("engine.wav");
        engine[i].setIsLooped(false);
        engine[i].setVolume(0.1f);
        }

        explosion = new Sound("xPlosion.wav");
        explosion.setIsLooped(false);
        explosion.setVolume(0.3f);

        connect = new Sound("connect.wav");
        connect.setIsLooped(false);
        connect.setVolume(0.3f);
    }

    public static void dispose() {
        music.dispose();
        connect.dispose();
        explosion.dispose();
        for (Sound anEngine : engine) anEngine.dispose();
    }

}
