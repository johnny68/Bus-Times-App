package shubham.in.bustimings.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import shubham.in.bustimings.Login.Fragments.Login;
import shubham.in.bustimings.Misc.SharedConstants;
import shubham.in.bustimings.R;
import shubham.in.bustimings.UI.MainActivity;

import static shubham.in.bustimings.Misc.SharedConstants.IS_LOGGED_IN;
import static shubham.in.bustimings.Misc.SharedConstants.PREF_LOGIN;

public class LoginActivity extends AppCompatActivity {

    private static FragmentManager fragmentManager;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity_login);
        fragmentManager = this.getSupportFragmentManager();
        sharedPreferences = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);

        if (sharedPreferences.getBoolean(IS_LOGGED_IN, false))
        {
            gotoProfile();
            Log.d("Already Logged in", "Going to Main Profile Page");

        }
        else
        {
            if (savedInstanceState == null){
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.frame, new Login(),
                                SharedConstants.Login_Fragment).commit();
            }
        }
        findViewById(R.id.close_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void replaceFragment(String Tag) {

        if (Tag.equals("right")) {
            fragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                    .replace(R.id.frame, new Login(), SharedConstants.Login_Fragment)
                    .commit();
        }
        else if (Tag.equals("left")){
            fragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                    .replace(R.id.frame, new Login(), SharedConstants.Login_Fragment)
                    .commit();
        }

    }

    @Override
    public void onBackPressed(){
        Fragment ForgotPassword = fragmentManager.findFragmentByTag(SharedConstants.Forgot_Password);
        Fragment SignUpFragment = fragmentManager.findFragmentByTag(SharedConstants.SignUp_Fragment);
        if (ForgotPassword != null){
            replaceFragment("left");
        }
        else if (SignUpFragment != null){
            replaceFragment("right");
        }
        else
        {
            super.onBackPressed();
        }
    }

    private void gotoProfile() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}