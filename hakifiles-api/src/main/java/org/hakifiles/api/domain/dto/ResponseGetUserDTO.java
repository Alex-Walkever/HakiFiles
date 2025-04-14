package org.hakifiles.api.domain.dto;

import jakarta.validation.constraints.Email;
import org.hakifiles.api.domain.entities.User;

import java.util.Set;

public class ResponseGetUserDTO {
    private Long userId;

    private String name;

    private String email;

    private Set<String> deckList;

    private Set<String> likedDeckList;

    public ResponseGetUserDTO(User user) {
        userId = user.getUserId();
        name = user.getName();
        email = user.getEmail();
        deckList = user.getDeckList();
        likedDeckList = user.getLikedDecks();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getDeckList() {
        return deckList;
    }

    public void setDeckList(Set<String> deckList) {
        this.deckList = deckList;
    }

    public Set<String> getLikedDeckList() {
        return likedDeckList;
    }

    public void setLikedDeckList(Set<String> likedDeckList) {
        this.likedDeckList = likedDeckList;
    }
}
