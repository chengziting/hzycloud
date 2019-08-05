package com.huy.cloud.web.vm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 10191042 on 2019-08-05.
 */
public class MenuModel implements Serializable {
    private String id;
    private String name;
    private String iconPath;
    private String description;
    private BigDecimal price;
    private List<String> detailImagePaths;
    private int stars;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<String> getDetailImagePaths() {
        return detailImagePaths;
    }

    public void setDetailImagePaths(List<String> detailImagePaths) {
        this.detailImagePaths = detailImagePaths;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
