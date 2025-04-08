package org.hakifiles.api.domain.dto;

import org.hakifiles.api.domain.entities.CardInfo;
import org.hakifiles.api.domain.entities.card.category.CharacterCard;
import org.hakifiles.api.domain.entities.card.category.EventCard;
import org.hakifiles.api.domain.entities.card.category.LeaderCard;
import org.hakifiles.api.domain.entities.card.category.StageCard;

public class CardInfoWithCategoryDto {
    private CardInfo cardInfo;
    private CharacterCard characterCard;
    private EventCard eventCard;
    private LeaderCard leaderCard;
    private StageCard stageCard;

    public CardInfoWithCategoryDto() {
    }

    public CardInfoWithCategoryDto(CardInfo cardInfo) {
        this.cardInfo = cardInfo;
    }

    public CardInfo getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(CardInfo cardInfo) {
        this.cardInfo = cardInfo;
    }

    public CharacterCard getCharacterCard() {
        return characterCard;
    }

    public void setCharacterCard(CharacterCard characterCard) {
        this.characterCard = characterCard;
    }

    public EventCard getEventCard() {
        return eventCard;
    }

    public void setEventCard(EventCard eventCard) {
        this.eventCard = eventCard;
    }

    public LeaderCard getLeaderCard() {
        return leaderCard;
    }

    public void setLeaderCard(LeaderCard leaderCard) {
        this.leaderCard = leaderCard;
    }

    public StageCard getStageCard() {
        return stageCard;
    }

    public void setStageCard(StageCard stageCard) {
        this.stageCard = stageCard;
    }
}
