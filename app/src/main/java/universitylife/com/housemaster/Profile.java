package universitylife.com.housemaster;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    //context application of NavigationDrawer
    private Context context;


    //so the purpose of this class is to load the profile of user
    private SharedPreferences shared;

    //attribute
    private TextView textUserName;
    private TextView textEmail;
    private FrameLayout myAmenities;


    //value attribute
    private String userName;
    private String userEmail;
    private ArrayList<PlaceReview> listPlaceReview = new ArrayList<PlaceReview>();






    public Profile() {
        // Required empty public constructor

    }


    public Profile(Context context){
        this.context = context;
        shared = context.getSharedPreferences("UserPrefs", context.MODE_PRIVATE);
        userName = shared.getString("UserName",null);
        userEmail = shared.getString("Email",null);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View currentView = inflater.inflate(R.layout.fragment_profile, container, false);

        //declaration of all attribute
        textUserName = (TextView) currentView.findViewById(R.id.name_profile);
        textEmail = (TextView) currentView.findViewById(R.id.name_email);

        //set the name of user in the profile
        textUserName.setText("Name:  "+userName);
        textEmail.setText("Email: "+userEmail);
        getMyListAmenities();
        return currentView;
    }


    public void getMyListAmenities(){
        //so basically in my parse cloud accoutn there has already had a relational database of PlaceReview
        ParseQuery<PlaceReview> query = ParseQuery.getQuery("PlaceReview");
        //to load the amenities
        ParseObject.registerSubclass(PlaceReview.class);
        //ParseObject User
        final ArrayList<PlaceReview>[] list = new ArrayList[1];
        query.findInBackground(new FindCallback<PlaceReview>() {
            @Override
            public void done(List<PlaceReview> objects, ParseException e) {
                //if the usetName equals with current userName then put it to the listPlaceReview
                for(int i = 0 ; i < objects.size() ; i++){
                    Log.e("userName",userName);
                    Log.e("userObjectName",objects.get(i).getUsername());
                    if(objects.get(i).getUsername().equals(userName)){
                        PlaceReview place = objects.get(i);
                        listPlaceReview.add(place);
                    }
                }
                loadFragment(listPlaceReview);
            }
        });

    }


    public void loadFragment(ArrayList<PlaceReview> list){
        Fragment fragment = new ListLoader(list);
        FragmentManager manager =  this.getFragmentManager();
        FragmentTransaction fragmentTransaction  = manager.beginTransaction();
        fragmentTransaction.replace(R.id.list_my_amenities,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }



}
