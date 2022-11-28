package com.example.mealer.Accounts;

import java.util.List;

public class Meal {
//    keep track of IDs
    private String mealID;
    private String cookID;
    private String mealName;



    // main dish, soup, desert, etc.
    private String mealType;
//    Cuisine type
    private String cuisineType;
    private String ingredients;
    private String allergens;
//  if I use the double, the firebase will automatically create a
//  0.0 value, which not looks very well.
    private String price;
    private String description;

    public Meal() {
    }

    // test constructor for manually create
    public Meal(String name, String mealType, String cuisineType, String id){
        mealName = name;
        this.mealType = mealType;
        this.cuisineType = cuisineType;
        cookID = id;
    }
    public Meal(String cookID, String mealName, String mealType, String cuisineType, String ingredients, String allergens, String price, String description) {
        this.cookID = cookID;
        this.mealName = mealName;
        this.mealType = mealType;
        this.cuisineType = cuisineType;
        this.ingredients = ingredients;
        this.allergens = allergens;
        this.price = price;
        this.description = description;
    }

    public String getMealID() {
        return mealID;
    }

    public void setMealID(String mealID) {
        this.mealID = mealID;
    }

    public String getCookID() {
        return cookID;
    }

    public void setCookID(String cookID) {
        this.cookID = cookID;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getAllergens() {
        return allergens;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
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

    @Override
    public String toString() {
        return "Meal{" +
                "mealName='" + mealName + '\'' +
                ", mealType='" + mealType + '\'' +
                ", cuisineType='" + cuisineType + '\'' +
                ", ingredients=" + ingredients +
                ", allergens='" + allergens + '\'' +
                ", price=" + price +
                ", Description='" + description + '\'' +
                '}';
    }
}
