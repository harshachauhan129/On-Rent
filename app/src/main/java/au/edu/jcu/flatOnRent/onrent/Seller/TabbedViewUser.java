package au.edu.jcu.flatOnRent.onrent.Seller;

import android.app.Dialog;
import android.content.Intent;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import au.edu.jcu.flatOnRent.onrent.BuildConfig;
import au.edu.jcu.flatOnRent.onrent.FirebaseHelper.EnternNumber;
import au.edu.jcu.flatOnRent.onrent.Fragmentation.books;
import au.edu.jcu.flatOnRent.onrent.Fragmentation.electronics;
import au.edu.jcu.flatOnRent.onrent.Fragmentation.pg;
import au.edu.jcu.flatOnRent.onrent.R;
import au.edu.jcu.flatOnRent.onrent.helper.ViewPagerAdapter;


import au.edu.jcu.flatOnRent.onrent.startingPages.LocationActivity;
import au.edu.jcu.flatOnRent.onrent.upload_data.UploadForms;


public class TabbedViewUser extends AppCompatActivity {

    Toolbar toolbar1;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    ImageView profile;
    FirebaseAuth auth;

    String APP_PNAME="On Rent";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_demo);

        auth = FirebaseAuth.getInstance();
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        toolbar1 = (Toolbar) findViewById(R.id.toolbar1);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);


        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new books(), "Books");
        viewPagerAdapter.addFragments(new pg(), "Pg/Flats");
        viewPagerAdapter.addFragments(new electronics(), "Electronics");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tabmenubar, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.rate:
                //Toast.makeText(TabbedViewUser.this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME)));


                break;
            case R.id.share:
//                Toast.makeText(TabbedViewUser.this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "On Rent");
                    String shareMessage = "/nLet me recommend you this application/n/n";
                    shareMessage = shareMessage + "http://play.google.com/store/app/details?id=" + BuildConfig.APPLICATION_ID + "/n/n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "select any one app"));

                } catch (Exception e) {
                    System.out.println(e);
                }

                break;
            case R.id.upload:
                Intent i_upload = new Intent(getApplicationContext(), UploadForms.class);
                startActivity(i_upload);
                break;
            case R.id.version:
//                Toast.makeText(TabbedViewUser.this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                Dialog d=new Dialog(this);
                d.setContentView(R.layout.activity_version);
                d.show();

                break;
            case R.id.logout:
                //Toast.makeText(TabbedViewUser.this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                auth.signOut();
                Intent i = new Intent(getApplicationContext(), EnternNumber.class);
                startActivity(i);
                finish();
                break;
            case R.id.settings:
                //Toast.makeText(TabbedViewUser.this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                Intent i_settings=new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i_settings);

                break;
            case R.id.profile:
                Intent p = new Intent(getApplicationContext(), SellerProfile.class);
                startActivity(p);
                break;
            case R.id.lock:
                Intent l = new Intent(getApplicationContext(), LocationActivity.class);
                startActivity(l);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
