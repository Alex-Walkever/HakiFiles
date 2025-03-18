package org.hakifiles.api.domain.entities.card.category;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(max = 1000)
    private String effects;
    @Size(max = 1000)
    private String triggerEffect;
    @NotNull
    private Integer cost;
    @NotNull
    private Integer power;
    @NotNull
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

    public String getEffects() {
        return effects;
    }

    public void setEffects(String effects) {
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

    @Override
    public String toString() {
        return "CharacterCard{" +
                "CardId='" + CardId + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", effects=" + effects +
                ", triggerEffect='" + triggerEffect + '\'' +
                ", cost=" + cost +
                ", power=" + power +
                ", counterPower=" + counterPower +
                ", attribute=" + attribute +
                '}';
    }
}
