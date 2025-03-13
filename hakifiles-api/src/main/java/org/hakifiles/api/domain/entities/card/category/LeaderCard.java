package org.hakifiles.api.domain.entities.card.category;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.hakifiles.api.domain.entities.CardInfo;

import java.util.List;

@Entity
@Table(name = "Leaders")
public class LeaderCard {
    @Id
    @Column(nullable = false)
    private String CardId;

    @NotBlank
    private String name;
    @NotEmpty
    private List<String> type;
    @NotEmpty
    private List<String> effects;
    @NotEmpty
    private Integer life;
    @NotEmpty
    private Integer power;
    @NotEmpty
    private List<CardInfo.Attribute> attribute;

    public String getCardId() {
        return CardId;
    }

    public void setCardId(String cardId) {
        CardId = cardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public List<String> getEffects() {
        return effects;
    }

    public void setEffects(List<String> effects) {
        this.effects = effects;
    }

    public Integer getLife() {
        return life;
    }

    public void setLife(Integer life) {
        this.life = life;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public List<CardInfo.Attribute> getAttribute() {
        return attribute;
    }

    public void setAttribute(List<CardInfo.Attribute> attribute) {
        this.attribute = attribute;
    }
}
