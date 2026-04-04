package com.mapstob.group35.Gameplay;

import com.mapstob.group35.core.PlayerProfile;
import com.mapstob.group35.core.Stats;
import com.mapstob.group35.core.loggedUser;

import java.util.ArrayList;

/**
 * Manages overall game functionality. This class should always be active so long as they are in the game screen.
 * <br>
 * Contains methods for:
 * <ul>
 *     <li>Incrementing time</li>
 *     <li>Spelling attempts</li>
 *     <li>Starting waves</li>
 * </ul>
 *
 * @author Shaun
 * @see WaveManager
 * @see TextManager
 * @see ShopManager
 */

public class GameManager {
    private WaveManager waveManager;
    private TextManager textManager;
    private ShopManager shopManager;
    private loggedUser account = new loggedUser();
    private PlayerProfile player = account.getCurrUser();
    private GameGUIManager GUI;

    private Thread gameThread;

    private int coins;
    private int defaultCoinGain;
    private int maxLife;
    private int curLife;
    private boolean died;
    private boolean downtime;

    /**
     * Constructor.
     */
    public GameManager() {
        maxLife = 100;
        curLife = maxLife;
        coins = 0;
        defaultCoinGain = 10;

        waveManager = new WaveManager(0,10);
        textManager = new TextManager();
        died = false;
        downtime = true;
    }

    /**
     * Constructor for loading already existing data.
     *
     * @param maxLife Maximum Life (by default, assume 100)
     * @param curLife How much health the player should start with
     * @param wave What wave the Player is currently on
     * @param waveTime How long the current wave lasts
     * @param coins How many coins the Player has
     * @param defaultCoinGain How many coins the Player should get for a successful comment
     * @author Shaun Zhang
     */
    public GameManager(int maxLife, int curLife, int wave, int waveTime, int coins, int defaultCoinGain) {
        this.maxLife = maxLife;
        this.curLife = curLife;
        this.coins = coins;
        this.defaultCoinGain = defaultCoinGain;

        this.waveManager = new WaveManager(wave,waveTime);
        this.textManager = new TextManager();
        died = false;
        downtime = true;
    }
    /// /// /// /// PROCESS

