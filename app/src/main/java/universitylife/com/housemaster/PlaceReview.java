package universitylife.com.housemaster;

/**
 * Created by LENOVO on 19/03/2016.
 */
public class PlaceReview {
    private String hdbName;
    private String description;
    private int imageUrl;

    public PlaceReview(String hdbName,String hdbDescription,int imageUrl){
        this.hdbName = hdbName;
        this.description = hdbDescription;
        this.imageUrl = imageUrl;
    }


    //just for the getter
    public String getHdbName(){
        return hdbName;
    }


    public String getHdbDescription(){
        return description;
    }


    public int getImageUrl(){
        return imageUrl;
    }


}
