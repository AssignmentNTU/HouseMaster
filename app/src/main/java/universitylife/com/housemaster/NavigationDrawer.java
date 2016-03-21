package universitylife.com.housemaster;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NavigationDrawer extends Activity {


    private String[] drawerListViewItems;
    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        //Image
        Integer[] imageId = {R.drawable.star,R.drawable.offer,R.drawable.place,R.drawable.offering,R.drawable.profile,R.drawable.setting,R.drawable.help,R.drawable.history};

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
                    //Toast.makeText(MainActivity.this, "2", Toast.LENGTH_LONG).show();
                    break;

                case 2:
                    //Toast.makeText(MainActivity.this, "3", Toast.LENGTH_LONG).show();

                default:
                    break;
            }

        }



    }

    //for featured view
    public void changeToFeaturedFragment(){
        Fragment fragment = new Featured();
        FragmentManager manager =  this.getFragmentManager();
        FragmentTransaction fragmentTransaction  = manager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }





}



