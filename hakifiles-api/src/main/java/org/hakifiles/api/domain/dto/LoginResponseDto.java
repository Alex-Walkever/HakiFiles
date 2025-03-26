package org.hakifiles.api.domain.dto;

import org.hakifiles.api.domain.entities.User;

public class LoginResponseDto {
    private User user;
    private String jwt;

    public LoginResponseDto() {
    }

    public LoginResponseDto(User user, String jwt) {
        this.user = user;
        this.jwt = jwt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
