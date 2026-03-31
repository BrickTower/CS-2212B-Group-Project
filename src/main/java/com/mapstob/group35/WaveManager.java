package com.mapstob.group35;

import java.util.ArrayList;
import java.util.Dictionary;

public class WaveManager {
    private int wave;
    private int curTime;
    private int waveTime;
    private int defaultWaveTime;

    private ArrayList<String> buffList;
    /*  BUFF TYPES
    *   increase text time for x waves
    *   lower wave time for x waves
    *   increase coin gain for x waves
    *   lower damage taken for x waves
    * */

    public WaveManager(int wave,  int defaultWaveTime) {
        this.wave = wave;
        this.waveTime = defaultWaveTime;
        this.defaultWaveTime = defaultWaveTime;
        curTime = 0;
    }

    public void incWave(){wave++;}
    public int getWave(){return wave;}
    public void setWaveTime(int waveTime){this.waveTime = waveTime;}
    public double getWaveTimeLeft () {return waveTime - curTime;}


    public void waveOver(){
        incWave();
    }
    public void startWave(){
        curTime = 0;
        setWaveTime(defaultWaveTime);
    }
    public void incTime(){
        curTime++;
        if (curTime>=waveTime){
            waveOver();
        }
    }
    // for the timer icon
    public float waveCompletionPercent(){
        return (float) curTime / (float) waveTime;
    }
}
