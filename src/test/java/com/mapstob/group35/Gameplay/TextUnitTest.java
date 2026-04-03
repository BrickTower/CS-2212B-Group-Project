package com.mapstob.group35.Gameplay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * tests for TextUnit
 * covers time tracking, spelling validity checks, and percent calculations
 */
public class TextUnitTest {

    private TextUnit textUnit;

    // create a fresh text unit before each test - "HELLO" with 10 second limit
    @BeforeEach
    public void setUp() {
        textUnit = new TextUnit("HELLO", 10);
    }

    // getter should return what was passed in the constructor
    @Test
    public void testGetText_returnsCorrectText() {
        assertEquals("HELLO", textUnit.getText());
    }

    // time limit should match what we passed in
    @Test
    public void testGetTimeLimit_returnsCorrectLimit() {
        assertEquals(10.0, textUnit.getTimeLimit(), 0.001);
    }

    // brand new unit should have zero time elapsed
    @Test
    public void testGetCurTime_startsAtZero() {
        assertEquals(0.0, textUnit.getCurTime(), 0.001);
    }

    // percent time should be 0 when no time has passed
    @Test
    public void testGetPercentTime_startsAtZero() {
        assertEquals(0.0f, textUnit.getPercentTime(), 0.001f);
    }

    // incrementing time once should move us to 1
    @Test
    public void testIncTime_incrementsByOne() {
        textUnit.incTime();
        assertEquals(1.0, textUnit.getCurTime(), 0.001);
    }

    // incTime should return false while we havent hit the limit yet
    @Test
    public void testIncTime_returnsFalseBeforeLimit() {
        boolean result = textUnit.incTime(); // now at 1, limit is 10
        assertFalse(result);
    }

    // incTime should return true exactly when we hit the time limit
    @Test
    public void testIncTime_returnsTrueAtLimit() {
        boolean result = false;
        for (int i = 0; i < 10; i++) {
            result = textUnit.incTime();
        }
        assertTrue(result);
    }

    // percent time after halfway should be 0.5
    @Test
    public void testGetPercentTime_halfwayThrough() {
        for (int i = 0; i < 5; i++) {
            textUnit.incTime();
        }
        assertEquals(0.5f, textUnit.getPercentTime(), 0.001f);
    }

    // perfect spelling match should return 0 mistakes
    @Test
    public void testCheckSpellingValidity_perfectMatch() {
        int result = textUnit.checkSpellingValidity("HELLO");
        assertEquals(0, result);
    }

    // wrong spelling with same length should count character differences
    @Test
    public void testCheckSpellingValidity_wrongLetters() {
        // HELLO vs HXLLX - two characters are different
        int result = textUnit.checkSpellingValidity("HXLLX");
        assertEquals(2, result);
    }

    // completely wrong word with same length should count all differences
    @Test
    public void testCheckSpellingValidity_allWrong() {
        int result = textUnit.checkSpellingValidity("ZZZZZ");
        assertEquals(5, result);
    }

    // shorter input should count length difference plus mismatched chars
    @Test
    public void testCheckSpellingValidity_inputTooShort() {
        // HELL vs HELLO - 1 length difference + comparing HELl vs HELL (no char errors)
        int result = textUnit.checkSpellingValidity("HELL");
        assertEquals(1, result); // just the missing character
    }

    // longer input counts the extra length too
    @Test
    public void testCheckSpellingValidity_inputTooLong() {
        // HELLOO vs HELLO - 1 extra char
        int result = textUnit.checkSpellingValidity("HELLOO");
        assertEquals(1, result);
    }

    // empty input vs a 5 letter word should give 5 mistakes (all from length diff)
    @Test
    public void testCheckSpellingValidity_emptyInput() {
        int result = textUnit.checkSpellingValidity("");
        assertEquals(5, result);
    }

    // if the attempt exactly equals the text length but everything is wrong thats max mistakes
    @Test
    public void testCheckSpellingValidity_sameLengthAllWrong() {
        TextUnit unit = new TextUnit("AAA", 5);
        int result = unit.checkSpellingValidity("ZZZ");
        assertEquals(3, result);
    }

    // a single character unit should work fine
    @Test
    public void testSingleCharacter_correctSpelling() {
        TextUnit unit = new TextUnit("A", 5);
        assertEquals(0, unit.checkSpellingValidity("A"));
    }

    // a single character unit wrong answer should give 1 mistake
    @Test
    public void testSingleCharacter_wrongSpelling() {
        TextUnit unit = new TextUnit("A", 5);
        assertEquals(1, unit.checkSpellingValidity("Z"));
    }
}