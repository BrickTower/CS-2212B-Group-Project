package com.mapstob.group35;

import java.util.ArrayList;

public class WaveManager {
    private int wave;
    private int curTime;
    private int waveTime;
    private int defaultWaveTime;


    private ArrayList<BuffItem> buffList;
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

    public ArrayList<BuffItem> getBuffList(){return buffList;}
    public void addBuff(BuffItem buff){buffList.add(buff);}


    public void waveOver(){
        incWave();
        // go through buffs, see if any are used up
        for(int i = 0; i < buffList.size(); i++){
            if (buffList.get(i).decLength()){
                buffList.remove(buffList.get(i));
                i--;
            }
        }
    }

    public void startWave(){
        curTime = 0;
        setWaveTime((int) (defaultWaveTime * getWaveTimeModifier()));
    }
    public boolean incTime(){
        curTime++;
        if (curTime>=waveTime){
            waveOver();
            return true;
        }
        return false;
    }

    // for the timer icon
    public float waveCompletionPercent(){
        return (float) curTime / (float) waveTime;
    }

    private float getWaveTimeModifier(){
        float reduction = 1;
        for(int i = 0; i < buffList.size(); i++){
            if (buffList.get(i).getName().equals("Wave Length Decrease")){
                reduction = reduction / (float) buffList.get(i).getPower();
            }
        }
        return reduction;
    }
    public int getHealAmount(){
        int amount = 0;
        for(int i = 0; i < buffList.size(); i++){
            if (buffList.get(i).getName().equals("Regeneration")){
                amount += buffList.get(i).getPower();
            }
        }
        return amount;
    }

    public float getWordTimeModifier(){
        float increase = 1;
        for(int i = 0; i < buffList.size(); i++){
            if (buffList.get(i).getName().equals("Word Time Increase")){
                increase = increase * (1 + 1/(float) buffList.get(i).getPower());
            }
        }
        return increase;
    }

    public float getCoinGainModifier(){
        float increase = 1;
        for(int i = 0; i < buffList.size(); i++){
            if (buffList.get(i).getName().equals("Coin Gain Increase")){
                increase = increase * (1 + 1/(float) buffList.get(i).getPower());
            }
        }
        return increase;
    }
}
