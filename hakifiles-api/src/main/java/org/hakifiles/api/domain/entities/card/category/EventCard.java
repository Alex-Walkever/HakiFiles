package org.hakifiles.api.domain.entities.card.category;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "Events")
public class EventCard {
    @Id
    @Column(nullable = false)
    private String CardId;

    @NotBlank
    private String name;
    @NotEmpty
    private List<String> type;
    @NotEmpty
    @Size(max = 1000)
    private String effects;
    @Size(max = 1000)
    private String triggerEffect;
    @NotNull
    private Integer cost;


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

    @Override
    public String toString() {
        return "EventCard{" +
                "CardId='" + CardId + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", effects=" + effects +
                ", triggerEffect='" + triggerEffect + '\'' +
                ", cost=" + cost +
                '}';
    }
}
