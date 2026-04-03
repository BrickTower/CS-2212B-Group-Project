package com.mapstob.group35.Gameplay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * tests for TextManager
 * covers text list management, spelling attempts, wpm tracking, and time behavior
 * note: makeText() currently hardcodes "AAA" as the word so tests reflect that
 */
public class TextManagerTest {

    private TextManager textManager;

    // fresh manager with default settings before each test
    @BeforeEach

    void setUp() {
        // Initialize with 2 second spawn rate and 15 second word life
        manager = new TextManager();

    }

    // brand new manager should have an empty text list
    @Test
    public void testInitial_textListIsEmpty() {
        assertTrue(textManager.getTextList().isEmpty());
    }

    // wpm starts at zero
    @Test
    public void testInitial_wpmIsZero() {
        assertEquals(0, textManager.getWPM());
    }

    // words completed starts at zero
    @Test
    public void testInitial_wordsCompletedIsZero() {
        assertEquals(0, textManager.getWordsCompleted());
    }

    // makeText should add a new textunit to the list
    @Test
    public void testMakeText_addsToList() {
        textManager.makeText();
        assertEquals(1, textManager.getTextList().size());
    }

    // calling makeText multiple times should grow the list
    @Test
    public void testMakeText_multipleAdds() {
        textManager.makeText();
        textManager.makeText();
        textManager.makeText();
        assertEquals(3, textManager.getTextList().size());
    }

    // clear should wipe the text list completely
    @Test
    public void testClear_emptiesList() {
        textManager.makeText();
        textManager.makeText();
        textManager.clear();
        assertTrue(textManager.getTextList().isEmpty());
    }

    // spelling attempt on empty list should return -1 (invalid)
    @Test
    public void testSpellingAttempt_emptyListReturnsNegativeOne() {
        int result = textManager.spellingAttempt("AAA");
        assertEquals(-1, result);
    }

    // correct spelling should return 0 (no damage) and increment wordsCompleted
    @Test
    public void testSpellingAttempt_correctSpelling() {
        textManager.makeText(); // hardcoded to "AAA"
        int result = textManager.spellingAttempt("AAA");
        assertEquals(0, result);
        assertEquals(1, textManager.getWordsCompleted());
    }

    // correct spelling should remove the word from the text list
    @Test
    public void testSpellingAttempt_correctRemovesText() {
        textManager.makeText();
        textManager.spellingAttempt("AAA");
        assertTrue(textManager.getTextList().isEmpty());
    }

    // wrong spelling should return damage (non-zero) and not remove the word
    @Test
    public void testSpellingAttempt_incorrectSpelling() {
        textManager.makeText();
        int result = textManager.spellingAttempt("ZZZ");
        assertTrue(result > 0); // should deal some damage
        // the word should still be in the list since it was wrong
        assertEquals(1, textManager.getTextList().size());
    }

    // reset should clear everything back to starting state
    @Test
    public void testReset_clearsAll() {
        textManager.makeText();
        textManager.spellingAttempt("AAA"); // complete one word
        textManager.reset();

        assertTrue(textManager.getTextList().isEmpty());
        assertEquals(0, textManager.getWordsCompleted());
        assertEquals(0, textManager.getWPM());
    }

    // incTime on an empty list should return 0 damage
    @Test
    public void testIncTime_emptyListNoDamage() {
        int damage = textManager.incTime();
        assertEquals(0, damage);
    }

    // incTime many times should eventually cause damage when words time out
    @Test
    public void testIncTime_wordTimeoutCausesDamage() {
        textManager.makeText(); // default time limit is 15 seconds

        int totalDamage = 0;
        for (int i = 0; i < 20; i++) {
            totalDamage += textManager.incTime();
        }

        // after 20 ticks the word with 15 second limit should have timed out
        assertTrue(totalDamage > 0, "expected damage from timed out word but got none");
    }

    // words completed should only go up when you get one right
    @Test
    public void testWordsCompleted_onlyIncrementsOnSuccess() {
        textManager.makeText();
        textManager.spellingAttempt("ZZZ"); // wrong
        assertEquals(0, textManager.getWordsCompleted());

        textManager.spellingAttempt("AAA"); // correct
        assertEquals(1, textManager.getWordsCompleted());
    }

    // mistakes percent is 0 initially since both numerator and denominator are 0
    // after a wrong then right answer it should be 0.5 (1 mistake out of 2 word events but only 1 word completed)
    @Test
    public void testCalculateMistakesPercent_afterMistake() {
        textManager.makeText();
        textManager.spellingAttempt("ZZZ"); // wrong - increments mistakes
        textManager.makeText();
        textManager.spellingAttempt("AAA"); // right - increments wordsCompleted

        // 1 mistake / 1 wordsCompleted = 1.0
        float percent = textManager.calculateMistakesPercent();
        assertEquals(1.0f, percent, 0.001f);
    }

    // setting word spawn time should update the internal spawn timer
    @Test
    public void testSetWordSpawnTime_doesNotThrow() {
        assertDoesNotThrow(() -> textManager.setWordSpawnTime(6));
    }

    // the overloaded constructor should apply the provided settings
    @Test
    public void testOverloadedConstructor_setsValues() {
        TextManager custom = new TextManager(3, 20, 5, 10);
        // we cant access private fields directly but we can verify it doesnt throw
        // and that wordsCompleted/mistakes are reflected
        assertEquals(10, custom.getWordsCompleted());
    }

    // calculateWPM returns 0 when no words have been typed in the tracking window
    @Test
    public void testCalculateWPM_startsAtZero() {
        assertEquals(0, textManager.calculateWPM());
    }
}