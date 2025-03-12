package org.hakifiles.api.domain.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Cards")
public class CardInfo {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String image;
    private String cardId;
    private List<String> type;
    private Category category;
    private Integer alternateArt;
    private String Product;
    private List<ColorCard> colorCards;
    private Rarity rarity;
    private Integer life;
    private Integer cost;
    private Integer power;
    private Integer counterPower;
    private List<String> attribute;
    private String effect;

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

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
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
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public List<ColorCard> getColorCards() {
        return colorCards;
    }

    public void setColorCards(List<ColorCard> colorCards) {
        this.colorCards = colorCards;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public Integer getLife() {
        return life;
    }

    public void setLife(Integer life) {
        this.life = life;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getCounterPower() {
        return counterPower;
    }

    public void setCounterPower(Integer counterPower) {
        this.counterPower = counterPower;
    }

    public List<String> getAttribute() {
        return attribute;
    }

    public void setAttribute(List<String> attribute) {
        this.attribute = attribute;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }
}