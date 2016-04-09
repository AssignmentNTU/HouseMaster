package universitylife.com.housemaster;


import java.util.ArrayList;


public interface SearchInterface  {

    public ArrayList<PlaceReview> doSearching(String hdbName,String amenitiyText,String lowestPrice,String highestPrice,boolean rent,boolean sell,ArrayList<PlaceReview> listPlaceReview);

    public ArrayList<PlaceReview> doSearching(String contentSearch);

    public void startFragmentFeatured(ArrayList<PlaceReview> listPlaceReview);

}
