package universitylife.com.housemaster;

//this is manager class that responsible to control the search view

import android.app.Fragment;
import android.content.Context;

public class SearchManager {

    //attribute that is needed to control the view of search
    private Fragment searchForm;

    public SearchManager(Fragment searchForm){
        this.searchForm = searchForm;
    }

    public Fragment setSearchForm(){
        return (Fragment)searchForm;
    }


}

