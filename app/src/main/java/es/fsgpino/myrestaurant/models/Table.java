package es.fsgpino.myrestaurant.models;

import java.io.Serializable;
import java.util.LinkedList;

public class Table implements Serializable {

    private int mIdentifier;
    private LinkedList<Plate> mPlates;

    public Table(int identifier, LinkedList<Plate> plates) {
        mIdentifier = identifier;
        mPlates = plates;
    }

    public int getIdentifier() {
        return mIdentifier;
    }

    public LinkedList<Plate> getPlates() {
        return mPlates;
    }

    public void addPlate(Plate newPlate) {
        mPlates.add(newPlate);
    }

    public void clear() {
        mPlates.clear();
    }

    public float getBill(){
        float bill = 0;
        for (Plate plate:mPlates) {
            bill += plate.getPrice();
        }
        return bill;
    }

}
