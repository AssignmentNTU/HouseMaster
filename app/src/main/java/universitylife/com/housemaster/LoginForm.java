package universitylife.com.housemaster;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginForm extends Activity {

    //for initialize the attribute
    //editText
    private EditText usernameText;
    private EditText passwordText;
    //Button
    private Button loginButton;
    //String
    private String username;
    private String password;
    //another clas
    private final AccountManager accountManager = new AccountManager(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //find the route for all variable
        usernameText = (EditText) findViewById(R.id.userNameText);
        passwordText = (EditText) findViewById(R.id.passwordText);


        //declare Button
        loginButton = (Button) findViewById(R.id.buttonLogin);



        //login
        //if general login is clicked
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameText.getText().toString();
                password = passwordText.getText().toString();
                //general Login is clicked
                accountManager.verifyLogin(username,password);
                //after we sign in we will redirected to search form
               // goToSearchForm();
            }
        });
    }

    public void goToSearchForm(){
        Intent intent = new Intent(this,NavigationDrawer.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class PlaceHolderFragment extends Fragment{

    }

}
