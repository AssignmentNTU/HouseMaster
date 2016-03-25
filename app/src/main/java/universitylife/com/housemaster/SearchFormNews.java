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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFormNews extends Fragment {

    private PlaceReviewCollect prc;
    private ArrayList<PlaceReview> listPlaceReview;


    //attribute within the views
    Button buttonSearch;
    EditText textSearchNews;



    public SearchFormNews() {

    }

    public SearchFormNews(PlaceReviewCollect prc) {
        this.prc = prc;
        listPlaceReview = prc.getPlaceReviewList();
        Log.e("Place Review :",listPlaceReview.toString());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //get the view of fragment
        View currentView = inflater.inflate(R.layout.fragment_search_form_news, container, false);
        //declare all the attribute
        textSearchNews = (EditText) currentView.findViewById(R.id.newsSearchText);
        buttonSearch = (Button) currentView.findViewById(R.id.buttonSearchNews);


        Log.e("searching","true");
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String searchContent = textSearchNews.getText().toString();
                Log.e("Click","true");
                Log.e("Content",searchContent);
                if(searchContent != null) startFragmentFeatured(doSearching(searchContent));
                else Toast.makeText(getActivity(),"Search content is blank",Toast.LENGTH_LONG).show();
            }
        });

        return currentView;
    }


    public void startFragmentFeatured(ArrayList<PlaceReview> listPlaceReview){
        Fragment fragment = new Featured(listPlaceReview);
        FragmentManager manager =  this.getFragmentManager();
        FragmentTransaction fragmentTransaction  = manager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }



    public ArrayList<PlaceReview> doSearching(String searchContent){
        //we have already get PRC object that contains all the placeReviewList
        //so the idea is just to filter the PRC
        ArrayList<PlaceReview> filterPlaceReview = new ArrayList<PlaceReview>();
        for(int i = 0 ; i < listPlaceReview.size() ; i++){
            PlaceReview place = listPlaceReview.get(i);
            String hdbFilteredName = filterHDBName(place.getHdbName());
            String hdbFilterName1 = filterHDBName(searchContent.toLowerCase());
            Log.e("placeHDBName1",hdbFilteredName);
            Log.e("placeHDBName2",searchContent.toLowerCase());
            if(hdbFilterName1.equals(hdbFilteredName)){
                filterPlaceReview.add(place);
                Log.e("it is true","true");
            }
        }
        return filterPlaceReview;
    }


    public String filterHDBName(String hdbName){
        int space = 0;
        for(char c : hdbName.toCharArray()){
            if(c == ' '){
                break;
            }
            space++;
        }

        String hdbFiltered = hdbName.substring(0,space);
        hdbFiltered = hdbFiltered.toLowerCase();
        Log.e("HDBName",hdbFiltered);
        return hdbFiltered;
    }

}
