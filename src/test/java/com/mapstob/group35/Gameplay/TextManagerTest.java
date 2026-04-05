package com.mapstob.group35.Gameplay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * tests for TextManager
 * covers text list mgmt, spelling attempts, wpm, time behavior, and firstAttempt tracking
 *
 */
public class TextManagerTest {

    private TextManager tm;

    // fresh manager before each test
    @BeforeEach
    public void setUp() {
        tm = new TextManager();
    }

    // empty list on init
    @Test
    public void testInit_listEmpty() {
        assertTrue(tm.getTextList().isEmpty());
    }

    // wpm init zero
    @Test
    public void testInit_wpmZero() {
        assertEquals(0, tm.getWPM());
    }

    // wordsCompleted init zero
    @Test
    public void testInit_wordsZero() {
        assertEquals(0, tm.getWordsCompleted());
    }

    // mistakes init zero
    @Test
    public void testInit_mistakesZero() {
        assertEquals(0, tm.getMistakes());
    }

    // firstAttemptWords init zero
    @Test
    public void testInit_firstAttZero() {
        assertEquals(0, tm.getFirstAttemptWords());
    }

    // makeText adds one unit
    @Test
    public void testMakeText_addsOne() {
        tm.makeText();
        assertEquals(1, tm.getTextList().size());
    }

    // makeText multiple times grows list
    @Test
    public void testMakeText_multi() {
        tm.makeText();
        tm.makeText();
        tm.makeText();
        assertEquals(3, tm.getTextList().size());
    }

    // word from WordList is not null or empty
    @Test
    public void testMakeText_wordNotEmpty() {
        tm.makeText();
        String word = tm.getTextList().get(0).getText();
        assertNotNull(word);
        assertFalse(word.isEmpty());
    }

    // clear wipes list
    @Test
    public void testClear_empty() {
        tm.makeText();
        tm.makeText();
        tm.clear();
        assertTrue(tm.getTextList().isEmpty());
    }

    // spelling attempt on empty list returns -1
    @Test
    public void testSpell_emptyList() {
        assertEquals(-1, tm.spellingAttempt("ANYTHING"));
    }

    // correct spelling returns 0 and increments wordsCompleted
    // uppercase to mirror GameManager behaviour
    @Test
    public void testSpell_correct() {
        tm.makeText();
        String word = tm.getTextList().get(0).getText().toUpperCase();
        assertEquals(0, tm.spellingAttempt(word));
        assertEquals(1, tm.getWordsCompleted());
    }

    // correct spelling removes word from list
    @Test
    public void testSpell_correctRemoves() {
        tm.makeText();
        tm.spellingAttempt(tm.getTextList().get(0).getText().toUpperCase());
        assertTrue(tm.getTextList().isEmpty());
    }

    // wrong spelling returns damage and leaves word in list
    @Test
    public void testSpell_wrong() {
        tm.makeText();
        int dmg = tm.spellingAttempt("ZZZZZZZZZZZZZZ");
        assertTrue(dmg > 0);
        assertEquals(1, tm.getTextList().size());
    }

    // getMistakes increments on wrong answer
    @Test
    public void testMistakes_incOnWrong() {
        tm.makeText();
        tm.spellingAttempt("ZZZZZZZZZZZZZZ");
        assertEquals(1, tm.getMistakes());
    }

    // getMistakes unchanged on correct answer
    @Test
    public void testMistakes_noIncOnCorrect() {
        tm.makeText();
        tm.spellingAttempt(tm.getTextList().get(0).getText().toUpperCase());
        assertEquals(0, tm.getMistakes());
    }

    // first try correct increments firstAttemptWords
    @Test
    public void testFirstAtt_incOnFirstTry() {
        tm.makeText();
        tm.spellingAttempt(tm.getTextList().get(0).getText().toUpperCase());
        assertEquals(1, tm.getFirstAttemptWords());
    }

    // wrong then correct does not count as first attempt
    @Test
    public void testFirstAtt_noIncAfterMistake() {
        tm.makeText();
        tm.spellingAttempt("ZZZZZZZZZZZZZZ"); // wrong
        tm.spellingAttempt(tm.getTextList().get(0).getText().toUpperCase()); // correct but not first try
        assertEquals(0, tm.getFirstAttemptWords());
    }

    // firstTry resets after each word so next word starts fresh
    @Test
    public void testFirstAtt_resetsPerWord() {
        tm.makeText();
        tm.spellingAttempt("ZZZZZZZZZZZZZZ"); // wrong on word 1
        tm.spellingAttempt(tm.getTextList().get(0).getText().toUpperCase()); // complete word 1 - not first try

        tm.makeText();
        tm.spellingAttempt(tm.getTextList().get(0).getText().toUpperCase()); // word 2 first try

        assertEquals(1, tm.getFirstAttemptWords()); // only word 2 counts
    }

    // reset clears everything including mistakes
    @Test
    public void testReset_clearsAll() {
        tm.makeText();
        tm.spellingAttempt(tm.getTextList().get(0).getText());
        tm.reset();
        assertTrue(tm.getTextList().isEmpty());
        assertEquals(0, tm.getWordsCompleted());
        assertEquals(0, tm.getMistakes());
        assertEquals(0, tm.getWPM());
    }

    // incTime on empty list returns 0 damage
    @Test
    public void testIncTime_emptyNoDmg() {
        assertEquals(0, tm.incTime());
    }

    // word times out after 20 ticks (wordDefaultTimeLeft = 20)
    @Test
    public void testIncTime_timeoutDmg() {
        tm.makeText();
        int totalDmg = 0;
        for (int i = 0; i < 25; i++) totalDmg += tm.incTime();
        assertTrue(totalDmg > 0, "expected dmg from timed out word but got none");
    }

    // wordsCompleted only goes up on correct answer
    @Test
    public void testWordsCompleted_onlyOnCorrect() {
        tm.makeText();
        tm.spellingAttempt("ZZZZZZZZZZZZZZ"); // wrong
        assertEquals(0, tm.getWordsCompleted());
        tm.spellingAttempt(tm.getTextList().get(0).getText().toUpperCase()); // correct
        assertEquals(1, tm.getWordsCompleted());
    }

    // 1 mistake / 1 wordsCompleted = 1.0
    @Test
    public void testMistakePct_afterOne() {
        tm.makeText();
        tm.spellingAttempt("ZZZZZZZZZZZZZZ"); // wrong
        tm.spellingAttempt(tm.getTextList().get(0).getText().toUpperCase()); // right
        assertEquals(1.0f, tm.calculateMistakesPercent(), 0.001f);
    }

    // setWordSpawnTime doesnt throw
    @Test
    public void testSetSpawnTime_noThrow() {
        assertDoesNotThrow(() -> tm.setWordSpawnTime(8));
    }

    // overloaded ctor applies provided values
    @Test
    public void testCtor_overloaded() {
        TextManager custom = new TextManager(3, 20, 5, 10);
        assertEquals(10, custom.getWordsCompleted());
        assertEquals(5, custom.getMistakes());
    }

    // calculateWPM starts at zero
    @Test
    public void testWPM_initZero() {
        assertEquals(0, tm.calculateWPM());
    }

    // auto-spawns word at default spawn interval of 6
    @Test
    public void testIncTime_autoSpawn() {
        for (int i = 0; i < 6; i++) tm.incTime();
        assertFalse(tm.getTextList().isEmpty());
    }
}