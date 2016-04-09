package universitylife.com.housemaster;

import java.util.ArrayList;

/**
 * Created by LENOVO on 08/04/2016.
 */
public interface PlaceReviewDao{

    public ArrayList<PlaceReview> getPlaceReviewList();
    public void getPlaceReviewList(ArrayList<PlaceReview> listPlaceReview);
    public void addToParse(UserData user,ArrayList<PlaceReview> listPlaceReview);



}
