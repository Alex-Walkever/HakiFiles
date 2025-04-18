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
    private Series series;
    @NotNull
    private Rarity rarity;
    @NotEmpty
    @ElementCollection
    private List<ColorCard> colorCards;

    @NotNull
    private Integer block;

    @NotNull
    private TournamentStatus tournamentStatus;

    private Long cardUsage = 0L;

    public static enum TournamentStatus {
        LEGAL,
        BANNED,
        UNRELEASED
    }

    public static enum Rarity {
        COMMON,
        UNCOMMON,
        RARE,
        SUPER_RARE,
        SECRET_RARE,
        LEADER,
        PROMO
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

    public static enum Series {
        OP,
        ST,
        EB,
        PRB,
        P
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

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public Long getCardUsage() {
        return cardUsage;
    }

    public void addCardUsage(Long use) {
        cardUsage += use;
        if (cardUsage < 0L)
            cardUsage = 0L;
    }

    public void removeCardUsage(Long use) {
        cardUsage -= use;
        if (cardUsage < 0L)
            cardUsage = 0L;
    }

    public void setCardUsage(Long cardUsage) {
        this.cardUsage = cardUsage;
    }

    public String createUrl() {
        String url = productCode + "/" + cardId;
        return (alternateArt == 0 ? url : url + "_" + alternateArt) + ".png";
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