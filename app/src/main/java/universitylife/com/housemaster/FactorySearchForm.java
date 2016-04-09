package universitylife.com.housemaster;

import android.app.Activity;
import android.app.Application;

/**
 * Created by LENOVO on 08/04/2016.
 */
public class FactorySearchForm {

    //attribute needed for the subclass of SearchInterface
    private Activity activity;
    private PlaceReviewCollect prc;


    public FactorySearchForm(Activity activity,PlaceReviewCollect prc){
        this.activity = activity;
        this.prc = prc;
    }


    public SearchInterface getSearchForm(int option){
        //option one means SearchFrom
        //option two means SearchFormNews
        SearchInterface searchForm = null;
        if(option == 1){
            searchForm  = new SearchForm(activity);
            return searchForm;
        }else if(option == 2){
            searchForm = new SearchFormNews(prc);
            return searchForm;
        }else{
            return null;
        }
    }

}
