package universitylife.com.housemaster;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LENOVO on 25/03/2016.
 */
public class PlaceReviewCollectParse {
    //in here i will retrieve all place that is sold or rent by user of HosueMaster

    private Context context;
    private ArrayList<ArrayList<PlaceReview>> listPlaceReviewAll = new ArrayList();


    public PlaceReviewCollectParse(Context context){
        this.context = context;
    }


    public void getPlaceReviewList(final ArrayList<PlaceReview> listPlaceReview){


        //so basically in my parse cloud accoutn there has already had a relational database of PlaceReview

        ParseQuery<PlaceReview> query = ParseQuery.getQuery("PlaceReview");
        query.findInBackground(new FindCallback<PlaceReview>() {
            @Override
            public void done(List<PlaceReview> objects, ParseException e) {
                for(int i = 0 ;i < objects.size() ; i++){
                    listPlaceReview.add(objects.get(i));
                }
            }
        });

    }


}
