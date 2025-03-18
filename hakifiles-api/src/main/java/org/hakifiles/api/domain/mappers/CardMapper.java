package org.hakifiles.api.domain.mappers;

import org.hakifiles.api.domain.dto.CardDto;
import org.hakifiles.api.domain.entities.CardInfo;
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
        if (cardDto.type.contains("/")) {
            String[] stringType = cardDto.type.split("/");
            leader.setType(Arrays.stream(stringType).toList());
        } else {
            List<String> typeList = new ArrayList<>();
            typeList.add(cardDto.type);
            leader.setType(typeList);
        }
        leader.setEffects(cardDto.effects);
        leader.setLife(Integer.parseInt(cardDto.life));
        leader.setPower(Integer.parseInt(cardDto.power));
        List<CardInfo.Attribute> attributes = new ArrayList<>();
        if (cardDto.attribute.contains("/")) {
            String[] stringAttribute = cardDto.attribute.split("/");
            for (String s : stringAttribute) {
                attributes.add(CardInfo.Attribute.valueOf(s));
            }
        } else {
            attributes.add(CardInfo.Attribute.valueOf(cardDto.attribute));
        }
        leader.setAttribute(attributes);
        return leader;
    }
}
