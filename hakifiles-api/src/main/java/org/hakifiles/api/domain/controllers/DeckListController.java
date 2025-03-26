package org.hakifiles.api.domain.controllers;

import jakarta.validation.Valid;
import org.hakifiles.api.domain.dto.DeckListDto;
import org.hakifiles.api.domain.entities.CardInfo;
import org.hakifiles.api.domain.entities.DeckList;
import org.hakifiles.api.domain.entities.User;
import org.hakifiles.api.domain.services.CardInfoService;
import org.hakifiles.api.domain.services.DeckListService;
import org.hakifiles.api.domain.services.UserService;
import org.hakifiles.api.infrastructure.tools.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/decks")
public class DeckListController {
    @Autowired
    DeckListService deckListService;

    @Autowired
    CardInfoService cardInfoService;

    @Autowired
    UserService userService;

    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> createDeck(@Valid @RequestBody DeckListDto list, BindingResult result) {
        if (result.hasErrors()) {
            return Errors.validate(result);
        }

        DeckList deckList = new DeckList(list);

        Optional<CardInfo> cardInfo = cardInfoService.getCardByCardId(list.getLeader());
        Optional<User> userById = userService.getUserById(list.getUserId());
        if (cardInfo.isPresent() && userById.isPresent()) {
            CardInfo leader = cardInfo.get();
            if (leader.getCategory() != CardInfo.Category.LEADER) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("Error", "Card selected is not a leader"));
            }
            deckList.setLeader(leader);
            DeckList savedDeckList = deckListService.saveDeckList(deckList);
            User user = userById.get();
            user.addDekList(savedDeckList.getId());
            userService.editUser(user);
            return ResponseEntity.ok(savedDeckList);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("Error", "Leader Card is not found"));

    }
}
