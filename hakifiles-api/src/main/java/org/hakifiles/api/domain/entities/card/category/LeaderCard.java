package org.hakifiles.api.domain.entities.card.category;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    private String effects;
    @NotNull
    private Integer life;
    @NotNull
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

    public String getEffects() {
        return effects;
    }

    public void setEffects(String effects) {
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

    @Override
    public String toString() {
        return "LeaderCard{" +
                "CardId='" + CardId + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", effects=" + effects +
                ", life=" + life +
                ", power=" + power +
                ", attribute=" + attribute +
                '}';
    }
}
