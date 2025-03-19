package org.hakifiles.api.domain.controllers;

import org.hakifiles.api.domain.dto.CardDto;
import org.hakifiles.api.domain.dto.PaginationDto;
import org.hakifiles.api.domain.entities.CardInfo;
import org.hakifiles.api.domain.entities.card.category.CharacterCard;
import org.hakifiles.api.domain.entities.card.category.EventCard;
import org.hakifiles.api.domain.entities.card.category.LeaderCard;
import org.hakifiles.api.domain.entities.card.category.StageCard;
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

    @GetMapping("/api/cards/block")
    public ResponseEntity<List<CardInfo>> getCardsByBlock(@RequestBody PaginationDto paginationDto, @RequestParam Integer Block) {
        return ResponseEntity.ok(cardInfoService.getCardsByBlock(paginationDto, Block));
    }

    @GetMapping("/api/cards/{cardId}")
    public ResponseEntity<CardInfo> getCardInfoByCardId(@PathVariable String cardId) {
        Optional<CardInfo> card = cardInfoService.getCardByCardId(cardId);
        return card.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/api/cards/characters/{cardId}")
    public ResponseEntity<CharacterCard> getCharacterDetails(@PathVariable String cardId) {
        Optional<CharacterCard> card = characterCardService.getCharacterCardByCardId(cardId);
        return card.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/api/cards/events/{cardId}")
    public ResponseEntity<EventCard> getEventDetails(@PathVariable String cardId) {
        Optional<EventCard> card = eventCardService.getEventCardByCardId(cardId);
        return card.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/api/cards/leaders/{cardId}")
    public ResponseEntity<LeaderCard> getLeaderDetails(@PathVariable String cardId) {
        Optional<LeaderCard> card = leaderCardService.getLeaderCardByCardId(cardId);
        return card.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/api/cards/stages/{cardId}")
    public ResponseEntity<StageCard> getStageDetails(@PathVariable String cardId) {
        Optional<StageCard> card = stageCardService.getStageCardByCardId(cardId);
        return card.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/api/cards")
    public ResponseEntity<?> addMultipleCards(@RequestBody List<CardDto> cardDto) {
        for (CardDto dto : cardDto) {
            boolean safeToAdd = false;
            if (dto.category.equals(CardInfo.Category.LEADER.toString())) {
                LeaderCard leaderCard = leaderCardService.saveCard(dto);
                safeToAdd = true;
                System.out.println(leaderCard.toString());
            } else if (dto.category.equals(CardInfo.Category.CHARACTER.toString())) {
                CharacterCard characterCard = characterCardService.saveCard(dto);
                safeToAdd = true;
                System.out.println(characterCard.toString());
            } else if (dto.category.equals(CardInfo.Category.STAGE.toString())) {
                StageCard stageCard = stageCardService.saveCard(dto);
                safeToAdd = true;
                System.out.println(stageCard.toString());
            } else if (dto.category.equals(CardInfo.Category.EVENT.toString())) {
                EventCard eventCard = eventCardService.saveCard(dto);
                safeToAdd = true;
                System.out.println(eventCard.toString());
            }
            if (safeToAdd) {
                CardInfo info = cardInfoService.saveCard(dto);
                System.out.println(info.toString());
            }
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/cards/{cardId}")
    public ResponseEntity<?> removeCard(@PathVariable String cardId) {
        Optional<CardInfo> ci = cardInfoService.getCardByCardId(cardId);
        if (ci.isPresent()) {
            CardInfo cardInfo = ci.get();
            boolean safeCardDelete = false;
            if (cardInfo.getCategory() == CardInfo.Category.LEADER) {
                Optional<LeaderCard> lc = leaderCardService.getLeaderCardByCardId(cardId);
                if (lc.isPresent()) {
                    leaderCardService.delete(cardId);
                    safeCardDelete = true;
                }
            } else if (cardInfo.getCategory() == CardInfo.Category.CHARACTER) {
                Optional<CharacterCard> cc = characterCardService.getCharacterCardByCardId(cardId);
                if (cc.isPresent()) {
                    characterCardService.delete(cardId);
                    safeCardDelete = true;
                }

            } else if (cardInfo.getCategory() == CardInfo.Category.STAGE) {
                Optional<StageCard> sc = stageCardService.getStageCardByCardId(cardId);
                if (sc.isPresent()) {
                    stageCardService.delete(cardId);
                    safeCardDelete = true;
                }
            } else if (cardInfo.getCategory() == CardInfo.Category.EVENT) {
                Optional<EventCard> ec = eventCardService.getEventCardByCardId(cardId);
                if (ec.isPresent()) {
                    eventCardService.delete(cardId);
                    safeCardDelete = true;
                }
            }
            if (safeCardDelete) {
                cardInfoService.deleteCard(cardInfo.getId());
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.notFound().build();
    }
}
