package universitylife.com.housemaster;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.PlaceTypes;
import com.google.android.gms.maps.GoogleMap;
import com.parse.Parse;
import com.parse.ParseObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by LENOVO on 21/03/2016.
 */

 public class PlaceReviewCollect extends AsyncTask<String, String, JSONObject> implements LocationListener{
    //for GoogleMap API purpose
    private GoogleMap mGoogleMap;
    private double mLatitude;
    private double mLongitude;

    //for JSON purpose
    private static String url = "https://data.gov.sg/api/action/datastore_search?resource_id=d23b9636-5812-4b33-951e-b209de710dd5";
    JSONArray record = null;
    JSONObject allObject =null;
    JSONArray lastResult = null;
    JSONObject resultObject = null;

    //JSON attribute name
    private static final String TAG_RECORD = "records";
    private static final String TAG_FIN_YEAR = "financial_year";
    private static final String TAG_TOWN = "town";
    private static final String TAG_ROOM_TYPE = "room_type";
    private static final String  TAG_MIN_PRICE = "min_selling_price";
    private static final String  TAG_MAX_PRICE = "max_selling_price";
    private static final String TAG_RESULT = "result";

    //other declaration
    private MyProgressDialog mDialog;
    private ArrayList<PlaceReview> placeReviewsList = null;
    //we will randoming the image since the image is not available from the server
    private Integer[] imageList = new Integer[]{R.drawable.hdb1,R.drawable.hdb2,R.drawable.hdb3,R.drawable.hdb4,R.drawable.hdb6,R.drawable.hdb7,R.drawable.hdb8,
            R.drawable.hdb10,R.drawable.hdb11};
    private boolean finishLoading = false;
    private Context context;
    private Activity activity;


    public PlaceReviewCollect(Context c) {
        mDialog = new MyProgressDialog(c);
        placeReviewsList = new ArrayList<PlaceReview>();
        finishLoading = false;
        this.context = c;
        this.activity = (Activity) c;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog.showProgressDialogRetrieveData();
        finishLoading = false;
    }

    @Override
    protected JSONObject doInBackground(String... args) {
        //this method will be executed in the background
        JSONParser jParser = new JSONParser();
        // Getting JSON from URL
        JSONObject json = jParser.getJSONFromUrl(url);
        //Log.e("JSON FILE: ",json.toString());
        allObject = json;
        return json;
    }

    @Override
    protected void onPostExecute(JSONObject json) {
        mDialog.dismissDialogRetrieveData();
        finishLoading = true;
        Log.e("allObject: ", allObject.toString());
        try {
            // Getting JSON Array
            resultObject = allObject.getJSONObject("result");
            record = resultObject.getJSONArray("records");
            //populatte all the data and save it into the placeReviewList
            int currentImage = 0;
            placeReviewsList.clear();
            //should be record.length but now i am using 10  as representatiom
            for(int i = 0 ; i < 10 ; i++){
                JSONObject buffObj = record.getJSONObject(i);
                String townString = buffObj.getString(TAG_TOWN);
                String roomTypeString = buffObj.getString(TAG_ROOM_TYPE);
                String price          = buffObj.getInt(TAG_MIN_PRICE)+ " - " + buffObj.getInt(TAG_MAX_PRICE);

                //place review constructor HDBName,list Amenities,Location,Price
                //placeReview attribute
                String hdbName = townString+" "+roomTypeString;
                //location
                String locationHdb = townString;
                //price
                String priceHDB = price;
                //change the amenities List
                getAmenitiesList(hdbName ,townString,priceHDB,imageList[i%imageList.length]);
            }

            Log.e("records: ",record.toString());

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Error1: ","true");
        }

    }


    public void getAmenitiesList(String hdbName,String location,String priceHDB,int imageIndex){
        // Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);

        if(status == ConnectionResult.SUCCESS){
            //when the phone is supported by google maps

            //we need to get the location longitude and latitude from GeoCoder
            Geocoder geocoder = new Geocoder(context);
            Address address = null;
            try {
                //change jurong east as town
                List<Address> list = geocoder.getFromLocationName(location,1);
                address = list.get(0);
                mLatitude = address.getLatitude();
                mLongitude = address.getLongitude();

            } catch (IOException e) {
                Log.e("Test 2","error here");
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
            PlacesTask placesTask = new PlacesTask(hdbName,location,priceHDB,imageIndex);
            // Invokes the "doInBackground()" method of the class PlaceTask
            placesTask.execute(sb.toString());


        }else{
            //when the phone does not support google Service
            Toast.makeText(context,"Sorry your phone is not supported by google MAP",Toast.LENGTH_LONG).show();

        }

    }



    //in here i will populate all the data that is retrieved from the API and the database
    public ArrayList<PlaceReview> getPlaceReviewList(){
        //just from API
        return  placeReviewsList;
    }

    public boolean getFinishLoading(){
        return finishLoading;
    }



    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

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
    private class PlacesTask extends AsyncTask<String, Integer, String>{

        String data = null;


        //attribute for the placeReview
        String hdbName = null;
        String priceRange = null;
        String location = null;
        int indexImage = 0;


        public PlacesTask(String hdbName,String location,String priceRange, int indexImage ){
            this.hdbName = hdbName;
            this.priceRange = priceRange;
            this.indexImage = indexImage;
            this.location = location;
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
            ParserTask parserTask = new ParserTask(hdbName,location,priceRange,indexImage);

            // Start parsing the Google places in JSON format
            // Invokes the "doInBackground()" method of the class ParseTask
            parserTask.execute(result);
        }

    }

    private class ParserTask extends AsyncTask<String ,Integer ,List<HashMap<String,String>>>{

        JSONObject jObject;


        //attribute for placeReview
        String hdbName = null;
        String priceRange = null;
        String location = null;
        int indexImage = 0;

        public ParserTask(String hdbName ,String location,String priceRange , int indexImage){
            this.hdbName = hdbName;
            this.priceRange = priceRange;
            this.indexImage = indexImage;
            this.location = location;
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
            ParseObject.registerSubclass(PlaceReview.class);
            PlaceReview placeReview = new PlaceReview(hdbName,listAmenities,location,priceRange,indexImage);
            placeReviewsList.add(placeReview);
        }
    }




}