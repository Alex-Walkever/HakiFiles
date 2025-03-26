package org.hakifiles.api.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

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
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
