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


public class Featured extends Fragment {

    //decalration of new attribute
    //private Button buttonRefreshed;
    private PlaceReviewCollect prc;

    public Featured() {

    }


    public Featured(PlaceReviewCollect prc) {
            this.prc = prc;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //in featured you can view the current offer that the system currently collecting
        View featureView = inflater.inflate(R.layout.fragment_featured, container, false);
        final RecyclerView recyclerView = (RecyclerView) featureView.findViewById(R.id.featureView);
        //buttonRefreshed = (Button) featureView.findViewById(R.id.refresehButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.e("resulting array: ",prc.getPlaceReviewList().toString());
        CardViewAdapter adapter = new CardViewAdapter(prc.getPlaceReviewList());
        recyclerView.setAdapter(adapter);

        return featureView;
    }











}
