package universitylife.com.housemaster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import universitylife.com.housemaster.UserData;

/**
 * Created by LENOVO on 02/03/2016.
 */
public class AccountManager {

    private Context context;
    private boolean pass = true;
    //sharedPreference to save user session
    private SharedPreferences shared;

    public AccountManager(Context context){
        this.context = context;
    }

    public void verifySignUp(final String email,final String userName, final String password){
        ParseObject.registerSubclass(UserData.class);
        Parse.initialize(context, "q1ATuG6Ju9jfk0JF9wAvcP3Qnc060gTFBbg8MoXz", "TMbRe5o5wajxErJ3akzQmnvaQlBxuzg4LGc2CWSd");
        //before save it we need to make sure that the username is unique
        ParseQuery<UserData> query = ParseQuery.getQuery("UserData");
        //query.whereEqualTo("username",userName);
        final MyProgressDialog dialog = new MyProgressDialog(context);
        dialog.showProgressDialogRetrieveData();
        query.findInBackground(new FindCallback<UserData>() {
            @Override
            public void done(List<UserData> objects, ParseException e) {
                if(e == null){
                    Log.e("Object: ",objects+"");
                    for(int i = 0 ; i < objects.size() ; i++){
                        UserData getUser = objects.get(i);
                        if(getUser.getuserName().equals(userName) || getUser.getEmail().equals(email) ){
                            //it means username is exist
                            Toast.makeText(context,"username or email does exist",Toast.LENGTH_LONG).show();
                            pass = false;
                            Log.e("test","test1");
                            return;
                        }
                    }
                    UserData newUser = new UserData(email, userName, password);
                    newUser.saveInBackground();
                    //to simulate user login i will use SharePreference
                    shared = context.getSharedPreferences("UserPrefs",context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = shared.edit();
                    edit.putString("UserName",userName);
                    edit.putString("Email",email);
                    edit.commit();
                    dialog.dismissDialogRetrieveData();
                    goToSearchForm();
                }
            }
        });


    }

    public void verifyLogin(final String userName, final String password){
        //run a progress dialog
        final MyProgressDialog dialog = new MyProgressDialog(context);
        dialog.showProgressDialogRetrieveData();

        //so basically i will retrieve all the object first
        ParseObject.registerSubclass(UserData.class);
        Parse.initialize(context, "q1ATuG6Ju9jfk0JF9wAvcP3Qnc060gTFBbg8MoXz", "TMbRe5o5wajxErJ3akzQmnvaQlBxuzg4LGc2CWSd");
        ParseQuery<UserData> query = ParseQuery.getQuery("UserData");
        //query.whereEqualTo("username",userName);
        query.findInBackground(new FindCallback<UserData>() {
            @Override
            public void done(List<UserData> objects, ParseException e) {
                if(e == null){
                    for(int i = 0 ; i < objects.size() ; i++){
                        UserData getUser = objects.get(i);
                        if(getUser.getuserName().equals(userName)){
                            if(getUser.get("password").equals(password)){
                                Toast.makeText(context,"Login successful",Toast.LENGTH_LONG).show();
                                //to simulate user login i will use SharePreference
                                shared = context.getSharedPreferences("UserPrefs",context.MODE_PRIVATE);
                                SharedPreferences.Editor edit = shared.edit();
                                edit.putString("UserName",userName);
                                edit.putString("Email",getUser.getEmail());
                                edit.commit();
                                dialog.dismissDialogRetrieveData();
                                goToSearchForm();
                            }else{
                                Toast.makeText(context,"Wrong Password",Toast.LENGTH_LONG).show();
                            }
                            return;
                        }
                    }
                    Toast.makeText(context,"User does not exist",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void goToSearchForm(){
        Intent intent = new Intent(context,NavigationDrawer.class);
        context.startActivity(intent);
    }



}
