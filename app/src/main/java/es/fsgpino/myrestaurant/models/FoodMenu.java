package es.fsgpino.myrestaurant.models;

import java.io.Serializable;
import java.util.LinkedList;

public class FoodMenu implements Serializable {

    private LinkedList<Plate> mPlates;

    public FoodMenu() {
        mPlates = new LinkedList<Plate>();
    }

    public LinkedList<Plate> getPlates() {
        return mPlates;
    }

    public void addPlate(Plate newPlate) {
        mPlates.add(newPlate);
    }

}
