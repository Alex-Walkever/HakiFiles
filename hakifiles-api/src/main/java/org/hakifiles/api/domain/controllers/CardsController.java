package org.hakifiles.api.domain.controllers;

import com.google.common.io.Files;
import org.hakifiles.api.domain.dto.CardDto;
import org.hakifiles.api.domain.dto.CardInfoWithCategoryDto;
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
import org.hakifiles.api.infrastructure.utils.GoogleCloudStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/cards")
@CrossOrigin
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

    final GoogleCloudStorage googleCloudStorage;

    @Autowired
    public CardsController(GoogleCloudStorage googleCloudStorage) {
        this.googleCloudStorage = googleCloudStorage;
    }

    @GetMapping("/search")
    public ResponseEntity<List<CardInfo>> getCardsByDynamicSearch(@RequestParam Map<String, String> requestParam) {
        if (requestParam.containsKey("category")) {
            Set<String> cardsId = new HashSet<>();
            List<String> category = new ArrayList<>(Arrays.stream(requestParam.get("category").split(",")).toList());
            if (category.contains("LEADER")) {
                List<LeaderCard> leadersCardsByFilter = leaderCardService.getCharactersCardsByFilter(requestParam);
                for (LeaderCard lcf : leadersCardsByFilter) {
                    cardsId.add(lcf.getCardId());
                }
            }
            if (category.contains("CHARACTER")) {
                List<CharacterCard> charactersCardsByFilter = characterCardService.getCharactersCardsByFilter(requestParam);
                for (CharacterCard ccf : charactersCardsByFilter) {
                    cardsId.add(ccf.getCardId());
                }
            }
            if (category.contains("EVENT")) {
                List<EventCard> eventCardsByFilter = eventCardService.getEventsCardsByFilter(requestParam);
                for (EventCard ecf : eventCardsByFilter) {
                    cardsId.add(ecf.getCardId());
                }
            }
            if (category.contains("STAGE")) {
                List<StageCard> stageCardsByFilter = stageCardService.getStagesCardsByFilter(requestParam);
                for (StageCard scf : stageCardsByFilter) {
                    cardsId.add(scf.getCardId());
                }
            }

            return ResponseEntity.ok(cardInfoService.getCardsByFilterAndCardsId(requestParam, cardsId.stream().toList()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/product")
    public ResponseEntity<List<CardInfo>> getCardsByProduct(@RequestParam String product) {
        return ResponseEntity.ok(cardInfoService.getCardsByProduct(product));
    }

    @GetMapping("/category")
    public ResponseEntity<List<CardInfo>> getCardsByCategory(@RequestBody PaginationDto paginationDto, @RequestParam CardInfo.Category category) {
        return ResponseEntity.ok(cardInfoService.getCardsByCategory(paginationDto, category));
    }

    @GetMapping("/block")
    public ResponseEntity<List<CardInfo>> getCardsByBlock(@RequestBody PaginationDto paginationDto, @RequestParam Integer block) {
        return ResponseEntity.ok(cardInfoService.getCardsByBlock(paginationDto, block));
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<CardInfoWithCategoryDto> getCardInfoByCardId(@PathVariable String cardId) {
        CardInfoWithCategoryDto cardWithCategory = getCardWithCategory(cardId);
        if (cardWithCategory != null) {
            return ResponseEntity.ok(cardWithCategory);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/deck-list")
    public ResponseEntity<List<CardInfoWithCategoryDto>> getDeckList(@RequestBody Map<String, List<String>> deckList) {
        List<CardInfoWithCategoryDto> cardList = new ArrayList<>();
        for (String dl : deckList.get("list")) {
            CardInfoWithCategoryDto cardWithCategory = getCardWithCategory(dl);
            if (cardWithCategory != null) {
                cardList.add(cardWithCategory);
            }
        }

        return ResponseEntity.ok(cardList);
    }


    @GetMapping("/characters/{cardId}")
    public ResponseEntity<CharacterCard> getCharacterDetails(@PathVariable String cardId) {
        Optional<CharacterCard> card = characterCardService.getCharacterCardByCardId(cardId);
        return card.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/events/{cardId}")
    public ResponseEntity<EventCard> getEventDetails(@PathVariable String cardId) {
        Optional<EventCard> card = eventCardService.getEventCardByCardId(cardId);
        return card.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/leaders/{cardId}")
    public ResponseEntity<LeaderCard> getLeaderDetails(@PathVariable String cardId) {
        Optional<LeaderCard> card = leaderCardService.getLeaderCardByCardId(cardId);
        return card.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search/leaders/{name}")
    public ResponseEntity<List<CardInfo>> getLeadersFromName(@PathVariable String name) {
        List<LeaderCard> cards = leaderCardService.getLeaderCardsByName(name);
        return ResponseEntity.ok(cardInfoService.getCardsByListCardId(cards.stream().map(LeaderCard::getCardId).toList()));
    }

    @GetMapping("/stages/{cardId}")
    public ResponseEntity<StageCard> getStageDetails(@PathVariable String cardId) {
        Optional<StageCard> card = stageCardService.getStageCardByCardId(cardId);
        return card.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{cardId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CardInfo> editCard(@RequestBody CardDto cardDto, @PathVariable String cardId) {
        Optional<CardInfo> cardInfo = cardInfoService.getCardByCardId(cardId);
        if (cardInfo.isPresent()) {
            boolean safeToModify = false;
            if (cardDto.category.equals(CardInfo.Category.LEADER.toString())) {
                LeaderCard leaderCard = leaderCardService.saveCard(cardDto);
                safeToModify = true;
            }
            if (cardDto.category.equals(CardInfo.Category.CHARACTER.toString())) {
                CharacterCard characterCard = characterCardService.saveCard(cardDto);
                safeToModify = true;
            }
            if (cardDto.category.equals(CardInfo.Category.STAGE.toString())) {
                StageCard stageCard = stageCardService.saveCard(cardDto);
                safeToModify = true;
            }
            if (cardDto.category.equals(CardInfo.Category.EVENT.toString())) {
                EventCard eventCard = eventCardService.saveCard(cardDto);
                safeToModify = true;
            }
            if (safeToModify) {
                return ResponseEntity.ok(cardInfoService.editCard(cardDto, cardInfo.get()));
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{cardId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addImageToCardInfo(@RequestBody MultipartFile image, @PathVariable String cardId) throws IOException {
        Optional<CardInfo> cardByCardId = cardInfoService.getCardByCardId(cardId);
        if (cardByCardId.isPresent()) {
            CardInfo cardInfo = cardByCardId.get();
            String url = googleCloudStorage.uploadImage(cardInfo.createUrl(), image.getBytes(), image.getContentType());
            cardInfo.setImage(url);
            return ResponseEntity.ok(cardInfoService.editCard(cardInfo));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addMultipleCards(@RequestBody List<CardDto> cardDto) {
        for (CardDto dto : cardDto) {
            boolean safeToAdd = false;
            if (dto.category.equals(CardInfo.Category.LEADER.toString())) {
                LeaderCard leaderCard = leaderCardService.saveCard(dto);
                safeToAdd = true;
            } else if (dto.category.equals(CardInfo.Category.CHARACTER.toString())) {
                CharacterCard characterCard = characterCardService.saveCard(dto);
                safeToAdd = true;
            } else if (dto.category.equals(CardInfo.Category.STAGE.toString())) {
                StageCard stageCard = stageCardService.saveCard(dto);
                safeToAdd = true;
            } else if (dto.category.equals(CardInfo.Category.EVENT.toString())) {
                EventCard eventCard = eventCardService.saveCard(dto);
                safeToAdd = true;
            }
            if (safeToAdd) {
                CardInfo info = cardInfoService.saveCard(dto);
            }
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cardId}")
    @PreAuthorize("hasRole('ADMIN')")
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

    private CardInfoWithCategoryDto getCardWithCategory(String cardId) {
        Optional<CardInfo> cardInfoDb = cardInfoService.getCardByCardId(cardId);
        if (cardInfoDb.isPresent()) {
            CardInfo cardInfo = cardInfoDb.get();
            CardInfoWithCategoryDto card = new CardInfoWithCategoryDto(cardInfo);
            if (cardInfo.getCategory().equals(CardInfo.Category.CHARACTER)) {
                Optional<CharacterCard> characterCardDb = characterCardService.getCharacterCardByCardId(cardId);
                characterCardDb.ifPresent(card::setCharacterCard);
            } else if (cardInfo.getCategory().equals(CardInfo.Category.EVENT)) {
                Optional<EventCard> eventCardDb = eventCardService.getEventCardByCardId(cardId);
                eventCardDb.ifPresent(card::setEventCard);
            } else if (cardInfo.getCategory().equals(CardInfo.Category.LEADER)) {
                Optional<LeaderCard> leaderCardDb = leaderCardService.getLeaderCardByCardId(cardId);
                leaderCardDb.ifPresent(card::setLeaderCard);
            } else if (cardInfo.getCategory().equals(CardInfo.Category.STAGE)) {
                Optional<StageCard> stageCardDb = stageCardService.getStageCardByCardId(cardId);
                stageCardDb.ifPresent(card::setStageCard);
            }
            return card;
        }
        return null;
    }
}
