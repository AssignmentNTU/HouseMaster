package universitylife.com.housemaster;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LENOVO on 26/03/2016.
 */
public class AmenitiesGenerator {

    //dependency purpose
    private Context context;

    //for GoogleMap API purpose
    private GoogleMap mGoogleMap;
    private double mLatitude;
    private double mLongitude;


    //Listing PlaceReviewList purpoee
    private ArrayList<PlaceReview> listPlaceReview = null;
    private String typeAmenities = null;
    private String userName = null;
    private SharedPreferences shared;

    public AmenitiesGenerator(Context context,ArrayList<PlaceReview> listPlaceReview,String typeAmenities){
        this.context = context;
        this.listPlaceReview  = listPlaceReview;
        this.typeAmenities = typeAmenities;
        shared = context.getSharedPreferences("UserPrefs",context.MODE_PRIVATE);
        userName = shared.getString("UserName",null);
    }


    public void getAmenitiesListShort(String hdbName,String location,String priceHDB,int imageIndex){
        // Getting Google Play availability status
        boolean status = Geocoder.isPresent();

        if(status){
            //when the phone is supported by google maps

            //we need to get the location longitude and latitude from GeoCoder
            Geocoder geocoder = new Geocoder(context);
            Address address = null;
            try {
                //change jurong east as town
                Log.i("location", location);
                List<Address> list = geocoder.getFromLocationName(location,1);
                address = list.get(0);
                mLatitude = address.getLatitude();
                mLongitude = address.getLongitude();

            } catch (IOException e) {
                Log.e("Test 2", "error here");
                e.printStackTrace();
                return;
            }


            //then we need to specified what sort of amenities we want to retrieve
            String typeAmenity = typeAmenities;
            String keyAPI = "AIzaSyBFNY0--sAqq2jVFAcs11jtTRJ2OYRVHYE";


            StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
            sb.append("location="+mLatitude+","+mLongitude);
            sb.append("&radius=5000");
            sb.append("&types="+typeAmenity);
            sb.append("&sensor=true");
            sb.append("&key="+keyAPI);
            // Creating a new non-ui thread task to download json data
            //in here i will pass my PlaceReviewList so it just will be added
            PlacesTask placesTask = new PlacesTask(hdbName, new LatLng(mLatitude, mLongitude), location,priceHDB,imageIndex);
            // Invokes the "doInBackground()" method of the class PlaceTask
            placesTask.execute(sb.toString());


        }else{
            //when the phone does not support google Service
            Toast.makeText(context, "Sorry your phone is not supported by google MAP", Toast.LENGTH_LONG).show();

        }

    }



    public void getAmenitiesListLong(String hdbName, String description,String price,boolean rent,boolean sale,String phoneNumber,ParseFile imageFile){
        // Getting Google Play availability status
        boolean status = Geocoder.isPresent();

        if(status){
            //when the phone is supported by google maps

            //we need to get the location longitude and latitude from GeoCoder
            Geocoder geocoder = new Geocoder(context);
            Address address = null;
            try {
                //change jurong east as town
                List<Address> list = geocoder.getFromLocationName(hdbName,1);
                if(list.size()  < 1){
                    mLatitude = 110;
                    mLongitude = 110;
                }else{
                    address = list.get(0);
                    mLatitude = address.getLatitude();
                    mLongitude = address.getLongitude();
                }


            } catch (IOException e) {
                Log.e("Test 2", "error here");
                e.printStackTrace();
                return;
            }


            //then we need to specified what sort of amenities we want to retrieve
            String typeAmenity = "Hotel";
            String keyAPI = "AIzaSyBFNY0--sAqq2jVFAcs11jtTRJ2OYRVHYE";


            StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
            sb.append("location="+mLatitude+","+mLongitude);
            sb.append("&radius=5000");
            sb.append("&types="+typeAmenity);
            sb.append("&sensor=true");
            sb.append("&key="+keyAPI);
            // Creating a new non-ui thread task to download json data
            //in here i will pass my PlaceReviewList so it just will be added
            PlacesTask placesTask = new PlacesTask(hdbName, new LatLng(mLatitude, mLongitude), price,description,phoneNumber,sale,rent,imageFile);
            // Invokes the "doInBackground()" method of the class PlaceTask
            placesTask.execute(sb.toString());


        }else{
            //when the phone does not support google Service
            Toast.makeText(context, "Sorry your phone is not supported by google MAP", Toast.LENGTH_LONG).show();

        }

    }






    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb  = new StringBuffer();
            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Hi error 1", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }

