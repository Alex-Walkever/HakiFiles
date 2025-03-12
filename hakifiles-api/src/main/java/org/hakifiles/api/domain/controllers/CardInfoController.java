package org.hakifiles.api.domain.controllers;

import org.hakifiles.api.domain.dto.PaginationDto;
import org.hakifiles.api.domain.entities.CardInfo;
import org.hakifiles.api.domain.services.CardInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class CardInfoController {
    @Autowired
    private CardInfoService service;

    @GetMapping("/api/cards/category")
    public ResponseEntity<List<CardInfo>> getCardsByCategory(@RequestBody PaginationDto paginationDto, @RequestParam CardInfo.Category category) {
        return ResponseEntity.ok(service.getCardsByCategory(paginationDto, category));
    }
}
