package org.hakifiles.api.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "Products")
public class Product implements Comparable<Product> {
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

    private String img;

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String createUrl() {
        return "Thumbnail/" + code + "_" + name.replace(" ", "_") + ".png";
    }

    @Override
    public int compareTo(Product o) {
        Date mainProduct = null;
        try {
            mainProduct = getTime(releaseDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Date otherProduct = null;
        try {
            otherProduct = getTime(o.getReleaseDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return otherProduct.compareTo(mainProduct);
    }

    private Date getTime(String strDate) throws ParseException {
        if (strDate.equals("none")) {
            strDate = "October 2022";
        }
        String[] parts = strDate.split(" ");
        SimpleDateFormat formatter = new SimpleDateFormat("MMM-yyyy");
        if (parts.length == 2) {
            return formatter.parse(parts[0] + "-" + parts[1]);
        }
        return formatter.parse(parts[1] + "-" + parts[2]);
    }
}
