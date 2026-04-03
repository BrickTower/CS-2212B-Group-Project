package com.mapstob.group35.Gameplay;

import java.util.ArrayList;

/**
 * Manages waves, start/stop, and buffs based on how many waves they last
 * @author Shaun
 * @see GameManager
 */
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

    /**
     * Constructor
     *
     * @param wave
     * @param defaultWaveTime
     */
    public WaveManager(int wave,  int defaultWaveTime) {
        this.wave = wave;
        this.waveTime = defaultWaveTime;
        this.defaultWaveTime = defaultWaveTime;
        this.buffList = new ArrayList<BuffItem>();
        curTime = 0;
    }

    /**
     * Increments wave.
     */
    public void incWave(){wave++;}

    /**
     * Gets wave amount.
     * @return wave
     */
    public int getWave(){return wave;}

    /**
     * Sets how long a wave is.
     * @param waveTime
     */
    public void setWaveTime(int waveTime){this.waveTime = waveTime;}

    /**
     * Gets how much time is left in the wave.
     * @return Time left
     */
    public double getWaveTimeLeft () {return waveTime - curTime;}
    public int getWaveTime(){return waveTime;}
    public int getCurTime(){return curTime;}

    /**
     * Gets list of current buffs.
     * @return List of wave-based buffs
     */
    public ArrayList<BuffItem> getBuffList(){return buffList;}

    /**
     * Adds a new buff onto the bufflist.
     * @param buff
     */
    public void addBuff(BuffItem buff){buffList.add(buff);}

    private void waveOver(){
        incWave();
        // go through buffs, see if any are used up
        for(int i = 0; i < buffList.size(); i++){
            if (buffList.get(i).decLength()){
                buffList.remove(buffList.get(i));
                i--;
            }
        }
    }

    /**
     * This method is called when you want to start the wave.
     */
    public void startWave(){
        curTime = 0;
        setWaveTime((int) (defaultWaveTime * getWaveTimeModifier()));
    }

    /**
     * Increments time, and returns whether the wave is over or not.
     * @return Time up
     */
    public boolean incTime(){
        curTime++;
        if (curTime>=waveTime){
            waveOver();
            return true;
        }
        return false;
    }

    public void resetTime(){
        curTime = 0;
    }

    /**
     * This method is used when calculating what percentage of the wave is completed
     * @return Wave completion percent
     */
    public float waveCompletionPercent(){
        return (float) curTime / (float) waveTime;
    }
    /**
     * Used when getting how much wave time is decreased. Stacks on itself multiplicatively.
     * -30% and -30% gives you -69%, not -60%, because its technically 1.3 * 1.3.
     * @return Wave time modifier
     */
    private float getWaveTimeModifier(){
        float reduction = 1;
        for(int i = 0; i < buffList.size(); i++){
            if (buffList.get(i).getName().equals("Wave Length Decrease")){
                reduction = reduction / (float) buffList.get(i).getPower();
            }
        }
        return reduction;
    }

    /**
     * Used when getting how much total healing per wave is done.
     * @return Total heal amount
     */
    public int getHealAmount(){
        int amount = 0;
        for(int i = 0; i < buffList.size(); i++){
            if (buffList.get(i).getName().equals("Regeneration")){
                amount += buffList.get(i).getPower();
            }
        }
        return amount;
    }

    /**
     * Used when getting how much word time is increased. Stacks on itself multiplicatively.
     * +30% and +30% gives you +69%, not +60%, because its technically 1.3 * 1.3.
     * @return Word time modifier
     */
    public float getWordTimeModifier(){
        float increase = 1;
        for(int i = 0; i < buffList.size(); i++){
            if (buffList.get(i).getName().equals("Word Time Increase")){
                increase = increase * (1 + 1/(float) buffList.get(i).getPower());
            }
        }
        return increase;
    }
    /**
     * Used when getting how much coin gain is increased. Stacks on itself multiplicatively.
     * +30% and +30% gives you 69%, not 60%, because its technically 1.3 * 1.3.
     * @return Coin gain modifier
     */
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