    /**
     * This function is the main function that counts time.
     * Waits 1 second, then calls the increment time function for all other managers.
     * if the wave is completed and you don't die, clears the text, heals you if you have regeneration buff, and saves info.
     *
     * @throws InterruptedException
     */
    public void process() throws InterruptedException {
        downtime = false;
        GUI.buttonValid(downtime);
        System.out.println("Process start");
        // taken from https://stackoverflow.com/questions/17758411/java-creating-a-new-thread
        // new thread required so that the thread.sleep doesnt stop the entire program
        gameThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!incTime() && !died){
                    try {
                        gameThread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Process loop");
                }

                if (!died){
                    textManager.clear();
                    resetTime();
                    heal( waveManager.getHealAmount() );
                    saveInfo();
                    downtime = true;
                    GUI.buttonValid(downtime);
                    GUI.nextWaveText();
                    GUI.setWaveText(waveManager.getWave());
                    GUI.setTIME_BAR(waveManager.getCurTime(),waveManager.getWaveTime());
                    displayText(textManager.getTextList());
                }
                gameThread.interrupt();
            }
        });
        gameThread.start();



    }


    /// /// /// /// INPUT

    public void initGUI(GameGUIManager GUI) {
        this.GUI = GUI;
        GUI.setWordText(new ArrayList<TextUnit>());
        displayScore();
        GUI.setCoinText(coins);
        GUI.setWaveText(waveManager.getWave());
        GUI.setTIME_BAR(0,0);
        GUI.setHP_BAR(curLife, maxLife);
        GUI.buttonValid(true);
        GUI.nextWaveText();
        GUI.GameOverScreen(false);
    }
    public GameGUIManager getGUI() {
        return GUI;
    }

    /**
     * Checks spelling and either rewards or punishes you accordingly.
     * @param input
     */
    public void spellingAttempt(String input){
        String corrected_input = input.toUpperCase();
        int spellingAttempt = textManager.spellingAttempt(corrected_input);
        if (spellingAttempt == 0){
            spellingCorrect();
        }
        else if (spellingAttempt == -1){
            //nothing, invalid
        }
        else{
            takeDamage(spellingAttempt);

        }
    }

    /**
     * Starts wave and automatically creates 5 words to complete.
     *
     * @throws InterruptedException
     */
    public void startWave() throws InterruptedException {
        GUI.setWaveText(waveManager.getWave());
        GUI.clearTextField();
        waveManager.startWave();
        textManager.reset();

        for(int x = 0;x<5;x++) {
            textManager.makeText();
            System.out.println("Text made");
        }
        for (int x = 0;x<5;x++) {
            System.out.println(textManager.getTextList().get(x));
        }

        GUI.setWordText(textManager.getTextList());
        process();
    }

    /// /// /// /// COINS
    /**
     * Increases coins by an amount.
     * @param amount
     */
    public void gainCoins(int amount){
        coins += amount;
        GUI.setCoinText(coins);
    }

    /**
     * Gets coin amount.
     * @return coins
     */
    public int getCoins() {return coins;}

    /**
     * Checks if coins can be used, and uses coins if it can. Returns whether coins have been used or not
     * @param amount
     * @return if coins used
     */
    public boolean useCoins(int amount){
        if (coins >= amount){
            coins -= amount;
            player.setCoins(coins);
            GUI.setCoinText(coins);
            return true;
        }
        return false;
    }
    /// /// /// /// SHOP
    /**
     * Adds a buff to the WaveManager
     * @param shopitem
     */
    public void addBuff(BuffItem shopitem){
        waveManager.addBuff(shopitem);
    }
    public void setShopManager(ShopManager shopManager) {this.shopManager = shopManager;}
    public ShopManager getShopManager() {return shopManager;}

    /// /// /// /// HEALTH
    /**
     * Takes damage by amount
     * @param amount
     */
    public void takeDamage(int amount){
        if (amount > curLife) {curLife = 0; die();}
        else{ curLife -= amount; }
        GUI.setHP_BAR(curLife,maxLife);
    }

    /**
     * Heals by amount
     * @param amount
     */
    public void heal(int amount){
        if (amount + curLife > maxLife) {curLife = maxLife;}
        else {curLife += amount; }
        GUI.setHP_BAR(curLife,maxLife);
    }

    /// /// /// /// FINISH
    /**
     * Gains coins and potentially other things that may be added. Only called when spelling is correct.
     */
    public void spellingCorrect(){
        //TODO other additions?
        gainCoins(defaultCoinGain);
        GUI.setWordText(textManager.getTextList());
    }
    public boolean getDowntime(){return downtime;}

    /// /// /// /// TIME
    /**
     * Increments time for itself and all of its sub-managers
     * @return if wave is completed
     */
    public boolean incTime(){
        int damage;
        damage = textManager.incTime();

        if (damage >0){takeDamage(damage);}

        GUI.setTIME_BAR(waveManager.getCurTime(),waveManager.getWaveTime());
        GUI.setWordText(textManager.getTextList());
        displayScore();
        return waveManager.incTime();
    }
    public void resetTime(){
        textManager.resetTime();
        waveManager.resetTime();
    }

    /// /// /// /// DISPLAY
    public void displayScore(){
        int WPM = textManager.getWPM();
        int mistakes = Math.round(textManager.calculateMistakesPercent() *100);
        int wordsCompleted = textManager.getWordsCompleted();
        GUI.setScoreText(WPM, mistakes,wordsCompleted,0,0 );
    }
    public void displayText(ArrayList<TextUnit> textList){
        GUI.setWordText(textList);
    }


    /// /// /// /// MODIFY INFO
    /**
     * Saves all data to the corresponding account
     */
    private void saveInfo(){
        //TODO save information
        player.setCoins(coins);
        Stats update = player.getPlayerStats();
        update.setCurrWave(waveManager.getWave());
        update.updateStats(textManager.getWPM(), textManager.calculateMistakesPercent(), 1, player.getCoins(), textManager.getMistakes(), waveManager.getWaveTime());
        player.setStats(update);
        System.out.println("saved");
    }

    /**
     * Resets all <strong>current</strong> run data, then saves.
     */
    private void deleteInfo(){
        player.setCoins(0);
        player.resetStats();
        saveInfo();
    }

    /**
     * Displays death screen, updates stats, and deletes current run stats
     */
    private void die(){
        died = true;
        deleteInfo();
        GUI.GameOverScreen(true);
        //TODO show death screen, delete current run stats
    }


}
