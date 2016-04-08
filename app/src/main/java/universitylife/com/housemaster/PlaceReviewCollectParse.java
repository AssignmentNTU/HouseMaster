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
public class PlaceReviewCollectParse implements PlaceReviewDao{
    //in here i will retrieve all place that is sold or rent by user of HosueMaster

    private Context context;


    public PlaceReviewCollectParse(Context context){
        this.context = context;
    }


    @Override
    public ArrayList<PlaceReview> getPlaceReviewList() {
        return null;
    }

    public void getPlaceReviewList(final ArrayList<PlaceReview> listPlaceReview){


        //so basically in my parse cloud accoutn there has already had a relational database of PlaceReview
        final AmenitiesGenerator generator = new AmenitiesGenerator(context,listPlaceReview,"gym,department_store");
        ParseQuery<PlaceReview> query = ParseQuery.getQuery("PlaceReview");
        query.findInBackground(new FindCallback<PlaceReview>() {
            @Override
            public void done(List<PlaceReview> objects, ParseException e) {
                for(int i = 0 ;i < objects.size() ; i++){
                    PlaceReview place = objects.get(i);
                    generator.getAmenitiesListLong(place.getHdbName(),place.getHdbDescription(),place.getPrice(),place.isRent(),place.isSold(),place.getPhoneNumber(),place.getParseFile());
                }
            }
        });

    }

    @Override
    public void addToParse(UserData user, ArrayList<PlaceReview> listPlaceReview) {
        user.addPlaceReview(listPlaceReview);
    }


}
