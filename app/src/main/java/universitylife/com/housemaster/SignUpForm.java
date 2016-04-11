package universitylife.com.housemaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpForm extends AppCompatActivity {

    //for initialize the attribute
    //editText
    private EditText usernameText;
    private EditText passwordText;
    private EditText emailText;
    //Button
    private Button signUpButton;
    //String
    private String username;
    private String password;
    private String email;

    //other class
    private final AccountManager account = new AccountManager(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        //find the route for all variable
        usernameText = (EditText) findViewById(R.id.userNameText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        emailText    = (EditText) findViewById(R.id.emailText);


        //declare Button
        signUpButton = (Button) findViewById(R.id.buttonSignUp);



        //login
        //if general login is clicked
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameText.getText().toString();
                password = passwordText.getText().toString();
                email = emailText.getText().toString();
                //general Login is clicked
                Log.e("Click", "Loginisclicked");
                account.verifySignUp(email,username,password);
            }
        });

    }

    private void goToSearchForm(){
        Intent intent = new Intent(this,NavigationDrawer.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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
}
