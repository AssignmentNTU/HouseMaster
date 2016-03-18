package universitylife.com.housemaster;

import android.content.Context;
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

    public AccountManager(Context context){
        this.context = context;
    }

    public void verifySignUp(final String email,final String userName,String password){
        ParseObject.registerSubclass(UserData.class);
        Parse.initialize(context, "q1ATuG6Ju9jfk0JF9wAvcP3Qnc060gTFBbg8MoXz", "TMbRe5o5wajxErJ3akzQmnvaQlBxuzg4LGc2CWSd");
        //before save it we need to make sure that the username is unique
        ParseQuery<UserData> query = ParseQuery.getQuery("UserData");
        //query.whereEqualTo("username",userName);
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
                            Log.e("test","test");
                        }
                    }
                }
            }
        });

            UserData newUser = new UserData(email, userName, password);
            newUser.saveInBackground();
    }

    public boolean verifyLogin(final String userName, final String password){
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
                                pass = false;
                            }else{
                                Toast.makeText(context,"Wrong Password",Toast.LENGTH_LONG).show();
                                pass = false;
                            }
                            return;
                        }
                    }
                    Toast.makeText(context,"User does not exist",Toast.LENGTH_LONG).show();
                    pass = false;
                }
            }
        });

        if(pass){
            return true;
        }else{
            return false;
        }

    }


}