        return data;
    }




    /** A class, to download Google Places */
    private class PlacesTask extends AsyncTask<String, Integer, String> {

        String data = null;
        LatLng position;

        //attribute for the placeReview
        String hdbName = null;
        String priceRange = null;
        String location = null;
        int indexImage = 0;
        String description =null;
        String price =null;
        String phoneNumber = null;
        boolean rent= false;
        boolean sale= false;
        ParseFile imageFile = null;


        //this constructor is used just by PlaceReviewCollect
        public PlacesTask(String hdbName, LatLng position, String location,String priceRange, int indexImage ){
            this.hdbName = hdbName;
            this.priceRange = priceRange;
            this.indexImage = indexImage;
            this.location = location;
            this.position = position;
        }


        public PlacesTask(String hdbName, LatLng position, String price,String description,String phoneNumber,boolean rent,boolean sale,ParseFile imageFile){
            this.hdbName = hdbName;
            this.price = price;
            this.description = description;
            this.rent= rent;
            this.sale = sale;
            this.imageFile = imageFile;
            this.phoneNumber = phoneNumber;
            this.position = position;
        }


        // Invoked by execute() method of this object and pass the URL
        @Override
        protected String doInBackground(String... url) {
            try{
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result){

            //the different matter is additional attribute that is declared if imageFile is null then the caller must be PlaceRevuewCollect
            //otherwise it is PlaceReviewCollectParse
            if(imageFile == null) {
                ParserTask parserTask = new ParserTask(hdbName, position, location, priceRange, indexImage);
                // Start parsing the Google places in JSON format
                // Invokes the "doInBackground()" method of the class ParseTask
                parserTask.execute(result);
            }else{
                ParserTask parserTask = new ParserTask(hdbName, position, price, description,phoneNumber,rent,sale,imageFile);
                // Start parsing the Google places in JSON format
                // Invokes the "doInBackground()" method of the class ParseTask
                parserTask.execute(result);
            }
        }

    }

    private class ParserTask extends AsyncTask<String ,Integer ,List<HashMap<String,String>>>{

        JSONObject jObject;

        //attribute for the placeReview
        String hdbName = null;
        String priceRange = null;
        String location = null;
        int indexImage = 0;
        String description =null;
        String price =null;
        String phoneNumber = null;
        boolean rent= false;
        boolean sale= false;
        ParseFile imageFile;
        LatLng position;

        public ParserTask(String hdbName , LatLng position, String location,String priceRange , int indexImage){
            this.hdbName = hdbName;
            this.position = position;
            this.priceRange = priceRange;
            this.indexImage = indexImage;
            this.location = location;
        }

        public ParserTask(String hdbName, LatLng position, String price,String description,String phoneNumber,boolean rent,boolean sale,ParseFile imageFile){
            this.hdbName = hdbName;
            this.position = position;
            this.price = price;
            this.description = description;
            this.rent= rent;
            this.sale = sale;
            this.imageFile = imageFile;
            this.phoneNumber = phoneNumber;
        }



        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonObject) {
            List<HashMap<String, String>> places = null;
            PlaceJSONParser placeJsonParser = new PlaceJSONParser();
            try{
                jObject = new JSONObject(jsonObject[0]);
                places = placeJsonParser.parse(jObject);
            }catch(Exception e){
                Log.e("Failed in parse Task","true");
            }

            return places;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
            //in here i will retrieve all amenities nearby
            ArrayList<String> listAmenities = new ArrayList<String>();

            for(int i = 0 ; i < hashMaps.size()  ; i++){
                HashMap<String,String> hm = hashMaps.get(i);
                String hmPlace = hm.get("place_name");
                listAmenities.add(hmPlace);
                if(i >= 5){
                    //just retrieve 10 data at most
                    break;
                }
            }
            //when all data is retrieved we just need to create PlaceReview Object and pass it to the placeReviewList
            //these below method just will be executed when the caller is PlaceReviewCollect
            if(imageFile == null) {
                ParseObject.registerSubclass(PlaceReview.class);
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), indexImage);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bitmapdata = stream.toByteArray();
                ParseFile photo = new ParseFile("image.jpg", bitmapdata);
                try {
                    photo.save();
                } catch (ParseException e) {
                    Log.e("cannot be svae", "haha");
                    e.printStackTrace();
                }
                PlaceReview placeReview = new PlaceReview(hdbName, position, listAmenities, location, priceRange, photo,userName);
                listPlaceReview.add(placeReview);
            }else{
                PlaceReview placeReview = new PlaceReview(hdbName, hdbName, position, description, price,imageFile,listAmenities,rent,sale,phoneNumber,userName);
                listPlaceReview.add(placeReview);
            }
        }
    }


}
