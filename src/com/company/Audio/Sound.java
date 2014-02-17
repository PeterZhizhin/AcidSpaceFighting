package com.company.Audio;

import com.company.BasicWindow;
import com.company.World;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by peter on 17.02.14.
 */
public class Sound {
    private int buffer;
    private int source;

    public void setIsLooped(boolean isLooped)
    {
        AL10.alSourcei(source, AL10.AL_LOOPING, isLooped ? AL10.AL_TRUE : AL10.AL_FALSE);
    }

    public void play()
    {
        AL10.alSourcePlay(source);
    }

    public void stop()
    {
        AL10.alSourceStop(source);
    }

    public void pause()
    {
        AL10.alSourcePause(source);
    }

    public void setVolume(float volume)
    {
        AL10.alSourcef(source, AL10.AL_GAIN, volume);
    }

    public Sound(String filePath)
    {

            InputStream file = Sound.class.getResourceAsStream(filePath);
        buffer = AL10.alGenBuffers();
        source = AL10.alGenSources();
        WaveData waveData = WaveData.create(file);
        AL10.alBufferData(buffer, waveData.format, waveData.data, waveData.samplerate);
        waveData.dispose();
        AL10.alSourcei(source, AL10.AL_BUFFER, buffer);

    }

    public void dispose()
    {
        AL10.alDeleteBuffers(buffer);
        AL10.alDeleteSources(source);
    }
}
