package com.ausoccer.ausoccerintramurlasadmin;

public class MatchesModel {
    // General variables.
    String matchUid;
    String result, matchDate, matchStatus, matchTime;
    int matchNumber;

    // Home variables.
    String homeTeamUid;
    String homeTeamName, homeTeamLogoUrl;
    int homeTeamGoals;

    // Away variables.
    String awayTeamUid;
    String awayTeamName, awayTeamLogoUrl;
    int awayTeamGoals;


    public MatchesModel() {

    }

    public MatchesModel(String matchUid, String result, String matchDate, String matchStatus, String matchTime, int matchNumber, String homeTeamUid, String homeTeamName, String homeTeamLogoUrl, int homeTeamGoals, String awayTeamUid, String awayTeamName, String awayTeamLogoUrl, int awayTeamGoals) {
        this.matchUid = matchUid;
        this.result = result;
        this.matchDate = matchDate;
        this.matchStatus = matchStatus;
        this.matchTime = matchTime;
        this.matchNumber = matchNumber;
        this.homeTeamUid = homeTeamUid;
        this.homeTeamName = homeTeamName;
        this.homeTeamLogoUrl = homeTeamLogoUrl;
        this.homeTeamGoals = homeTeamGoals;
        this.awayTeamUid = awayTeamUid;
        this.awayTeamName = awayTeamName;
        this.awayTeamLogoUrl = awayTeamLogoUrl;
        this.awayTeamGoals = awayTeamGoals;
    }

    public String getMatchUid() {
        return matchUid;
    }

    public void setMatchUid(String matchUid) {
        this.matchUid = matchUid;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(int matchNumber) {
        this.matchNumber = matchNumber;
    }

    public String getHomeTeamUid() {
        return homeTeamUid;
    }

    public void setHomeTeamUid(String homeTeamUid) {
        this.homeTeamUid = homeTeamUid;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getHomeTeamLogoUrl() {
        return homeTeamLogoUrl;
    }

    public void setHomeTeamLogoUrl(String homeTeamLogoUrl) {
        this.homeTeamLogoUrl = homeTeamLogoUrl;
    }

    public int getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public void setHomeTeamGoals(int homeTeamGoals) {
        this.homeTeamGoals = homeTeamGoals;
    }

    public String getAwayTeamUid() {
        return awayTeamUid;
    }

    public void setAwayTeamUid(String awayTeamUid) {
        this.awayTeamUid = awayTeamUid;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public String getAwayTeamLogoUrl() {
        return awayTeamLogoUrl;
    }

    public void setAwayTeamLogoUrl(String awayTeamLogoUrl) {
        this.awayTeamLogoUrl = awayTeamLogoUrl;
    }

    public int getAwayTeamGoals() {
        return awayTeamGoals;
    }

    public void setAwayTeamGoals(int awayTeamGoals) {
        this.awayTeamGoals = awayTeamGoals;
    }
}