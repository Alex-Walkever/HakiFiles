package org.hakifiles.api.domain.entities.card.category;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

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
    private List<String> effects;
    private String triggerEffect;
    @NotEmpty
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
}
