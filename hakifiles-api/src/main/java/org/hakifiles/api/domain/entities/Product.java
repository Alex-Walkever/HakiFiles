package org.hakifiles.api.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Products")
public class Product {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String code;
    @NotBlank
    private String name;
    @NotBlank
    private String releaseDate;
    @NotNull
    private Integer amountOfCards;
    @NotNull
    private Integer block;

    public void setProduct(Product product) {
        code = product.getCode();
        name = product.getName();
        releaseDate = product.getReleaseDate();
        amountOfCards = product.getAmountOfCards();
        block = product.getBlock();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getAmountOfCards() {
        return amountOfCards;
    }

    public void setAmountOfCards(Integer amountOfCards) {
        this.amountOfCards = amountOfCards;
    }

    public Integer getBlock() {
        return block;
    }

    public void setBlock(Integer block) {
        this.block = block;
    }
}
