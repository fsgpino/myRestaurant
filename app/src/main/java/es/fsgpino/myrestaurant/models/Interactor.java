package es.fsgpino.myrestaurant.models;

import java.util.LinkedList;

public class Interactor {

    public static LinkedList<Table> getTables() {
        return tables;
    }

    private static LinkedList<Table> tables = new LinkedList<>();

    public static void addTable(Table t){
        tables.add(t);
    }

    public static void removeAllTables(){
        tables = new LinkedList<>();
    }

    public static void addPlate(Plate p, int position) {
        tables.get(position).addPlate(p);
    }

}
