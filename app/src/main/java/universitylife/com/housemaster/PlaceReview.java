package universitylife.com.housemaster;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.parse.GetDataCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    private ParseFile imageFile;


    public PlaceReview(){
        //for parse.com policy
    }

    public PlaceReview(String hdbName,String hdbDescription,int imageUrl){
        this.hdbName = hdbName;
        this.description = hdbDescription;
    }


    //constructor for the card view purpose
    public PlaceReview(String hdbName,ArrayList<String> listAmenities, String location,String price,ParseFile imageFile){
        this.hdbName = hdbName;
        this.amenities = listAmenities;
        this.location = location;
        this.price = price;
        this.imageFile = imageFile;
        put("hdbName",hdbName);
        put("amenities",listAmenities);
        put("location",location);
        put("price",price);
        put("ImageFile", imageFile);
    }


    //constructor for the card view purpose
    public PlaceReview(String hdbName, String description,String price,ParseFile imageFile,ArrayList<String> listAmenities,boolean rent,boolean sale,String phoneNumber){
        this.hdbName = hdbName;
        this.amenities = listAmenities;
        this.description = description;
        this.price = price;
        this.imageFile = imageFile;
        this.rent = rent;
        this.sale = sale;
        this.phoneNumber = phoneNumber;
        put("hdbName",hdbName);
        put("amenities",listAmenities);
        put("description",description);
        put("price",price);
        put("ImageFile",imageFile);
        put("rent",rent);
        put("sale",sale);
        put("phoneNumber",phoneNumber);
    }




    //just for the getter
    public String getHdbName(){
        return (String)get("hdbName");
    }


    public String getHdbDescription(){
        return (String)get("description");
    }


    public ParseFile getParseFile(){
        Log.e("ImageFileSave:",getParseFile("ImageFile").toString());
        return getParseFile("ImageFile");
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
        return (boolean)get("rent");
    }

    public boolean isSold(){
        return (boolean)get("sale");
    }

    public String getPhoneNumber(){
        return (String)get("phoneNumber");
    }

    public String getEmail(){
        return (String)get("email");
    }




    //for setter method
}
