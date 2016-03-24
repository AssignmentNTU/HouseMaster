package universitylife.com.housemaster;

import java.util.ArrayList;

/**
 * Created by LENOVO on 19/03/2016.
 */
public class PlaceReview {
    private String hdbName;
    private String description;
    private ArrayList<String> amenities;
    private String location;
    private String price;
    private boolean rent;
    private boolean sale;
    private String phoneNumber;
    private String email;
    private int imageUrl;


    public PlaceReview(String hdbName,String hdbDescription,int imageUrl){
        this.hdbName = hdbName;
        this.description = hdbDescription;
        this.imageUrl = imageUrl;
    }


    //constructor for the card view purpose
    public PlaceReview(String hdbName,ArrayList<String> listAmenities, String location,String price,int imageUrl){
        this.hdbName = hdbName;
        this.amenities = listAmenities;
        this.location = location;
        this.price = price;
        this.imageUrl = imageUrl;
    }


    //just for the getter
    public String getHdbName(){
        return hdbName;
    }


    public String getHdbDescription(){
        return description;
    }


    public int getImageUrl(){
        return imageUrl;
    }

    public ArrayList<String> getListAmenities(){
        return amenities;
    }

    public String getLocation(){
        return location;
    }

    public String getPrice(){
        return price;
    }

    public boolean isRent(){
        return rent;
    }

    public boolean isSold(){
        return sale;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public String getEmail(){
        return email;
    }

}
