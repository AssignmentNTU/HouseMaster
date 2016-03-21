package universitylife.com.housemaster;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by LENOVO on 21/03/2016.
 */

 public class PlaceReviewCollect extends AsyncTask<String, String, JSONObject> {

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
            for(int i = 0 ; i < record.length() ; i++){
                JSONObject buffObj = record.getJSONObject(i);
                String townString = buffObj.getString(TAG_TOWN);
                String roomTypeString = buffObj.getString(TAG_ROOM_TYPE);
                String price          = buffObj.getInt(TAG_MIN_PRICE)+ " - " + buffObj.getInt(TAG_MAX_PRICE);

                //place review constructor HDBName,list Amenities,Location,Price
                //placeReview attribute
                String hdbName = townString+" "+roomTypeString;
                //logic to get the list of amenities need to be settled here
                ArrayList<String> listAmenities = new ArrayList<String>();
                //location
                String locationHdb = townString;
                //price
                String priceHDB = price;
                //declare the place review and put it into placeReviewList

                PlaceReview placeReview = new PlaceReview(hdbName,listAmenities,locationHdb,priceHDB,imageList[i%imageList.length]);
                Log.e("ImageResource: ",imageList[currentImage]+"");
                placeReviewsList.add(placeReview);
            }

            Log.e("records: ",record.toString());

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Error1: ","true");
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



}