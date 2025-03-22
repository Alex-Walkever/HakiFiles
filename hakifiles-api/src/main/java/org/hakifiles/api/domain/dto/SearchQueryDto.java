package org.hakifiles.api.domain.dto;

import org.hakifiles.api.domain.entities.CardInfo;

import java.util.List;

public class SearchQueryDto {
    public String name;
    public String effects;
    public String triggerEffect;
    public List<CardInfo.Category> category;
    public List<CardInfo.ColorCard> colorsCard;
    public QueryConditional costConditional;
    public Integer cost;
    public QueryConditional lifeConditional;
    public Integer life;
    public QueryConditional powerConditional;
    public Integer power;
    public QueryConditional counterPowerConditional;
    public Integer counterPower;
    public List<CardInfo.Attribute> attribute;
    public List<String> type;
    public CardInfo.TournamentStatus tournamentStatus;
    public Integer block;

    public String alternateArt;
    public String product;
    public String productCode;
    public String rarity;


    public static enum QueryConditional {
        EQUAL_TO,
        LESS_THAN,
        GREATER_THAN,
        LESS_THAN_EQUAL_TO,
        GREATER_THAN_EQUAL_TO
    }

    public static enum CardAbilities {
        COUNTER,
        EFFECT,
        TRIGGER
    }
}
