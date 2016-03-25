package universitylife.com.housemaster;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;

/**
 * Created by LENOVO on 02/03/2016.
 */

@ParseClassName("UserData")
public class UserData  extends ParseObject{
    private String email;
    private String accountType;
    private String userName;
    private String password;
    private String phoneNumber;
    //later we have ArrayList<PlaceReview>
    private ArrayList<PlaceReview> listPlaceReview;

    public UserData(){

    }

    public UserData(String email,String userName,String password){
        this.email = email;
        this.userName = userName;
        this.password = password;
        put("username",userName);
        put("email",email);
        put("password",password);
    }



    public String getEmail(){
        return (String)get("email");
    }

    public void setPassword(String password){
        this.password = password;
        put("password",password);
    }

    public String getuserName(){
        return (String) get("username");
    }

    public void setPhoneNumber(String number){
        this.phoneNumber = number;
        put("phonenumber",number);
    }

    public String getPhoneNumber(){
        return (String) get("phonenumber");
    }

    //later we have 2 other method for sell function
    public void checkListPlaceReviw(){
        //in here we need to check whether the user has already had listPlaceRview or not
       listPlaceReview = (ArrayList<PlaceReview>) get("listPlaceReview");
        if(listPlaceReview == null){
            listPlaceReview = new ArrayList<PlaceReview>();
        }
    }

    public void addPlaceReview(PlaceReview placeReview){
        if(listPlaceReview != null){

        }
    }


    public ArrayList<PlaceReview> getPlaceReviewList(){
        return listPlaceReview;
    }

}
