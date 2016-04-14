package universitylife.com.housemaster;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 */
public class SellRentList extends Fragment {

    private PlaceReviewDao prc;
    private ArrayList<PlaceReview> listPlaceReview = new ArrayList<PlaceReview>();


    //attribute within the views
    Button buttonSearch;
    EditText textSearchNews;



    public SellRentList() {

    }

    public SellRentList(PlaceReviewDao prc) {
        this.prc = prc;
        prc.getPlaceReviewList(listPlaceReview);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //get the view of fragment
        final MyProgressDialog dialog = new MyProgressDialog(getActivity());
        dialog.showProgressDialogRetrieveData();
        View currentView = inflater.inflate(R.layout.fragment_search_form_news, container, false);
        //declare all the attribute
        textSearchNews = (EditText) currentView.findViewById(R.id.newsSearchText);
        buttonSearch = (Button) currentView.findViewById(R.id.buttonSearchNews);
        final ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        //need to crehate thread so it will wait for the previous session to be finished
        exec.schedule(new Runnable(){
            @Override
            public void run(){
                startFragmentFeatured(listPlaceReview);
                dialog.dismissDialogRetrieveData();
            }
        }, 5, TimeUnit.SECONDS);
        return currentView;
    }


    public void startFragmentFeatured(ArrayList<PlaceReview> listPlaceReview){
        Fragment fragment = new ListLoader(listPlaceReview);
        FragmentManager manager =  this.getFragmentManager();
        FragmentTransaction fragmentTransaction  = manager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


}
