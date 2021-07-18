package au.edu.jcu.flatOnRent.onrent.startingPages;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import au.edu.jcu.flatOnRent.onrent.FirebaseHelper.EnternNumber;
import au.edu.jcu.flatOnRent.onrent.R;
import au.edu.jcu.flatOnRent.onrent.Seller.TabbedViewUser;

public class SplashScreen extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser fb_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        auth=FirebaseAuth.getInstance();
        fb_user=auth.getCurrentUser();


        final Thread thread=new Thread(){
            @Override
            public void run() {
                try
                {
                    Thread.sleep(10000);

                    if(fb_user==null){
                        Intent i=new Intent(getApplicationContext(), EnternNumber.class);
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        Intent i=new Intent(getApplicationContext(), TabbedViewUser.class);
                        startActivity(i);
                        finish();
                    }


                }

                catch (Exception e)
                {
                    System.out.print(e);
                }
            }
        };
        thread.start();
    }


}
