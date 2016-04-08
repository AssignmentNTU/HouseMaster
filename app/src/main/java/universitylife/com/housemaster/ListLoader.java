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


public class ListLoader extends Fragment {

    //decalration of new attribute
    //private Button buttonRefreshed;
    private PlaceReviewCollect prc;
    private ArrayList<PlaceReview> listPlaceReview = null;

    public ListLoader() {

    }


    public ListLoader(PlaceReviewCollect prc) {
            this.prc = prc;
    }


    public ListLoader(ArrayList<PlaceReview> listPlaceReview){
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
            ArrayList<PlaceReview> listPlace = prc.getPlaceReviewList();
            /*if(listPlace.size() <= 0){
                //so user know that there is nothing to display
                listPlace.add(new PlaceReview("NONE TO DISPLAY","","",null,null,false,false,"",""));
            }*/
            CardViewAdapter adapter = new CardViewAdapter(listPlace, recyclerView);
            recyclerView.setAdapter(adapter);
            return featureView;
        }else{
            /*
            if(listPlaceReview.size() <= 0){
                //so user know that there has nothing to display
                listPlaceReview.add(new PlaceReview("NONE TO DISPLAY","","",null,null,false,false,"",""));
            }*/
            CardViewAdapter adapter = new CardViewAdapter(listPlaceReview, recyclerView);
            recyclerView.setAdapter(adapter);
            return featureView;
        }
    }














}
