package universitylife.com.housemaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {
    //all the attribute here
    //for Button
    private ImageButton buttonSignIn;
    private ImageButton buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //declare all here
        buttonSignIn = (ImageButton) findViewById(R.id.signInButton);
        buttonRegister = (ImageButton) findViewById(R.id.registerButton);

        //when button signIn is clicked
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //go to sign in activity through fragment
                goToSignInLayout();

            }
        });


        //when button signUp is clicked
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to register activity
                goToRegisterLayout();
            }
        });



    }

    private void goToSignInLayout(){
        Intent intent = new Intent(this,LoginForm.class);
        startActivity(intent);
    }


    private void goToRegisterLayout(){
        Intent intent = new Intent(this,SignUpForm.class);
        startActivity(intent);
    }

    /*
    private void goToSignInFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        SignInFragment  signInFragment = new SignInFragment();
        transaction.add(R.id.mainLayout,signInFragment);
        transaction.commit();

    }
    */



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
