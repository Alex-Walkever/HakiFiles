package org.hakifiles.api.domain.mappers;

import org.hakifiles.api.domain.dto.CardDto;
import org.hakifiles.api.domain.entities.CardInfo;
import org.hakifiles.api.domain.entities.card.category.CharacterCard;
import org.hakifiles.api.domain.entities.card.category.LeaderCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CardMapper {

    public static CardInfo toCardInfo(CardDto cardDto) {
        CardInfo info = new CardInfo();

        info.setImage(cardDto.image);
        info.setCardId(cardDto.cardId);
        info.setCategory(CardInfo.Category.valueOf(cardDto.category));
        if (cardDto.alternateArt == null) {
            info.setAlternateArt(0);
        } else {
            info.setAlternateArt(Integer.parseInt(cardDto.alternateArt));
        }
        info.setProduct(cardDto.product);
        info.setProductCode(cardDto.productCode);
        String rarity = cardDto.rarity;
        if (rarity.contains(" ")) {
            rarity = rarity.replace(" ", "_");
        }
        info.setRarity(CardInfo.Rarity.valueOf(rarity));
        List<CardInfo.ColorCard> colors = new ArrayList<>();
        if (cardDto.colorsCard.contains("/")) {
            String[] stringColor = cardDto.colorsCard.split("/");
            for (String s : stringColor) {
                colors.add(CardInfo.ColorCard.valueOf(s));
            }
            info.setColorCards(colors);
        } else {
            colors.add(CardInfo.ColorCard.valueOf(cardDto.colorsCard));
            info.setColorCards(colors);
        }
        info.setTournamentStatus(CardInfo.TournamentStatus.valueOf(cardDto.tournamentStatus));
        return info;
    }

    public static LeaderCard toLeaderCard(CardDto cardDto) {
        LeaderCard leader = new LeaderCard();
        leader.setCardId(cardDto.cardId);
        leader.setName(cardDto.name);
        leader.setType(getTypeList(cardDto.type));
        leader.setEffects(cardDto.effects);
        leader.setLife(Integer.parseInt(cardDto.life));
        leader.setPower(Integer.parseInt(cardDto.power));
        leader.setAttribute(getAttributeList(cardDto.attribute));
        return leader;
    }

    public static CharacterCard toCharacterCard(CardDto cardDto) {
        CharacterCard character = new CharacterCard();
        character.setCardId(cardDto.cardId);
        character.setName(cardDto.name);
        character.setType(getTypeList(cardDto.type));
        character.setEffects(cardDto.effects);
        character.setTriggerEffect(cardDto.triggerEffect);
        character.setCost(Integer.parseInt(cardDto.cost));
        character.setPower(Integer.parseInt(cardDto.power));
        character.setCounterPower(Integer.parseInt(cardDto.counterPower));
        character.setAttribute(getAttributeList(cardDto.attribute));
        return character;
    }

    private static List<CardInfo.Attribute> getAttributeList(String attribute) {
        List<CardInfo.Attribute> attributes = new ArrayList<>();
        if (attribute.contains("/")) {
            String[] stringAttribute = attribute.split("/");
            for (String s : stringAttribute) {
                attributes.add(CardInfo.Attribute.valueOf(s));
            }
        } else {
            attributes.add(CardInfo.Attribute.valueOf(attribute));
        }
        return attributes;
    }

    private static List<String> getTypeList(String type) {
        List<String> typeList = new ArrayList<>();
        if (type.contains("/")) {
            String[] stringType = type.split("/");
            typeList.addAll(Arrays.asList(stringType));
        } else {
            typeList.add(type);
        }
        return typeList;
    }
}
