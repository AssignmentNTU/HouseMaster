package universitylife.com.housemaster;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;

@ParseClassName("PlaceReview")
public class PlaceReview extends ParseObject{
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


    public PlaceReview(){
        //for parse.com policy
    }

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
        put("hdbName",hdbName);
        put("amenities",listAmenities);
        put("location",location);
        put("price",price);
        put("imageUrl",imageUrl);
    }


    //just for the getter
    public String getHdbName(){
        return (String)get("hdbName");
    }


    public String getHdbDescription(){
        return (String)get("description");
    }


    public int getImageUrl(){
        return (Integer)get("imageUrl");
    }

    public ArrayList<String> getListAmenities(){
        return (ArrayList<String>) get("amenities");
    }

    public String getLocation(){
        return (String)get("location");
    }

    public String getPrice(){
        return  (String)get("price");
    }

    public boolean isRent(){
        return (boolean)get("isRent");
    }

    public boolean isSold(){
        return (boolean)get("isSold");
    }

    public String getPhoneNumber(){
        return (String)get("phoneNumber");
    }

    public String getEmail(){
        return (String)get("email");
    }

}
