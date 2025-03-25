package org.hakifiles.api.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.hakifiles.api.domain.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "user_id")
    private Long userId;

    @NotEmpty
    @Column(unique = true)
    private String name;

    @NotBlank
    private String password;

    @NotEmpty
    @Email
    @Column(unique = true)
    private String email;

    @ElementCollection
    private Set<Long> deckList;

    //    @ElementCollection
//    private List<CollectionObject> collection;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role_junction",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> authorities;

    public User() {
        super();
        this.authorities = new HashSet<>();
        this.deckList = new HashSet<>();
    }

    public User(String name, String password, String email, Set<Long> deckList, Set<Role> authorities) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.deckList = deckList;
        this.authorities = authorities;
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

    public Set<Long> getDeckList() {
        return deckList;
    }

    public void setDeckList(Set<Long> deckList) {
        this.deckList = deckList;
    }

    public Set<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    public void addAuthority(Role authority) {
        this.authorities.add(authority);
    }

    public void setUserDto(UserDto userDto, PasswordEncoder encoder) {
        if (userDto.getName() != null && !userDto.getName().isEmpty()) {
            name = userDto.getName();
        }
        if (userDto.getEmail() != null && !userDto.getEmail().isEmpty()) {
            email = userDto.getEmail();
        }
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            password = encoder.encode(userDto.getPassword());
        }
    }
}
