package universitylife.com.housemaster;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.Parse;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NavigationDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private PlaceReviewCollect prc;
    private Toolbar toolbar;


    //SharedPreference to create session for user login
    private SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        //Image
        //Integer[] imageId = {R.drawable.star,R.drawable.place,R.drawable.offer,R.drawable.place,R.drawable.offering,R.drawable.profile,R.drawable.setting,R.drawable.help,R.drawable.history};

        navView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        navView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //load the list of hdb first
        prc = new PlaceReviewCollect(this);
        prc.execute();
        final ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);

        //need to crehate thread so it will wait for the previous session to be finished
        //normal 6s
        exec.schedule(new Runnable(){
            @Override
            public void run(){
                ArrayList<PlaceReview> list =  prc.getPlaceReviewList();
                for(int i = 0 ; i < list.size() ; i++){
                    PlaceReview place = list.get(i);
                    Log.e("resultingFromPlace",place.getParseFile().toString());
                }


                changeToFeaturedFragment();
            }
        }, 6, TimeUnit.SECONDS);




        //create mock user in here
        /*
        Parse.initialize(getBaseContext(), "q1ATuG6Ju9jfk0JF9wAvcP3Qnc060gTFBbg8MoXz", "TMbRe5o5wajxErJ3akzQmnvaQlBxuzg4LGc2CWSd");
        ParseObject.registerSubclass(UserData.class);
        UserData user = new UserData("edwardSujono@yahoo.com","edward","12345");
        user.addPlaceReview(new ArrayList<PlaceReview>());
        user.saveInBackground();*/
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawerLayout.closeDrawers();

        switch (item.getItemId()) {
            case R.id.featured:
                changeToFeaturedFragment();
                return true;

            case R.id.searchFeatured:
                changeToSearchFormNews();
                return true;

            case R.id.sellRent:
                changeToSellRentView();
                return true;

            case R.id.offer:
                changeToOfferForm();
                return true;

            case R.id.profile:
                changeToProfile();
                return true;
        }

        return false;
    }

    //for featured view
    public void changeToFeaturedFragment(){
        Fragment fragment = new ListLoader(prc);
        FragmentManager manager =  this.getFragmentManager();
        FragmentTransaction fragmentTransaction  = manager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    public void changeToSearchFormNews(){
        Fragment fragment = new SearchFormNews(prc);
        FragmentManager manager =  this.getFragmentManager();
        FragmentTransaction fragmentTransaction  = manager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    //for search View Sell Rent
    public void changeToSearchFormSellRent(){
        Fragment fragment = new SearchForm(this);
        FragmentManager manager =  this.getFragmentManager();
        FragmentTransaction fragmentTransaction  = manager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    //to go to the sell/Rent view
    public void changeToSellRentView(){
        PlaceReviewCollectParse prc = new PlaceReviewCollectParse(getBaseContext());
        Fragment fragment = new SellRentList(prc);
        FragmentManager manager =  this.getFragmentManager();
        FragmentTransaction fragmentTransaction  = manager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    //for offer form
    public void changeToOfferForm(){
        Fragment fragment = new OfferForm(this);
        FragmentManager manager =  this.getFragmentManager();
        FragmentTransaction fragmentTransaction  = manager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    //for profile View
    public void changeToProfile(){
        Fragment fragment = new Profile(this);
        FragmentManager manager =  this.getFragmentManager();
        FragmentTransaction fragmentTransaction  = manager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }




}



