package universitylife.com.housemaster;


import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchForm extends Fragment {



    public SearchForm() {
        // Required empty public constructor
        getActivity().getActionBar().setTitle("Find Place");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_form, container, false);
    }


}
