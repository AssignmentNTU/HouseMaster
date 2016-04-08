package universitylife.com.housemaster;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
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

public class NavigationDrawer extends Activity {


    private String[] drawerListViewItems;
    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private PlaceReviewCollect prc;


    //SharedPreference to create session for user login
    private SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        //Image
        Integer[] imageId = {R.drawable.star,R.drawable.place,R.drawable.offer,R.drawable.place,R.drawable.offering,R.drawable.profile,R.drawable.setting,R.drawable.help,R.drawable.history};

        // get list items from strings.xml
        drawerListViewItems = getResources().getStringArray(R.array.navigationDrawer_items);

        // get ListView defined in activity_main.xml
        drawerListView = (ListView) findViewById(R.id.left_navigation_drawer);

        // Set the adapter for the list view
        /*
        drawerListView.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, drawerListViewItems));*/

        //use custom adapter as the listViewAdapter
        CustomList customAdapter = new CustomList(this,drawerListViewItems,imageId);
        drawerListView.setAdapter(customAdapter);
        // App Icon
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);



        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description */
                R.string.navigation_drawer_close  /* "close drawer" description */
        );

        // Set actionBarDrawerToggle as the DrawerListener
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(244,67,54)));
        drawerListView.setOnItemClickListener(new DrawerItemClickListener());

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
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // call ActionBarDrawerToggle.onOptionsItemSelected(), if it returns true
        // then it has handled the app icon touch event
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            displayView(position);
            drawerLayout.closeDrawer(drawerListView);
        }

        private void displayView(int position)
        {
            switch (position)
            {
                case 0:
                    //this is featured option when it is clicked then the fragment View is changing
                    changeToFeaturedFragment();
                    break;
                case 1:
                    changeToSearchFormNews();
                    break;

                case 2:
                    //for SellAndRent
                    changeToSellRentView();
                    break;
                case 3:
                    //for SellAndRentSearch
                    changeToSearchFormSellRent();
                    break;
                case 4:
                    //go to offer form
                    changeToOfferForm();
                    break;
                case 5:
                    //go to your profile
                    changeToProfile();
                    break;
                case 6:
                    //log out from your profile
                    logOut();
                    break;
                default:
                  //  changeToFeaturedFragment();
                    break;
            }

        }



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
        FactorySearchForm factory = new FactorySearchForm(this,prc);
        Fragment producedFragment = (Fragment)factory.getSearchForm(2);
        SearchManager managerSearch = new SearchManager(producedFragment);
        Fragment fragment = managerSearch.setSearchForm();
        FragmentManager manager =  this.getFragmentManager();
        FragmentTransaction fragmentTransaction  = manager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    //for search View Sell Rent
    public void changeToSearchFormSellRent(){
        FactorySearchForm factory = new FactorySearchForm(this,prc);
        Fragment producedFragment = (Fragment)factory.getSearchForm(1);
        SearchManager managerSearch = new SearchManager(producedFragment);
        Fragment fragment = managerSearch.setSearchForm();
        FragmentManager manager =  this.getFragmentManager();
        FragmentTransaction fragmentTransaction  = manager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    //to go to the sell/Rent view
    public void changeToSellRentView(){
        PlaceReviewDao prc = new PlaceReviewCollectParse(getBaseContext());
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

    public void logOut(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }




}



