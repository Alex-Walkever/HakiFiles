package org.hakifiles.api.domain.controllers;

import jakarta.validation.Valid;
import org.hakifiles.api.domain.dto.AddCardDto;
import org.hakifiles.api.domain.dto.DeckListDto;
import org.hakifiles.api.domain.entities.CardInfo;
import org.hakifiles.api.domain.entities.DeckList;
import org.hakifiles.api.domain.entities.User;
import org.hakifiles.api.domain.services.AuthenticationService;
import org.hakifiles.api.domain.services.CardInfoService;
import org.hakifiles.api.domain.services.DeckListService;
import org.hakifiles.api.domain.services.UserService;
import org.hakifiles.api.infrastructure.tools.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/decks")
public class DeckListController {
    @Autowired
    private DeckListService deckListService;

    @Autowired
    private CardInfoService cardInfoService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createDeck(@Valid @RequestBody DeckListDto list, BindingResult result) {
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
            deckList.setId(randomUniqueString());
            leader.addCardUsage(1L);
            cardInfoService.editCard(leader);
            DeckList savedDeckList = deckListService.saveDeckList(deckList);
            User user = userById.get();
            user.addDeckList(savedDeckList.getId());
            userService.editUser(user);
            return ResponseEntity.ok(savedDeckList);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("Error", "Leader Card is not found"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeckList> deckDetails(@PathVariable String id) {
        Optional<DeckList> deckListById = deckListService.getDeckListById(id);
        return deckListById.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    @PreAuthorize("this.getAuthenticationService().hasUserFromDeckList( #id ) or hasRole('ADMIN')")
    public ResponseEntity<?> addCardToDeckList(@Valid @RequestBody List<AddCardDto> addCardDto, BindingResult result, @PathVariable String id) {
        if (result.hasErrors()) {
            return Errors.validate(result);
        }

        for (AddCardDto dto : addCardDto) {
            Optional<DeckList> deckListById = deckListService.getDeckListById(id);
            if (deckListById.isPresent()) {
                Optional<CardInfo> cardByCardId = cardInfoService.getCardByCardId(dto.getCardId());
                if (cardByCardId.isPresent()) {
                    DeckList deckList = deckListById.get();
                    CardInfo cardInfo = cardByCardId.get();
                    deckList.addOrRemoveToList(cardInfo.getCardId(), dto.getAmount());
                    deckList.setUpdatedOn(LocalDateTime.now());
                    deckListService.saveDeckList(deckList);
                    cardInfo.addCardUsage(dto.getAmount().longValue());
                    cardInfoService.editCard(cardInfo);
                } else {
                    return ResponseEntity.badRequest().build();

                }
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("this.getAuthenticationService().hasUserFromDeckList( #id ) or hasRole('ADMIN')")
    public ResponseEntity<?> removeDeck(@PathVariable String id) {
        Optional<DeckList> deckListById = deckListService.getDeckListById(id);

        if (deckListById.isPresent()) {
            DeckList deckList = deckListById.get();
            Optional<User> userById = userService.getUserById(deckList.getUserId());
            if (userById.isPresent()) {
                User user = userById.get();
                user.removeDeckList(deckList.getId());
                cardInfoService.removeUsage(deckList);
                userService.editUser(user);
                deckListService.removeDeckList(deckList.getId());
                return ResponseEntity.noContent().build();
            }
        }

        return ResponseEntity.notFound().build();
    }

    private String randomUniqueString() {
        String uniqueString = randomString();
        Optional<DeckList> listById = deckListService.getDeckListById(uniqueString);
        while (listById.isPresent()) {
            uniqueString = randomString();
            listById = deckListService.getDeckListById(uniqueString);
        }
        return uniqueString;
    }

    private String randomString() {
        String charCandidates = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890-_abcdefghijklmnopqrstwxyz";
        StringBuilder builder = new StringBuilder();
        Random rnd = new Random();
        while (builder.length() < 22) { // length of the random string.
            int index = (int) (rnd.nextFloat() * charCandidates.length());
            builder.append(charCandidates.charAt(index));
        }
        return builder.toString();
    }
}
