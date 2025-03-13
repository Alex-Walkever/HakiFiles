package org.hakifiles.api.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Entity
@Table(name = "Cards")
public class CardInfo {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;
    @NotBlank
    private String cardId;
    @NotEmpty
    private Category category;
    @NotEmpty
    private Integer alternateArt;
    @NotBlank
    private String product;
    @NotBlank
    private String productCode;
    @NotEmpty
    private Rarity rarity;
    @NotEmpty
    private List<ColorCard> colorCards;

    public static enum Rarity {
        COMMON,
        UNCOMMON,
        RARE,
        SUPER_RARE,
        SECRET_RARE,
        LEADER
    }

    public static enum ColorCard {
        RED,
        GREEN,
        BLUE,
        PURPLE,
        BLACK,
        YELLOW
    }

    public static enum Category {
        LEADER,
        EVENT,
        STAGE,
        CHARACTER
    }

    public static enum Attribute {
        SLASH,
        STRIKE,
        RANGED,
        SPECIAL,
        WISDOM
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getAlternateArt() {
        return alternateArt;
    }

    public void setAlternateArt(Integer alternateArt) {
        this.alternateArt = alternateArt;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public List<ColorCard> getColorCards() {
        return colorCards;
    }

    public void setColorCards(List<ColorCard> colorCards) {
        this.colorCards = colorCards;
    }
}