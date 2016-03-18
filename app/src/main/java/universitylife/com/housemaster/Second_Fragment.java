package universitylife.com.housemaster;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import universitylife.com.housemaster.R;

/**
 * Created by LENOVO on 18/03/2016.
 */
public class Second_Fragment extends Fragment{

    private View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.sec_nav, container, false);
        return myView;
    }
}
