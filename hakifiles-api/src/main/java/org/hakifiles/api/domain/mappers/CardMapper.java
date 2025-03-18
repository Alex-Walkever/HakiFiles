package org.hakifiles.api.domain.mappers;

import org.hakifiles.api.domain.dto.CardDto;
import org.hakifiles.api.domain.entities.CardInfo;

import java.util.ArrayList;
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
        if (cardDto.colorsCard.contains("/")) {
            String[] stringColor = cardDto.colorsCard.split("/");
            List<CardInfo.ColorCard> colors = new ArrayList<>();
            for (String s : stringColor) {
                colors.add(CardInfo.ColorCard.valueOf(s));
            }
            info.setColorCards(colors);
        } else {
            List<CardInfo.ColorCard> color = new ArrayList<>();
            color.add(CardInfo.ColorCard.valueOf(cardDto.colorsCard));
            info.setColorCards(color);
        }
        info.setTournamentStatus(CardInfo.TournamentStatus.valueOf(cardDto.tournamentStatus));
        return info;
    }
}
