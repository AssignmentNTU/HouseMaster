package universitylife.com.housemaster;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//this class will give news feed of people that sell or rent the HDB

public class SellRent extends Fragment {

    //decalration of new attribute
    //private Button buttonRefreshed;
    private PlaceReviewCollect prc;
    private ArrayList<PlaceReview> listPlaceReview = null;

    public SellRent() {

    }


    public SellRent(PlaceReviewCollect prc) {

        this.prc = prc;
    }


    public SellRent(ArrayList<PlaceReview> listPlaceReview){
        this.listPlaceReview = listPlaceReview;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //in featured you can view the current offer that the system currently collecting
        View featureView = inflater.inflate(R.layout.fragment_featured, container, false);
        final RecyclerView recyclerView = (RecyclerView) featureView.findViewById(R.id.featureView);
        //buttonRefreshed = (Button) featureView.findViewById(R.id.refresehButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(listPlaceReview == null) {
            CardViewAdapter adapter = new CardViewAdapter(prc.getPlaceReviewList(), recyclerView);
            recyclerView.setAdapter(adapter);
            return featureView;
        }else{
            CardViewAdapter adapter = new CardViewAdapter(listPlaceReview, recyclerView);
            recyclerView.setAdapter(adapter);
            return featureView;
        }
    }














}
