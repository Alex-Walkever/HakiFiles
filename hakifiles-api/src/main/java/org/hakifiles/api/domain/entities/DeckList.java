package org.hakifiles.api.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hakifiles.api.domain.dto.DeckListDto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
    @Column(name = "value")
    @CollectionTable(name = "list_attributes", joinColumns = @JoinColumn(name = "list_id"))
    private Map<String, Integer> list;

    @ManyToOne
    @NotNull
    private CardInfo leader;

    @NotNull
    private Long userId;

    @NotNull
    private Boolean isPrivate;

    LocalDateTime publishedOn;
    LocalDateTime updatedOn;

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
        list = new HashMap<>();
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

    public Map<String, Integer> getList() {
        return list;
    }

    public void addOrRemoveToList(String card, Integer amount) {
        Integer value = amount;
        if (list.containsKey(card)) {
            value += list.get(card);
            if (value > 0) {
                list.put(card, value);
            } else {
                list.remove(card);
            }
            return;
        }
        list.put(card, value);
    }

    public void setList(Map<String, Integer> list) {
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
}
