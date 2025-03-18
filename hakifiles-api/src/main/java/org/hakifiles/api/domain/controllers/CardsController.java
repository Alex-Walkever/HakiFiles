package org.hakifiles.api.domain.controllers;

import org.hakifiles.api.domain.dto.CardDto;
import org.hakifiles.api.domain.dto.PaginationDto;
import org.hakifiles.api.domain.entities.CardInfo;
import org.hakifiles.api.domain.entities.card.category.LeaderCard;
import org.hakifiles.api.domain.services.CardInfoService;
import org.hakifiles.api.domain.services.card.category.CharacterCardService;
import org.hakifiles.api.domain.services.card.category.EventCardService;
import org.hakifiles.api.domain.services.card.category.LeaderCardService;
import org.hakifiles.api.domain.services.card.category.StageCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CardsController {
    @Autowired
    CardInfoService cardInfoService;
    @Autowired
    CharacterCardService characterCardService;
    @Autowired
    EventCardService eventCardService;
    @Autowired
    LeaderCardService leaderCardService;
    @Autowired
    StageCardService stageCardService;

    @GetMapping("/api/cards/category")
    public ResponseEntity<List<CardInfo>> getCardsByCategory(@RequestBody PaginationDto paginationDto, @RequestParam CardInfo.Category category) {
        return ResponseEntity.ok(cardInfoService.getCardsByCategory(paginationDto, category));
    }

    @PostMapping("/api/cards")
    public ResponseEntity<?> addMultipleCards(@RequestBody List<CardDto> cardDto) {
        for (int i = 0; i < cardDto.size(); i++) {
            LeaderCard leaderCard = leaderCardService.saveCard(cardDto.get(i));
            System.out.println(leaderCard.toString());
            CardInfo info = cardInfoService.saveCard(cardDto.get(i));
            System.out.println(info.toString());
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/cards/{cardId}")
    public ResponseEntity<?> removeCard(@PathVariable String cardId) {
        Optional<CardInfo> ci = cardInfoService.getCardByCardId(cardId);
        if (ci.isPresent()) {
            CardInfo cardInfo = ci.get();
            if (cardInfo.getCategory() == CardInfo.Category.LEADER) {
                Optional<LeaderCard> lc = leaderCardService.getLeaderCardByCardId(cardId);
                if (lc.isPresent()) {
                    leaderCardService.delete(cardId);
                    cardInfoService.deleteCard(cardInfo.getId());
                    return ResponseEntity.noContent().build();
                }
            }
        }
        return ResponseEntity.notFound().build();
    }
}
