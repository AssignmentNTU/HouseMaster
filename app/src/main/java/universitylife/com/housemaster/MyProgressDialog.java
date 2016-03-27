package universitylife.com.housemaster;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by LENOVO on 21/03/2016.
 */
public class MyProgressDialog {

    private Context myContext  = null;
    ProgressDialog pDialog;

    public MyProgressDialog(Context context){
        this.myContext = context;
    }

    public void showProgressDialogRetrieveData(){

        pDialog = new ProgressDialog(myContext);
        pDialog.setMessage("Getting Data ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

    }

    public void dismissDialogRetrieveData(){

        pDialog.dismiss();
    }


}
