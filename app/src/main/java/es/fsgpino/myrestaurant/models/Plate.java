package es.fsgpino.myrestaurant.models;

import java.io.Serializable;

public class Plate implements Serializable {

    private String mName;
    private String mImage;
    private String[] mIngredients;
    private float mPrice;

    public Plate(String name, String image, String[] ingredients, float price) {
        mName = name;
        mImage = image;
        mIngredients = ingredients;
        mPrice = price;
    }

    public String getName() {
        return mName;
    }

    public String getImage() {
        return mImage;
    }

    public String[] getIngredients() {
        return mIngredients;
    }

    public float getPrice() {
        return mPrice;
    }

}
