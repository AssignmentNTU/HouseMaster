package universitylife.com.housemaster;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by LENOVO on 19/03/2016.
 */
public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder>
{
    private RecyclerView recyclerView = null;
    private ArrayList<PlaceReview> placeReviewList;

    public CardViewAdapter(ArrayList<PlaceReview> placeReviewList,RecyclerView recyclerView){
        this.placeReviewList = placeReviewList;
        this.recyclerView = recyclerView;
    }


    @Override
    public CardViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, null);

        // create ViewHolder
        //the idea of cardholder is firstly it will create a veiw of each list
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        itemLayoutView.setOnClickListener(new MyOnClickListener());

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //this will bind the placeReviewList with the viewHolder class
        holder.hdbName.setText(placeReviewList.get(position).getHdbName()+"");
        final Bitmap bitmap = null;
        final byte[] fileByte = null;
        Log.e("parsingCheck",placeReviewList.get(position).getParseFile().toString());
        final ParseFile parseFile = placeReviewList.get(position).getParseFile();
        loadImages(parseFile,holder.imageIcon);
        //holder.imageIcon.setImageURI(Uri.parse(parseFile.getUrl()));
        //because list Amenities is in the ArrayList form so need to change the representation to a String with commas
        String listingAmenities = "";
        for(String k : placeReviewList.get(position).getListAmenities()){
            listingAmenities += k+",";
        }
        if(listingAmenities.length()> 0) {
            listingAmenities = listingAmenities.substring(0, listingAmenities.length() - 1);
        }
        holder.textListAmenities.setText("List Amenities: "+listingAmenities);
        if(listingAmenities.equals("")){
            holder.textListAmenities.setText("");
        }
        if(placeReviewList.get(position).getLocation() != null) {
            holder.textLocation.setText("Address: " + placeReviewList.get(position).getLocation());
        }else{
            //sell or rent status
            String rentsell = "";
            if(placeReviewList.get(position).isRent()){
                rentsell += "RENT";
            }
            rentsell += " ";
            if(placeReviewList.get(position).isSold()){
                rentsell += "SELL";
            }
            holder.textLocation.setText("STATUS: " +rentsell);
        }
        holder.textPrice.setText("Price: "+placeReviewList.get(position).getPrice());
    }

    private void loadImages(ParseFile thumbnail, final ImageView img) {
        Log.e("after this",thumbnail.toString());
        Log.e("img: ",img.toString());
        if (thumbnail != null) {
            thumbnail.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    if (e == null) {
                        Log.e("successfull","true");
                        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                        img.setImageBitmap(bmp);
                    } else {
                    }
                }
            });
        } else {
            img.setImageResource(R.drawable.hdb1);
        }
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


    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildPosition(v);
            View currentView = recyclerView.getChildAt(itemPosition);
            //then from here we can go to particular activity to check the item that we clic k
            Log.e("Clicked and Position is",String.valueOf(itemPosition));
        }
    }


}
