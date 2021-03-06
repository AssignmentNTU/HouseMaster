package universitylife.com.housemaster;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.parse.*;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OfferForm extends Fragment implements PlaceSelectionListener, View.OnClickListener {

    private String currentUser;
    private SharedPreferences shared;
    private Context context;
    private ParseFile photo;

    //attribute inside the view
    private PlaceAutocompleteFragment location_autocomplete;
    private EditText price_text;
    private EditText description_text;
    private CheckBox sale_checkBox;
    private CheckBox rent_checkBox;
    private EditText phone_text;
    private Button offerButton;
    private Button takePhotoButton;
    private ImageView  cameraView;
    private Place place;


    public OfferForm(){
        // Required empty public constructor
    }

    public OfferForm(Context c) {
        this.context  = c;
        shared = context.getSharedPreferences("UserPrefs", context.MODE_PRIVATE);
        currentUser = shared.getString("UserName",null);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //declaration all attribute
        View currView = inflater.inflate(R.layout.fragment_offer, container, false);
        location_autocomplete = (PlaceAutocompleteFragment) getChildFragmentManager().findFragmentById(R.id.location_autocomplete);
        price_text = (EditText) currView.findViewById(R.id.offer_price);
        description_text = (EditText) currView.findViewById(R.id.description);
        offerButton = (Button) currView.findViewById(R.id.offer_post_button);
        takePhotoButton = (Button) currView.findViewById(R.id.image_takephoto_button);
        cameraView = (ImageView) currView.findViewById(R.id.image_result);
        sale_checkBox = (CheckBox) currView.findViewById(R.id.offer_sell);
        rent_checkBox = (CheckBox) currView.findViewById(R.id.offer_rent);
        phone_text = (EditText) currView.findViewById(R.id.phone_number);
        //when take photo is clicked
        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

        location_autocomplete.setOnPlaceSelectedListener(this);

        offerButton.setOnClickListener(this);



        return currView;
    }

    public void addToDatabaseFromUser(final ArrayList<PlaceReview> listPlaceReview){
        // Inflate the layout for this fragment
        ParseQuery<UserData> query = ParseQuery.getQuery("UserData");
        //query.whereEqualTo("username",userName);
        query.findInBackground(new FindCallback<UserData>() {
            @Override
            public void done(List<UserData> objects, ParseException e) {
                if(e == null){
                    for(int i = 0 ; i < objects.size() ; i++){
                        UserData getUser = objects.get(i);
                        Log.e("userName: ",getUser.getuserName());
                        if(getUser.getuserName().equals(currentUser)){
                            //then we update their placeReview List
                            //getUser.addPlaceReview(listPlaceReview);
                            PlaceReviewDao insertDatabase = new PlaceReviewCollectParse(context);
                            insertDatabase.addToParse(getUser,listPlaceReview);
                            Log.e("sizeList: ",getUser.getPlaceReviewList().size()+"");
                            getUser.saveInBackground();
                            Toast.makeText(context,"Successfully uploaded",Toast.LENGTH_LONG).show();
                            break;
                        }
                    }
                }
            }
        });
    }


    public ArrayList<PlaceReview> getExistedPlaceReview(){
        // Inflate the layout for this fragment
        ParseQuery<UserData> query = ParseQuery.getQuery("UserData");
        //query.whereEqualTo("username",userName);
        ArrayList list = null;
        query.findInBackground(new FindCallback<UserData>() {
            @Override
            public void done(List<UserData> objects, ParseException e) {
                if(e == null){
                    for(int i = 0 ; i < objects.size() ; i++){
                        UserData getUser = objects.get(i);
                        if(getUser.getuserName().equals(currentUser)){
                            //then we update their placeReview List
                            ArrayList list = getUser.getPlaceReviewList();
                        }
                    }
                }
            }
        });
        return list;
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bp = (Bitmap) data.getExtras().get("data");
        cameraView.setImageBitmap(bp);
        //change into parseFile first
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();
        photo = new ParseFile("image.jpg",bitmapdata);
        try {
            photo.save();
        } catch (ParseException e) {
            Log.e("cannot be svae","haha");
            e.printStackTrace();
        }

    }


    @Override
    public void onPlaceSelected(Place place) {
        this.place = place;
    }

    @Override
    public void onError(Status status) {
        // fail silently...
        Log.e("place_autocomplete", status.getStatusMessage());
    }

    @Override
    public void onClick(View view) {
        if (this.place == null) {
            Toast.makeText(context, "Please select a location.", Toast.LENGTH_SHORT).show();
            return;
        }

        //collect data
        String hdbname = this.place.getName().toString();
        String address = this.place.getAddress().toString();
        LatLng position = this.place.getLatLng();
        String priceText = price_text.getText().toString();
        String descriptiontext = description_text.getText().toString();
        String phonetext = phone_text.getText().toString();
        boolean rentCheck = rent_checkBox.isChecked();
        boolean sellCheck = sale_checkBox.isChecked();
        //list amenities will be compute by system later
        //String hdbName, String description,String price,ParseFile imageFile,ArrayList<String> listAmenities,boolean rent,boolean sale,String phoneNumber
        //data added
        ArrayList<String> listPlace = new ArrayList<String>();
        if(photo != null) {
            ParseObject.registerSubclass(PlaceReview.class);
            final PlaceReview placeAdded = new PlaceReview(hdbname, address, position, descriptiontext,priceText, photo, listPlace,rentCheck,sellCheck,phonetext,currentUser);
            ArrayList<PlaceReview> listPlaceReview = getExistedPlaceReview();
            if(listPlaceReview == null){
                //never create list before then just create new and store it to the database
                listPlaceReview = new ArrayList<PlaceReview>();
                listPlaceReview.add(placeAdded);
            }else{
                listPlaceReview.add(placeAdded);
            }

            final ArrayList<PlaceReview> finalListPlaceReview = listPlaceReview;
            addToDatabaseFromUser(finalListPlaceReview);
        }else{
            Toast.makeText(context,"Please upload photo",Toast.LENGTH_LONG).show();
        }
    }
}
