package com.mapstob.group35.Gameplay;

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

    private int coins;
    private int defaultCoinGain;
    private int maxLife;
    private int curLife;
    private boolean died;

    //TODO move elsewhere
    private String[] easyDiffWords = {"cake", "cookie", "tea", "hope", "news", "chance", "luck", "known", "you", "soul"};
    private String[] mediumDiffWords = {"poland", "canada", "reimu", "marisa", "important", "zoot", "dryad", "nomad", "heaven", "honeybee", "sandpaper"};
    private String[] hardDiffWords = {"artificial", "manipulation", "despised", "topological", "prerequisite", "difficulty", "completion", "andesite", "granite", "drizzled"};


    /**
     * Constructor.
     */
    public GameManager() {
        maxLife = 100; //todo change
        curLife = maxLife;
        coins = 0;
        defaultCoinGain = 10;

        waveManager = new WaveManager(0,30);
        textManager = new TextManager();
        shopManager = new ShopManager(this);
        died = false;
    }

    /**
     * Constructor for loading already existing data.
     *
     * @param maxLife
     * @param curLife
     * @param wave
     * @param waveTime
     * @param coins
     * @param defaultCoinGain
     */
    public GameManager(int maxLife, int curLife, int wave, int waveTime, int coins, int defaultCoinGain) {
        this.maxLife = maxLife;
        this.curLife = curLife;
        this.coins = coins;
        this.defaultCoinGain = defaultCoinGain;

        this.waveManager = new WaveManager(wave,waveTime);
        this.textManager = new TextManager();
        shopManager = new ShopManager(this);
        //due to how loading this works you can savescum shop items until you get a good one but like oh well
        died = false;
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
        while(!incTime() && !died){
            Thread.sleep(1000);
        }
        if (!died){
            textManager.clear();
            heal( waveManager.getHealAmount() );
            saveInfo();
        }

    }


    /// /// /// /// INPUT
    /**
     * Checks spelling and either rewards or punishes you accordingly.
     * @param input
     */
    public void spellingAttempt(String input){ //TODO replace string input with actual input
        int spellingAttempt = textManager.spellingAttempt(input);
        if (spellingAttempt == 0){
            spellingCorrect();
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
        waveManager.startWave();
        textManager.reset();
        for(int x = 0;x<5;x++) {
            textManager.makeText("Placeholder"); //TODO change!!!
        }
        process();
    }

    /// /// /// /// COINS
    /**
     * Increases coins by an amount.
     * @param amount
     */
    public void gainCoins(int amount){coins += amount;}

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
        if (coins > amount){
            coins -= amount;
            return true;
        }
        return false;
    }
    /// /// /// /// SHOP UPGRADES
    /**
     * Adds a buff to the WaveManager
     * @param shopitem
     */
    public void addBuff(BuffItem shopitem){
        waveManager.addBuff(shopitem);
    }

    /// /// /// /// HEALTH
    /**
     * Takes damage by amount
     * @param amount
     */
    public void takeDamage(int amount){
        if (amount > curLife) {curLife = 0; die();}
        else{ curLife -= amount; }
    }

    /**
     * Heals by amount
     * @param amount
     */
    public void heal(int amount){
        if (amount + curLife > maxLife) {curLife = maxLife;}
        else {curLife += amount; }
    }

    /// /// /// /// FINISH
    /**
     * Gains coins and potentially other things that may be added. Only called when spelling is correct.
     */
    public void spellingCorrect(){
        //TODO other additions?
        gainCoins(defaultCoinGain);
    }

    /// /// /// /// TIME
    /**
     * Increments time for itself and all of its sub-managers
     * @return if wave is completed
     */
    public boolean incTime(){
        int damage;
        damage = textManager.incTime();

        if (damage >0){takeDamage(damage);}

        return waveManager.incTime();
    }




    /// /// /// /// MODIFY INFO
    /**
     * Saves all data to the corresponding account
     */
    private void saveInfo(){
        //TODO save information
    }

    /**
     * Resets all <strong>current</strong> run data, then saves.
     */
    private void deleteInfo(){
        //TODO
        saveInfo();
    }

    /**
     * Displays death screen, updates stats, and deletes current run stats
     */
    private void die(){
        died = true;
        deleteInfo();
        //TODO show death screen, delete current run stats
    }


}
