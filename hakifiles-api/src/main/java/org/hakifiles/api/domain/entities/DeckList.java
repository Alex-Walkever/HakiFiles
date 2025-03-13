package org.hakifiles.api.domain.entities;

import jakarta.persistence.*;

import java.util.Map;

@Entity
@Table(name = "Decks")
public class DeckList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;

    private String description;

    private String youtubeLink;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    private Map<CardInfo, Integer> list;

//    @ElementCollection(cascade = CascadeType.ALL, orphanRemoval = true)
//    private CardInfo leader;

}
