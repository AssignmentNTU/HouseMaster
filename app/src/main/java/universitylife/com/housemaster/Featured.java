package universitylife.com.housemaster;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Featured extends Fragment {

    //decalration of new attribute
    private Button buttonRefreshed;

    public Featured() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //in featured you can view the current offer that the system currently collecting
        View featureView = inflater.inflate(R.layout.fragment_featured, container, false);
        RecyclerView recyclerView = (RecyclerView) featureView.findViewById(R.id.featureView);
        buttonRefreshed = (Button) featureView.findViewById(R.id.refresehButton);


        //mock up the PlaceReview object first
        PlaceReview place[] = new PlaceReview[]{
                new PlaceReview("Jurong","best for young people",R.drawable.hdb1),
                new PlaceReview("NTU hall","best for student",R.drawable.hdb2)
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CardViewAdapter adapter = new CardViewAdapter(place);
        recyclerView.setAdapter(adapter);

        //if the refreshed button is clicked

        return featureView;
    }



}
