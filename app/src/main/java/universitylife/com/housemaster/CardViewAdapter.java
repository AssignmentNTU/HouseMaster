package universitylife.com.housemaster;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by LENOVO on 19/03/2016.
 */
public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder>
{

    private PlaceReview[] placeReviewList;

    public CardViewAdapter(PlaceReview[] placeReviewList){
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
        holder.hdbName.setText(placeReviewList[position].getHdbName()+"");
        holder.hdbDescription.setText(placeReviewList[position].getHdbDescription());
        holder.imageIcon.setImageResource(placeReviewList[position].getImageUrl());
    }



    @Override
    public int getItemCount() {
        return placeReviewList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView hdbName;
        private TextView hdbDescription;
        private ImageView imageIcon;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            hdbName =(TextView) itemLayoutView.findViewById(R.id.hdbName);
            hdbDescription = (TextView) itemLayoutView.findViewById(R.id.hdbDesc);
            imageIcon = (ImageView) itemLayoutView.findViewById(R.id.thumbnailHDB);

        }
    }
}
