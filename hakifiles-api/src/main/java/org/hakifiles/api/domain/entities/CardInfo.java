package org.hakifiles.api.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

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
    @NotNull
    private Category category;
    @NotNull
    private Integer alternateArt;
    @NotBlank
    private String product;
    @NotBlank
    private String productCode;
    @NotNull
    private Rarity rarity;
    @NotEmpty
    @ElementCollection
    private List<ColorCard> colorCards;

    @NotNull
    private Integer block;

    @NotNull
    private TournamentStatus tournamentStatus;

    public static enum TournamentStatus {
        LEGAL,
        BANNED
    }

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

    public Integer getBlock() {
        return block;
    }

    public void setBlock(Integer block) {
        this.block = block;
    }

    public TournamentStatus getTournamentStatus() {
        return tournamentStatus;
    }

    public void setTournamentStatus(TournamentStatus tournamentStatus) {
        this.tournamentStatus = tournamentStatus;
    }

    @Override
    public String toString() {
        return "CardInfo{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", cardId='" + cardId + '\'' +
                ", category=" + category +
                ", alternateArt=" + alternateArt +
                ", product='" + product + '\'' +
                ", productCode='" + productCode + '\'' +
                ", rarity=" + rarity +
                ", colorCards=" + colorCards +
                ", tournamentStatus=" + tournamentStatus +
                '}';
    }
}