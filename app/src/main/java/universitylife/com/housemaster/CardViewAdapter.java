package universitylife.com.housemaster;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by LENOVO on 19/03/2016.
 */
public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder>
{

    private ArrayList<PlaceReview> placeReviewList;

    public CardViewAdapter(ArrayList<PlaceReview> placeReviewList){
        this.placeReviewList = placeReviewList;
    }


    @Override
    public CardViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, null);

        // create ViewHolder
        //the idea of cardholder is firstly it will create a veiw of each list
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //this will bind the placeReviewList with the viewHolder class
        holder.hdbName.setText(placeReviewList.get(position).getHdbName()+"");
        holder.imageIcon.setImageResource(placeReviewList.get(position).getImageUrl());
        Log.e("imagePosition: ",placeReviewList.get(position).getImageUrl()+"");
        //because list Amenities is in the ArrayList form so need to change the representation to a String with commas
        String listingAmenities = "";
        for(String k : placeReviewList.get(position).getListAmenities()){
            listingAmenities += k+",";
        }
        if(listingAmenities.length()> 0) {
            listingAmenities = listingAmenities.substring(0, listingAmenities.length() - 1);
        }
        holder.textListAmenities.setText("List Amenities: "+listingAmenities);
        holder.textLocation.setText("Address: "+placeReviewList.get(position).getLocation());
        holder.textPrice.setText("Price: "+placeReviewList.get(position).getPrice());
    }



    @Override
    public int getItemCount() {
        return placeReviewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView hdbName;
        private ImageView imageIcon;
        private TextView textListAmenities;
        private TextView textLocation;
        private TextView textPrice;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            hdbName =(TextView) itemLayoutView.findViewById(R.id.hdbName);
            imageIcon = (ImageView) itemLayoutView.findViewById(R.id.thumbnailHDB);
            textListAmenities = (TextView) itemLayoutView.findViewById(R.id.listAmenities);
            textLocation = (TextView) itemLayoutView.findViewById(R.id.locationHDB);
            textPrice = (TextView) itemLayoutView.findViewById(R.id.priceHDB);
        }

    }
}
