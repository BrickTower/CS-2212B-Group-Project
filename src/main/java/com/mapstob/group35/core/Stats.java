package com.mapstob.group35.core;

public class Stats {
    private double averageWPM;
    private double peakWPM;
    private double totalAccuracy;
    private int totalGamesPlayed;

    public Stats() {
        this.averageWPM = 0;
        this.peakWPM = 0;
        this.totalGamesPlayed = 0;
        this.totalAccuracy = 0;
    }

    public void updateStats(double wpm, double accuracy, int errors) {
        if (wpm > this.peakWPM) {
            this.peakWPM = (float) wpm;
        }

        // RECALCULATION OF AVERAGE BASED ON THIS STACKOVERFLOW ANSWER: https://stackoverflow.com/a/31904394
        double currentAvg = this.averageWPM * this.totalGamesPlayed;
        double newSum = wpm + currentAvg;
        this.totalGamesPlayed++;
        this.averageWPM = newSum / this.totalGamesPlayed;

        // TODO: HOW DOES ONE IMPLEMENT TOTAL ACCURACY, WITH ERRORS?

    }

    public double getAverageWPM() {
        return this.averageWPM;
    }

    public double getAccuracy() {
        return this.totalAccuracy;
    }
}
