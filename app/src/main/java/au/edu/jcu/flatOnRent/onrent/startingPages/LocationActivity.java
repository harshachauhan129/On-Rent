package au.edu.jcu.flatOnRent.onrent.startingPages;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;

import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.Locale;

import au.edu.jcu.flatOnRent.onrent.R;
import au.edu.jcu.flatOnRent.onrent.Seller.TabbedViewUser;

public class LocationActivity extends AppCompatActivity implements LocationListener {

    FirebaseAuth auth;
    FirebaseUser fb_user;
    LocationManager locationManager;
    TextView locationText;
    Button btn_get;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        btn_get=findViewById(R.id.current_btn);
        locationText=findViewById(R.id.locat);
        auth = FirebaseAuth.getInstance();
        fb_user = auth.getCurrentUser();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }

        getLocation();



    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            final String location_traced = (locationText.getText() + "\n" + addresses.get(0).getAddressLine(0) + ", " +
                    addresses.get(0).getAddressLine(1) + ", " + addresses.get(0).getAddressLine(2));
            locationText.setText(""+location_traced);
            btn_get.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), TabbedViewUser.class);
                    i.putExtra("location_u", location_traced);
                    startActivity(i);
                    finish();
                }
            });
        } catch (Exception e) {

        }


    }





    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(LocationActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }
    public void btnClick(View v)
    {
        Intent i=new Intent(getApplicationContext(),TabbedViewUser.class);
        startActivity(i);
        finish();
    }
}