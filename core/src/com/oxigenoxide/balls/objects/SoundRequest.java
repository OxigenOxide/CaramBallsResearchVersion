package com.oxigenoxide.balls.objects;

public class SoundRequest {
    public int soundID;
    public int priority=5;
    public float volume=1;
    public float pitch=1;

    public SoundRequest(int soundID, int priority, float volume,float pitch){
        this.soundID=soundID;
        this.priority=priority;
        this.volume=volume;
    }
    public SoundRequest(int soundID, int priority){
        this.soundID=soundID;
        this.priority=priority;
    }
    public SoundRequest(int soundID){
        this.soundID=soundID;
    }
}
