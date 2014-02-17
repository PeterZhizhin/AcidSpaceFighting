package com.company.Audio;

public class SoundBase {

    private static Sound music;
    private static Sound engine;
    private static Sound explosion;
    private static Sound connect;

    public static void playEngine() {
        engine.play();
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

        engine = new Sound("engine.wav");
        engine.setIsLooped(false);
        engine.setVolume(0.3f);

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
        engine.dispose();
    }

}
