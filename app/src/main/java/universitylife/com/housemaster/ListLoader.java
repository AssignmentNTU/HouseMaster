package universitylife.com.housemaster;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class ListLoader extends Fragment implements CardViewAdapter.OnPlaceClick {

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
            listPlaceReview = prc.getPlaceReviewList();
        }

        CardViewAdapter adapter = new CardViewAdapter(listPlaceReview, recyclerView, this);
        recyclerView.setAdapter(adapter);
        return featureView;
    }


    @Override
    public void onClick(PlaceReview placeReview) {
        Intent intent = new Intent(getActivity(), PlaceReviewActivity.class);
        PlaceReviewActivity.placeReview = placeReview;
        startActivity(intent);
    }
}
