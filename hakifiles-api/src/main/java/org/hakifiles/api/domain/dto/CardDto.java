package org.hakifiles.api.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.hakifiles.api.domain.entities.CardInfo;

import java.util.List;

public class CardDto {
    //To CardInfo
    public String image;
    public String cardId;
    public String category;
    public String alternateArt;
    public String product;
    public String productCode;
    public String rarity;
    public String colorsCard;
    public String tournamentStatus;

    // To LeaderCard
    public String name;
    public String type;
    public String effects;
    public String life;
    public String power;
    public String attribute;

    //To CharacterCard
    public String triggerEffect;
    public String cost;
    public String counterPower;
}
