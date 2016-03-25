package universitylife.com.housemaster;

import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LENOVO on 25/03/2016.
 */
public class PlaceReviewCollectParse {
    //in here i will retrieve all place that is sold or rent by user of HosueMaster

    public ArrayList<PlaceReview> getPlaceReviewList(){
        ParseQuery<UserData> query = ParseQuery.getQuery("UserData");
        //query.whereEqualTo("username",userName);
        query.findInBackground(new FindCallback<UserData>() {
            @Override
            public void done(List<UserData> objects, ParseException e) {
                if(e == null){
                    //we will search all the UserData and retrieve
                }
            }
        });

        return null;
    }


}
