package universitylife.com.housemaster;


import android.app.Activity;
import android.app.Application;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchForm extends Fragment implements  SearchInterface{

    private Activity activity;
    private ArrayList<PlaceReview> listPlaceReview = new ArrayList<PlaceReview>();


    //for view attribute
    private EditText addressText;
    private EditText textAmenities;
    private EditText textLowestPrice;
    private EditText textHighestPrice;
    private Spinner spinner;
    private Button buttonSearchSellRent;
    private CheckBox checkRent;
    private CheckBox checkSold;

    public SearchForm() {

    }

    public SearchForm(Activity activity) {
        this.activity = activity;
        PlaceReviewDao place = new PlaceReviewCollectParse(activity);
        place.getPlaceReviewList(listPlaceReview);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View currentView = inflater.inflate(R.layout.fragment_search_form, container, false);
        //declaration of all attribute
        addressText = (EditText)  currentView.findViewById(R.id.search_location);
        spinner = (Spinner) currentView.findViewById(R.id.list_amenities);
        textAmenities = (EditText) currentView.findViewById(R.id.amenities_text);
        textLowestPrice = (EditText) currentView.findViewById(R.id.lowest_price);
        textHighestPrice = (EditText) currentView.findViewById(R.id.highest_price);
        buttonSearchSellRent = (Button) currentView.findViewById(R.id.sell_search_button);
        checkRent = (CheckBox) currentView.findViewById(R.id.find_rent);
        checkSold = (CheckBox) currentView.findViewById(R.id.find_sale);


        //set the adapter for spinner
        final String[] listingAmenities = new String[]{"Department Store","Hotel","Zoo"};
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item,listingAmenities);
        spinner.setAdapter(listAdapter);

        //get the amenitiesList from parse


        //set the textAmenities when we click on it
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textAmenities.setText(listingAmenities[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //when the button search SellRent is click then start to go to the the featured fragment
        buttonSearchSellRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("size: ",listPlaceReview.size()+"");
                //extract the value of the attribute
                String addressString =  addressText.getText().toString();
                String choosenAmenities = spinner.getSelectedItem().toString();
                String lowPriceString = textLowestPrice.getText().toString();
                String highPriceString = textHighestPrice.getText().toString();
                boolean rent = checkRent.isChecked();
                boolean sold = checkSold.isChecked();
                //start featured Fragment
                startFragmentFeatured(doSearching(addressString,choosenAmenities,lowPriceString,highPriceString,rent,sold,listPlaceReview));
            }
        });

        return currentView;
    }


    //go the featured/ListLoader but using particular list of placeReview
    public void startFragmentFeatured(ArrayList<PlaceReview> listPlaceReview){
        Fragment fragment = new ListLoader(listPlaceReview);
        FragmentManager manager =  this.getFragmentManager();
        FragmentTransaction fragmentTransaction  = manager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }



    public ArrayList<PlaceReview> doSearching(String hdbName,String amenitiyText,String lowestPrice,String highestPrice,boolean rent,boolean sell,ArrayList<PlaceReview> listPlaceReview){
        //so the searching method will be step by step
        ArrayList<PlaceReview> listFilterPlaceReview = new ArrayList<PlaceReview>();
        //the type of amenity lis no needed to be filter because google API has already done so
        Log.e("insertParameter",hdbName+" "+amenitiyText+" "+lowestPrice+" "+highestPrice+" "+rent+" "+sell);
        //ommit the place which the list of amenities is zero
        for (int i = 0; i < listPlaceReview.size(); i++) {
            PlaceReview place = listPlaceReview.get(i);
            if(place.getListAmenities().size() > 0){
                listFilterPlaceReview.add(place);
            }
        }
        listPlaceReview = (ArrayList<PlaceReview>) listFilterPlaceReview.clone();


        // #1 filter by name if user inputted
        listFilterPlaceReview.clear();
        if(hdbName != null) {
            for (int i = 0; i < listPlaceReview.size(); i++) {
                PlaceReview place = listPlaceReview.get(i);
                //firstly detect the name
                Log.e("namePlace: ",place.getHdbName());
                if (place.getHdbName().contains(hdbName)) {
                    listFilterPlaceReview.add(place);
                }

            }
            listPlaceReview = (ArrayList<PlaceReview>) listFilterPlaceReview.clone();
        }


        // #2 filter by price if user input
        listFilterPlaceReview.clear();
        if(lowestPrice != null && highestPrice != null || ( lowestPrice!= ""  && highestPrice != "" ) ){
            for(int i = 0 ; i < listPlaceReview.size() ; i++){
                PlaceReview place = listPlaceReview.get(i);
                Double currPrice = Double.parseDouble(place.getPrice());
                Double lowPrice = Double.parseDouble(lowestPrice);
                Double highPrice = Double .parseDouble(highestPrice);
                Log.e("lowestPrice",lowPrice+"");
                Log.e("highestPrice",highPrice+"");
                if(currPrice <= highPrice && currPrice >= lowPrice){
                    listFilterPlaceReview.add(place);
                }
                listPlaceReview = (ArrayList<PlaceReview>) listFilterPlaceReview.clone();
            }
        }

        // #3 filter by rent
        listFilterPlaceReview.clear();
        if(rent == true ){
            for(int i = 0 ; i < listPlaceReview.size() ; i++){
                PlaceReview place = listPlaceReview.get(i);
                Log.e("placeRent",place.isRent()+"");
                Log.e("placeName",place.getHdbName());
                if(place.isRent()){
                    listFilterPlaceReview.add(place);
                }
            }
            listPlaceReview = (ArrayList<PlaceReview>) listFilterPlaceReview.clone();
        }

        //#4 filter by sell
        listFilterPlaceReview.clear();
        if(sell == true){
            for(int i = 0 ; i < listPlaceReview.size() ; i++){
                PlaceReview place = listPlaceReview.get(i);
                if(place.isSold()){
                    listFilterPlaceReview.add(place);
                }
            }
            listPlaceReview = (ArrayList<PlaceReview>) listFilterPlaceReview.clone();
        }

        //return the list of filtered placeReview
        return listPlaceReview;

    }

    @Override
    public ArrayList<PlaceReview> doSearching(String contentSearch) {
        //is not used in here
        return null;
    }

}
