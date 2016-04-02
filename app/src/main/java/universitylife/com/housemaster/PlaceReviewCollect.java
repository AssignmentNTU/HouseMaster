package universitylife.com.housemaster;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.PlaceTypes;
import com.google.android.gms.maps.GoogleMap;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import org.json.JSONArray;
import org.json.JSONException;
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
                AmenitiesGenerator amenitiesGenerator = new AmenitiesGenerator(context,placeReviewsList,"gym,department_store");
                amenitiesGenerator.getAmenitiesListShort(hdbName ,townString,priceHDB,imageList[i%imageList.length]);
            }

            Log.e("records: ",record.toString());

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Error1: ","true");
        }

        //finish all processing
        mDialog.dismissDialogRetrieveData();
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

    public ArrayList<PlaceReview> getPlaceReviewList(){
        return placeReviewsList;
    }
}