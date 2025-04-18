package org.hakifiles.api.domain.controllers;

import jakarta.validation.Valid;
import org.hakifiles.api.domain.dto.AddCardDto;
import org.hakifiles.api.domain.dto.DeckListDto;
import org.hakifiles.api.domain.dto.GamesDto;
import org.hakifiles.api.domain.entities.CardInfo;
import org.hakifiles.api.domain.entities.DeckList;
import org.hakifiles.api.domain.entities.User;
import org.hakifiles.api.domain.repositories.DeckListRepository;
import org.hakifiles.api.domain.services.AuthenticationService;
import org.hakifiles.api.domain.services.CardInfoService;
import org.hakifiles.api.domain.services.DeckListService;
import org.hakifiles.api.domain.services.UserService;
import org.hakifiles.api.infrastructure.tools.Errors;
import org.hakifiles.api.infrastructure.utils.Games;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/decks")
@CrossOrigin
public class DeckListController {
    @Autowired
    private DeckListService deckListService;

    @Autowired
    private CardInfoService cardInfoService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private DeckListRepository deckListRepository;

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    @PostMapping()
    @PreAuthorize("hasRole('USER') and this.getAuthenticationService().hasUserId( #list.getUserId() )")
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
            deckList.setBackgroundImage(leader.getImage());
            deckList.setId(randomUniqueString());
            User user = userById.get();
            deckList.setUsername(user.getName());
            leader.addCardUsage(1L);
            cardInfoService.editCard(leader);
            DeckList savedDeckList = deckListService.saveDeckList(deckList);
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

    @GetMapping("/user/{username}")
    public ResponseEntity<List<DeckList>> getDecksFromUser(@PathVariable String username) {
        Optional<User> userByName = userService.getUserByName(username);
        return userByName.map(
                user -> ResponseEntity.ok(
                        deckListService.getAllDecksWithId(
                                user.getDeckList().stream().toList()))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/multi-details")
    public ResponseEntity<List<DeckList>> decksDetails(@RequestBody List<String> ids) {
        return ResponseEntity.ok(deckListService.getAllDecksWithId(ids));
    }


    @PutMapping("/games/{id}")
    @PreAuthorize("this.getAuthenticationService().hasUserFromDeckList( #id ) or hasRole('ADMIN')")
    public ResponseEntity<?> addGameToDeckList(@Valid @RequestBody GamesDto gameDto, BindingResult result, @PathVariable String id) {
        if (result.hasErrors()) {
            return Errors.validate(result);
        }

        if (gameDto.getGames() == null && gameDto.getLooses() == null && gameDto.getWins() == null)
            return ResponseEntity.badRequest().build();

        if (gameDto.getLooses() == null && gameDto.getWins() == null) return ResponseEntity.badRequest().build();

        Optional<CardInfo> cardByCardId = cardInfoService.getCardByCardId(gameDto.getLeaderId());
        if (cardByCardId.isPresent()) {
            CardInfo card = cardByCardId.get();
            if (!card.getCategory().equals(CardInfo.Category.LEADER)) {
                return ResponseEntity.badRequest().build();
            }
            Optional<DeckList> deckListById = deckListService.getDeckListById(id);
            if (deckListById.isPresent()) {
                DeckList deckList = deckListById.get();
                Games game = new Games(
                        (gameDto.getGames() != null) ? gameDto.getGames() : 1,
                        (gameDto.getWins() != null) ? gameDto.getWins() : 0,
                        (gameDto.getLooses() != null) ? gameDto.getLooses() : 0,
                        gameDto.getLeaderId());
                deckList.addGame(game);
                return ResponseEntity.ok(deckListService.saveDeckList(deckList));
            }
        }

        return ResponseEntity.badRequest().build();
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
