package com.mapstob.group35.Gameplay;

import java.util.ArrayList;

/**
 * Utility Class storing a list of words present in BotSpam's game, and a function to pull a random word out of said list
 * @author Shaun Zhang
 */
public class WordList {

    /// List of all words that can appear in BotSpam's words that spawn.
    static String[] textList = {
            "PROTECT", "THREAT", "CURVE", "INTEGRATION", "PAN", "SUBWAY", "QUESTION", "SWEAR", "OLD", "COMMENT", "PURPOSE", "TEAR", "STATION", "GET", "SNUGGLE", "ELEPHANT", "DISCO", "INSERT", "RESORT", "MONOPOLY", "SPEED", "LIVER", "CAREER", "SUNDAY", "PATENT", "SPINE", "TASTY", "INNOVATION", "ANALYST", "STORM", "PENALTY", "EFFECTIVE", "SUM", "RAISE", "TRACK", "NETWORK", "RESIST", "COACH", "IDENTIFICATION", "URINE", "MARS", "OF", "PHOTOGRAPH", "CONVICTION", "SESSION", "BRAG", "DECLINE", "APPEAR", "ELAPSE", "LAW", "CORN", "ASSIGNMENT", "RECOVER", "COLLEGE", "BROCCOLI", "MISCARRIAGE", "OFFEND", "IRONY", "ADVERTISE", "SESSION", "TERRACE", "ALLOWANCE", "INITIATIVE", "CLOSED", "DOMESTIC", "DANGEROUS", "LONG", "RESIDENT", "TANK", "MORAL", "FICTION", "GLARE", "PEDESTRIAN", "CHORD", "SPECIES", "WITNESS", "AUDITOR", "SOAP", "HEIR", "HEROIN", "STALL", "RUB", "INJURY", "VELVET", "SKELETON", "FAVORABLE", "ANGEL", "AFFINITY", "STICKY", "TERMS", "VOUCHER", "DAMAGE", "CULTURAL", "AUTHORITY", "PERCEIVE", "BLUE", "COMPETENCE", "LONELY", "INQUIRY", "DEMONSTRATOR",
    };

    /**
     * Function to pull out a random word in {@code textList}, and return it as a String.
     * @return String of a random word from {@code textlist}.
     * @author Shaun Zhang
     */
    static public String randWord(){
        int index  = (int)(Math.random()*textList.length);
        return textList[index];
    }
}
