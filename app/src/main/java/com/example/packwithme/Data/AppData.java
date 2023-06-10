package com.example.packwithme.Data;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.example.packwithme.Constants.MyConstants;
import com.example.packwithme.Database.RoomDB;
import com.example.packwithme.Models.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppData extends Application {

    RoomDB database;
    String category;
    Context context;

    public static final String LAST_VERSION = "LAST_VERSION";
    public static final int NEW_VERSION = 1;


    public AppData(RoomDB database) {
        this.database = database;
    }


    public AppData(RoomDB database, Context context) {
        this.database = database;
        this.context = context;
    }

    public List<Items> getBasicData() {
        category = "Basic Needs";
        List<Items> basicItems = new ArrayList<Items>();
        basicItems.clear();
        basicItems.add(new Items("Visa", category,false));
        basicItems.add(new Items("Passport", category,false));
        basicItems.add(new Items("Tickets", category,false));
        basicItems.add(new Items("Wallet", category,false));
        basicItems.add(new Items("Driving License", category,false));
        basicItems.add(new Items("Currency", category,false));
        basicItems.add(new Items("House Key", category,false));
        basicItems.add(new Items("Book", category,false));
        basicItems.add(new Items("Travel Pillow", category,false));
        basicItems.add(new Items("Eye Patch", category,false));
        basicItems.add(new Items("Umbrella", category,false));
        basicItems.add(new Items("Note Book", category,false));
        return basicItems;

    }
    public List<Items> getPersonalCareData(){
        String []data = {"Tooth-brush", "Tooth-paste", "Floss", "Mouthwash","Shaving Cream", "Soap"};
        return prepareItemsList(MyConstants.PERSONAL_CARE_CAMEL_CASE, data);
    }
    public List<Items> getClothingData() {
        String[] data = {"Stocking", "T-Shirts", "Casual Dress", "Shorts", "Jeans", "Suit"};
        return prepareItemsList(MyConstants.CLOTHING_CAMEL_CASE, data);
    }
    public List<Items> getBabyNeedsCareData() {
        String[] data = {"Outfit", "Baby Socks", "Baby Hat", "Baby Pyjamas", "Blanket", "Baby Cotton"};
        return prepareItemsList(MyConstants.BABY_NEEDS_CAMEL_CASE, data);
    }
    public List<Items> getHealthData() {
        String[] data = {"Vitamins Used", "Drugs Used", "Hot Water Bag", "Pain Reliever", "Fever Reducer", "First Aid Kit"};
        return prepareItemsList(MyConstants.HEALTH_CAMEL_CASE, data);
    }
    public List<Items> getTechnologyData() {
        String[] data = {"Mobile Phone", "Charger", "Flash-Light", "Laptop", "Camera", "Phone Cover"};
        return prepareItemsList(MyConstants.TECHNOLOGY_CAMEL_CASE, data);
    }
    public List<Items> getFoodData() {
        String[] data = {"Snacks", "Sandwich", "Juice", "Tea Bags", "Coffee", "Water"};
        return prepareItemsList(MyConstants.FOOD_CAMEL_CASE, data);
    }
    public List<Items> getBeachSuppliesData() {
        String[] data = {"Sea Glasses", "Sea Bed", "Suntan Cream", "Beach Bag"};
        return prepareItemsList(MyConstants.BEACH_SUPPLIES_CAMEL_CASE, data);
    }
    public List<Items> getCarSuppliesData() {
        String[] data = {"Pump", "Car Jack", "Car Charger", "Car Cover"};
        return prepareItemsList(MyConstants.CAR_SUPPLIES_CAMEL_CASE, data);
    }
    public List<Items> getNeedsData() {
        String[] data = {"Backpack", "Daily Bags", "Laundary Bag", "Travel lock", "Sports Equipment"};
        return prepareItemsList(MyConstants.NEEDS_CAMEL_CASE, data);
    }

    public List<Items> prepareItemsList(String category, String []data){
        List<String> list = Arrays.asList(data);
        List<Items> dataList = new ArrayList<>();
        dataList.clear();

        for (int i = 0; i < list.size(); i++) {
            dataList.add(new Items(list.get(i), category, false));
        }
        return dataList;
    }

    public List<List<Items>> getAllData(){
        List<List<Items>> listOfAllItems = new ArrayList<>();
        listOfAllItems.clear();
        listOfAllItems.add(getBasicData());
        listOfAllItems.add(getPersonalCareData());
        listOfAllItems.add(getClothingData());
        listOfAllItems.add(getFoodData());
        listOfAllItems.add(getNeedsData());
        listOfAllItems.add(getBabyNeedsCareData());
        listOfAllItems.add(getBeachSuppliesData());
        listOfAllItems.add(getCarSuppliesData());
        listOfAllItems.add(getHealthData());
        listOfAllItems.add(getTechnologyData());
        return listOfAllItems;
    }

    public void persistAllData(){
        List<List<Items>> listOfAllItems = getAllData();
        for (List<Items> list: listOfAllItems){
            for (Items items:list){
                database.mainDao().saveItem(items);
            }
        }

        System.out.println("Data added.");
    }

    public void persistDataByCategory(String category, Boolean onlyDelete) {
        try {
            List<Items> list = deleteAndGetListByCategory(category, onlyDelete);
            if (!onlyDelete) {
                for (Items item : list) {
                    database.mainDao().saveItem(item);
                }
                Toast.makeText(this, category + " Reset Successfully.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, category + " Reset Successfully.", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }
    }


    private List<Items> deleteAndGetListByCategory(String category, Boolean onlyDelete) {
        if (onlyDelete) {
            database.mainDao().deleteAllByCategoryAndAddedBy(category, MyConstants.SYSTEM_SMALL);
        }else{
            database.mainDao().deleteAllByCategory(category);
        }

        switch (category){
            case MyConstants.BABY_NEEDS_CAMEL_CASE:
                return getBasicData();

            case MyConstants.HEALTH_CAMEL_CASE:
                return getHealthData();

            case MyConstants.TECHNOLOGY_CAMEL_CASE:
                return getTechnologyData();

            case MyConstants.FOOD_CAMEL_CASE:
                return getFoodData();

            case MyConstants.BEACH_SUPPLIES_CAMEL_CASE:
                return getBeachSuppliesData();

            case MyConstants.CAR_SUPPLIES_CAMEL_CASE:
                return getCarSuppliesData();

            case MyConstants.CLOTHING_CAMEL_CASE:
                return getClothingData();

            case MyConstants.NEEDS_CAMEL_CASE:
                return getNeedsData();

            default:
                return new ArrayList<>();
        }
    }





}
