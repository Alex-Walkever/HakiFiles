package org.hakifiles.api.infrastructure.utils;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class Games {
    @NotNull
    private Integer games;

    @NotNull
    private Integer wins;

    @NotNull
    private Integer looses;

    @NotBlank
    private String leaderId;

    public Games() {
    }

    public Games(Integer games, Integer wins, Integer looses, String leaderId) {
        this.games = games;
        this.wins = wins;
        this.looses = looses;
        this.leaderId = leaderId;
    }

    public Double winRate() {
        double result = (double) ((wins * 100) / games);
        return InternalMath.round(result, 1);
    }

    public Integer getGames() {
        return games;
    }

    public void setGames(Integer games) {
        this.games = games;
    }

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
}
