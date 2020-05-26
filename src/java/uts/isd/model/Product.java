/*
 * UTS Introduction to Software Development
 * IOT Bay - Assignment 1
 * @author Rhys Hanrahan 11000801
 */
package uts.isd.model;

import java.util.Date;
import javax.servlet.ServletRequest;

/**
 *
 * @author rhys
 */
public class Product {
    
    private String id; 
    private String categoryId;
    private String currencyId;
    private String name;
    private String price;
    private String description;
    private String image;
    private String initialQuantity;
    private String currentQuantity;
    private String lastReorderDate;
    private String createdDate;
    private String createdBy;
    private String modifiedDate;
    private String modifiedBy;
    
    //insert a constructor to initialize the fields

    public Product(String name, String description, String initialQuantity, String price, String id) {
        this.name = name;
        this.description = description;
        this.initialQuantity = initialQuantity;
        this.price = price;
        this.id = id;
    }

   //insert getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInitialQuantity() {
        return initialQuantity;
    }

    public void setInitialQuantity(String initialQuantity) {
        this.initialQuantity = initialQuantity;
    }

    public String getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(String currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public String getLastReorderDate() {
        return lastReorderDate;
    }

    public void setLastReorderDate(String lastReorderDate) {
        this.lastReorderDate = lastReorderDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

   
      
}