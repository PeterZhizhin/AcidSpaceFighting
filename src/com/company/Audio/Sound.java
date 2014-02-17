package com.company.Audio;

import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;
import java.io.InputStream;

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

        AL10.alSourcef(source, AL10.AL_MAX_GAIN, 1.0f);
        AL10.alSourcef(source, AL10.AL_MIN_GAIN, 0.0f);

    }

    public void dispose()
    {
        AL10.alDeleteBuffers(buffer);
        AL10.alDeleteSources(source);
    }
}
