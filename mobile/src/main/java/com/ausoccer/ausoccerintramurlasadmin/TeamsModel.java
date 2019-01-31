package com.ausoccer.ausoccerintramurlasadmin;

public class TeamsModel {

    String teamLogoUrl, teamName, teamUid;

    public TeamsModel () {

    }

    public TeamsModel(String teamLogoUrl, String teamName, String teamUid) {
        this.teamLogoUrl = teamLogoUrl;
        this.teamName = teamName;
        this.teamUid = teamUid;
    }

    public String getTeamLogoUrl() {
        return teamLogoUrl;
    }

    public void setTeamLogoUrl(String teamLogoUrl) {
        this.teamLogoUrl = teamLogoUrl;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamUid() {
        return teamUid;
    }

    public void setTeamUid(String teamUid) {
        this.teamUid = teamUid;
    }
}
