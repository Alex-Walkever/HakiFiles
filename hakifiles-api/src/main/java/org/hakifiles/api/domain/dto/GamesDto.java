package org.hakifiles.api.domain.dto;

import jakarta.validation.constraints.NotBlank;

public class GamesDto {

    private Integer games;

    private Integer wins;

    private Integer looses;

    @NotBlank
    private String leaderId;

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLooses() {
        return looses;
    }

    public void setLooses(Integer looses) {
        this.looses = looses;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public Integer getGames() {
        return games;
    }

    public void setGames(Integer games) {
        this.games = games;
    }
}
