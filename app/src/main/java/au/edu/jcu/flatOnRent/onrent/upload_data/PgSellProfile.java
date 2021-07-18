package au.edu.jcu.flatOnRent.onrent.upload_data;


import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Random;

import au.edu.jcu.flatOnRent.onrent.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PgSellProfile extends Fragment {


    public PgSellProfile() {
        // Required empty public constructor
    }

    FirebaseUser user;
    FirebaseAuth mAuth;
    static DatabaseReference db_pg;
    static  String mobe_number;

    static HashMap<String, String> pg_hash_map = new HashMap<>();

    TextInputEditText address, rent, security, location, aboutt_pg;
    Spinner pg_accomodation, pg_meal, pg_water, pg_time, pg_rooms, pg_for, pg_furnished;
    Button btn_pg;
    static Uri urip;
    static ImageView imageadd, image2;

    @Override


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pg_sell_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        address = view.findViewById(R.id.addre);
        location = view.findViewById(R.id.accomodation);
        rent = view.findViewById(R.id.rent_amount);
        security = view.findViewById(R.id.security_money);

        image2 = view.findViewById(R.id.pgimg);
        imageadd = view.findViewById(R.id.add_pg);


        aboutt_pg = view.findViewById(R.id.desc_pg);
        pg_accomodation = view.findViewById(R.id.accomodation_s);
        pg_meal = view.findViewById(R.id.meal);
        pg_water = view.findViewById(R.id.water);
        pg_time = view.findViewById(R.id.tiime);
        pg_rooms = view.findViewById(R.id.rooms);
        pg_for = view.findViewById(R.id.a_for);
        pg_furnished = view.findViewById(R.id.furnished);

        btn_pg = view.findViewById(R.id.submit_pg);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser current_user = mAuth.getCurrentUser();

        mobe_number = current_user.getPhoneNumber().toString();
        db_pg = FirebaseDatabase.getInstance().getReference().child("Upload_pg");
        btn_pg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a_address = address.getText().toString();
                String a_location = location.getText().toString();
                String a_rent = rent.getText().toString();
                String a_security = security.getText().toString();

                String accomodation = pg_accomodation.getSelectedItem().toString();
                String meal = pg_meal.getSelectedItem().toString();
                String water = pg_water.getSelectedItem().toString();
                String time = pg_time.getSelectedItem().toString();
                String rooms = pg_rooms.getSelectedItem().toString();
                String a_for = pg_for.getSelectedItem().toString();
                String furnished = pg_furnished.getSelectedItem().toString();

                String desc = aboutt_pg.getText().toString();
                if (a_address.isEmpty()) {
                    address.setError("Enter complete address");
                    address.setFocusable(true);
                } else if (a_location.isEmpty()) {
                    location.setError("Enter your Location");
                    location.setFocusable(true);
                } else if (desc.isEmpty()) {
                    aboutt_pg.setError("Enter your Location");
                    aboutt_pg.setFocusable(true);
                } else if (a_rent.isEmpty()) {
                    rent.setError("Enter your Rent amount");
                    rent.setFocusable(true);

                } else if (a_security.isEmpty()) {
                    security.setError("Enter security fees");
                    security.setFocusable(true);
                } else if (accomodation.equalsIgnoreCase("Accomodation Type")) {
                    pg_accomodation.setFocusable(true);
                } else if (meal.equalsIgnoreCase("Meal Status")) {
                    pg_meal.setFocusable(true);

                } else if (water.equalsIgnoreCase("Water Availability")) {
                    pg_water.setFocusable(true);

                } else if (time.equalsIgnoreCase("Time Restriction")) {
                    pg_time.setFocusable(true);

                } else if (rooms.equalsIgnoreCase("Water Availability")) {
                    pg_rooms.setFocusable(true);

                } else if (a_for.equalsIgnoreCase("Select Availability")) {
                    pg_for.setFocusable(true);

                } else if (furnished.equalsIgnoreCase("Furnished Status")) {
                    pg_furnished.setFocusable(true);

                } else {
                    pg_hash_map.put("address", a_address);
                    pg_hash_map.put("location", a_location);
                    pg_hash_map.put("rent", a_rent);
                    pg_hash_map.put("security_fee", a_security);
                    pg_hash_map.put("accomodation", accomodation);
                    pg_hash_map.put("meat", meal);
                    pg_hash_map.put("water", water);
                    pg_hash_map.put("room", rooms);
                    pg_hash_map.put("furnished", furnished);
                    pg_hash_map.put("time", time);
                    pg_hash_map.put("pg_for", a_for);
                    pg_hash_map.put("mobile", mobe_number);
                    pg_hash_map.put("about", desc);


                    sendImagepg();
                }

            }
        });


    }

    private void sendImagepg() {

        Random random=new Random();
        int i=random.nextInt(999999-111111)+111111;
        final StorageReference firebaseStorage= FirebaseStorage.getInstance().getReference().child("imagep").child(""+i);
        UploadTask storageReference=firebaseStorage.putFile(urip);

        Task<Uri> urlTask = storageReference.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return firebaseStorage.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    pg_hash_map.put("image",""+downloadUri);
                    addValue();
                } else {

                    Toast.makeText(getActivity().getApplicationContext(), "Faild", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void addValue() {
        db_pg.push().setValue(pg_hash_map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Successfully Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "not Added", Toast.LENGTH_SHORT).show();

                }
            }

        });
    }

    public static void imagePG(Uri uri)
    {
        urip=uri;
        image2.setVisibility(View.VISIBLE);
        image2.setImageURI(uri);

    }
}
