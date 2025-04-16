package org.hakifiles.api.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hakifiles.api.domain.dto.DeckListDto;
import org.hakifiles.api.infrastructure.utils.Games;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "Decks")
public class DeckList {
    @Id
    @Column(nullable = false)
    private String id;

    @NotBlank
    private String name;

    @Size(max = 2500)
    private String description;

    private String youtubeLink;

    @ElementCollection
    @MapKeyColumn(name = "deck_list")
    @CollectionTable(name = "list_attributes", joinColumns = @JoinColumn(name = "list_id"))
    private List<String> list;

    @ElementCollection
    @MapKeyColumn(name = "considering_list")
    @CollectionTable(name = "considering_attributes", joinColumns = @JoinColumn(name = "considering_id"))
    private List<String> consideringList;

    @ManyToOne
    @NotNull
    private CardInfo leader;

    @NotNull
    private Long userId;

    @NotNull
    private String username;

    @NotNull
    private Boolean isPrivate;

    @ElementCollection
    @CollectionTable(name = "games_attributes", joinColumns = @JoinColumn(name = "games_id"))
    private Set<Games> games;

    private Long views = 0L;
    private Long likes = 0L;

    LocalDateTime publishedOn;
    LocalDateTime updatedOn;

    String backgroundImage;

    public DeckList() {

    }

    public DeckList(DeckListDto dto) {
        setDeckListFromDto(dto);
    }

    public void setDeckListFromDto(DeckListDto dto) {
        name = dto.getName();
        description = (dto.getDescription() != null) ? dto.getDescription() : "";
        youtubeLink = (dto.getYoutubeLink() != null) ? dto.getYoutubeLink() : "";
        userId = dto.getUserId();
        list = new ArrayList<>();
        consideringList = new ArrayList<>();
        games = new HashSet<>();
        publishedOn = LocalDateTime.now();
        isPrivate = dto.isPrivate();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }

    public List<String> getList() {
        return list;
    }

    public void addOrRemoveToList(String card, Integer amount) {
        if (amount > 0) {
            for (int i = 0; i < amount; i++) {
                list.add(card);

            }
        } else {
            for (int i = 0; i < Math.abs(amount); i++) {
                list.remove(card);
            }
        }
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public CardInfo getLeader() {
        return leader;
    }

    public void setLeader(CardInfo leader) {
        this.leader = leader;
    }

    public LocalDateTime getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(LocalDateTime publishedOn) {
        this.publishedOn = publishedOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Set<Games> getGames() {
        return games;
    }

    public void addGame(Games game) {
        for (Games g : this.games) {
            if (g.getLeaderId().equals(game.getLeaderId())) {
                Games addGame = new Games(
                        g.getGames() + game.getGames(),
                        g.getWins() + game.getWins(),
                        g.getLooses() + game.getLooses(),
                        g.getLeaderId());
                this.games.remove(g);
                this.games.add(addGame);
                return;
            }
        }
        this.games.add(game);
    }

    public void setGames(Set<Games> games) {
        this.games = games;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public List<String> getConsideringList() {
        return consideringList;
    }

    public void setConsideringList(List<String> consideringList) {
        this.consideringList = consideringList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "DeckList{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", youtubeLink='" + youtubeLink + '\'' +
                ", list=" + list +
                ", leader=" + leader +
                ", publishedOn=" + publishedOn +
                ", updatedOn=" + updatedOn +
                '}';
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
}
