package universitylife.com.housemaster;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchForm extends Fragment {

    private Activity activity;
    private PlaceReviewCollect prc;
    private ArrayList<PlaceReview> listPlaceReview;


    //for view attribute
    private EditText addressText;
    private Button buttonGPS;
    


    public SearchForm() {

    }

    public SearchForm(Activity activity,PlaceReviewCollect prc) {
        this.activity = activity;
        this.prc = prc;
        listPlaceReview = prc.getPlaceReviewList();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View currentView = inflater.inflate(R.layout.fragment_search_form, container, false);
        addressText = (EditText)  currentView.findViewById(R.id.search_location);
        buttonGPS = (Button) currentView.findViewById(R.id.search_GPS_button);



        return currentView;
    }


    public void doSearching(){

    }

}
