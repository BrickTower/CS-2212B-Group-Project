package com.mapstob.group35.Gameplay;

import java.util.ArrayList;

public class WordList {

    static String[] textList = {
            "PROTECT", "THREAT", "CURVE", "INTEGRATION", "PAN", "SUBWAY", "QUESTION", "SWEAR", "OLD", "COMMENT", "PURPOSE", "TEAR", "STATION", "GET", "SNUGGLE", "ELEPHANT", "DISCO", "INSERT", "RESORT", "MONOPOLY", "SPEED", "LIVER", "CAREER", "SUNDAY", "PATENT", "SPINE", "TASTY", "INNOVATION", "ANALYST", "STORM", "PENALTY", "EFFECTIVE", "SUM", "RAISE", "TRACK", "NETWORK", "RESIST", "COACH", "IDENTIFICATION", "URINE", "MARS", "OF", "PHOTOGRAPH", "CONVICTION", "SESSION", "BRAG", "DECLINE", "APPEAR", "ELAPSE", "LAW", "CORN", "ASSIGNMENT", "RECOVER", "COLLEGE", "BROCCOLI", "MISCARRIAGE", "OFFEND", "IRONY", "ADVERTISE", "SESSION", "TERRACE", "ALLOWANCE", "INITIATIVE", "CLOSED", "DOMESTIC", "DANGEROUS", "LONG", "RESIDENT", "TANK", "MORAL", "FICTION", "GLARE", "PEDESTRIAN", "CHORD", "SPECIES", "WITNESS", "AUDITOR", "SOAP", "HEIR", "HEROIN", "STALL", "RUB", "INJURY", "VELVET", "SKELETON", "FAVORABLE", "ANGEL", "AFFINITY", "STICKY", "TERMS", "VOUCHER", "DAMAGE", "CULTURAL", "AUTHORITY", "PERCEIVE", "BLUE", "COMPETENCE", "LONELY", "INQUIRY", "DEMONSTRATOR", "",
    };

    static public String randWord(){
        int index  = (int)(Math.random()*textList.length);
        return textList[index];
    }
}
