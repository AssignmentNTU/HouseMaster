package universitylife.com.housemaster;

import android.util.Log;


import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.*;
import java.util.ArrayList;

@ParseClassName("PlaceReview")
public class PlaceReview extends ParseObject {
    public PlaceReview(){
        //for parse.com policy
    }


    //constructor for government
    public PlaceReview(String hdbName, LatLng position, ArrayList<String> listAmenities, String location,String price,
                       ParseFile imageFile,String userName){
        put("hdbName",hdbName);
        put("amenities",listAmenities);
        put("location",location);
        put("price",price);
        put("ImageFile", imageFile);
        put("userName",userName);
        put("lat", position.latitude);
        put("lng", position.longitude);
    }


    //constructor for user input
    public PlaceReview(String hdbName, LatLng position, String description, String price, ParseFile imageFile, ArrayList<String> listAmenities,
                       boolean rent,boolean sale,String phoneNumber,String userName){
        put("hdbName",hdbName);
        put("location", hdbName);
        put("amenities",listAmenities);
        put("description",description);
        put("price",price);
        put("ImageFile",imageFile);
        put("rent",rent);
        put("sale",sale);
        put("phoneNumber",phoneNumber);
        put("userName",userName);
        put("lat", position.latitude);
        put("lng", position.longitude);
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

    public String getUsername(){ return (String)get("userName"); }

    public LatLng getPosition() {
        return new LatLng(
                getDouble("lat"),
                getDouble("lng")
        );
    }
}
