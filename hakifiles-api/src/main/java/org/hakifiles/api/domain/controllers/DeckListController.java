package org.hakifiles.api.domain.controllers;

import jakarta.validation.Valid;
import org.hakifiles.api.domain.entities.DeckList;
import org.hakifiles.api.domain.services.DeckListService;
import org.hakifiles.api.domain.services.UserService;
import org.hakifiles.api.infrastructure.tools.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/decks")
public class DeckListController {
    @Autowired
    DeckListService deckListService;

    @Autowired
    UserService userService;

    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> createDeck(@Valid @RequestBody DeckList list, BindingResult result) {
        if (result.hasErrors()) {
            return Errors.validate(result);
        }

        return ResponseEntity.ok().build();
    }
}
