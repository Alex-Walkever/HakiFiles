package org.hakifiles.api.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.hakifiles.api.infrastructure.tools.CollectionObject;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotEmpty
    @Column(unique = true)
    private String name;

    @NotBlank
    private String password;

    @NotEmpty
    @Email
    @Column(unique = true)
    private String email;

    private List<Long> deckList;

//    @ElementCollection
//    private List<CollectionObject> collection;

    private List<String> rol;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Long> getDeckList() {
        return deckList;
    }

    public void setDeckList(List<Long> deckList) {
        this.deckList = deckList;
    }

    public void addToDeckList(Long deckId) {
        this.deckList.add(deckId);
    }

    public void removeDeckList(Long deckId) {
        this.deckList.remove(deckId);
    }

//    public List<CollectionObject> getCollection() {
//        return collection;
//    }
//
//    public void setCollection(List<CollectionObject> collection) {
//        this.collection = collection;
//    }
//
//    public void addToCollection(CollectionObject collection) {
//        this.collection.add(collection);
//    }
//
//    public void removeCollection(CollectionObject cardId) {
//        this.collection.remove(cardId);
//    }

    public List<String> getRol() {
        return rol;
    }

    public void setRol(List<String> rol) {
        this.rol = rol;
    }

    public void setUser(User user) {
        name = user.name;
        password = user.password;
        email = user.email;
        deckList = user.deckList;
        //collection = user.collection;
        rol = user.rol;
    }
}
