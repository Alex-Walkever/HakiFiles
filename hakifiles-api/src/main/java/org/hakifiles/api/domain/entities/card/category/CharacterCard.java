package org.hakifiles.api.domain.entities.card.category;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.hakifiles.api.domain.entities.CardInfo;

import java.util.List;

@Entity
@Table(name = "Characters")
public class CharacterCard {
    @Id
    @Column(nullable = false)
    private String CardId;

    @NotBlank
    private String name;
    @NotEmpty
    private List<String> type;
    private List<String> effects;
    private String triggerEffect;
    @NotEmpty
    private Integer cost;
    @NotEmpty
    private Integer power;
    @NotEmpty
    private Integer counterPower;
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

    public String getTriggerEffect() {
        return triggerEffect;
    }

    public void setTriggerEffect(String triggerEffect) {
        this.triggerEffect = triggerEffect;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getCounterPower() {
        return counterPower;
    }

    public void setCounterPower(Integer counterPower) {
        this.counterPower = counterPower;
    }

    public List<CardInfo.Attribute> getAttribute() {
        return attribute;
    }

    public void setAttribute(List<CardInfo.Attribute> attribute) {
        this.attribute = attribute;
    }
}
