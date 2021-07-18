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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Random;

import au.edu.jcu.flatOnRent.onrent.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ElectronicsUpload extends Fragment {


    static DatabaseReference db_elect;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    TextInputEditText company, rent, security, about, model, location, address;
    Spinner category;
    static ImageView image_add, image_i;
    static HashMap<String,String> ehashMap=new HashMap();
    static Uri uri2;
    Button upload_elect;
    String currentUserr;

    public ElectronicsUpload() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_electronics_upload, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth=FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser();
        db_elect= FirebaseDatabase.getInstance().getReference().child("Upload_elect");
        currentUserr=firebaseUser.getPhoneNumber().toString();

        company = view.findViewById(R.id.company_elect);
        rent = view.findViewById(R.id.rent_elect);
        security = view.findViewById(R.id.security_elect);
        about = view.findViewById(R.id.about_elect);
        model = view.findViewById(R.id.model_elect);
        location = view.findViewById(R.id.location_elect);
        address = view.findViewById(R.id.address_elect);
        category = view.findViewById(R.id.category_elect);
        image_i=view.findViewById(R.id.im);
        image_add=view.findViewById(R.id.addd);


        upload_elect = view.findViewById(R.id.upload_elect);

        upload_elect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ecompany = company.getText().toString();
                String erent = rent.getText().toString();
                String esecurity = security.getText().toString();
                String eabout = about.getText().toString();
                String emodel = model.getText().toString();
                String elocation = location.getText().toString();
                String eaddress = address.getText().toString();
                String ecategory = category.getSelectedItem().toString();


                if (ecompany.isEmpty()) {
                    company.setError("enter company");
                    company.setFocusable(true);
                } else if (erent.isEmpty()) {
                    rent.setError("enter rent");
                    rent.setFocusable(true);
                } else if (esecurity.isEmpty()) {
                    security.setError("enter security");
                    security.setFocusable(true);
                } else if (eabout.isEmpty()) {
                    about.setError("enter about");
                    about.setFocusable(true);
                } else if (emodel.isEmpty()) {
                    model.setError("enter model");
                    model.setFocusable(true);
                } else if (elocation.isEmpty()) {
                    location.setError("enter location");
                    location.setFocusable(true);
                } else if (eaddress.isEmpty()) {
                    address.setError("enter address");
                    address.setFocusable(true);
                } else if (ecategory.equals("Select the category")) {

                    category.setFocusable(true);
                    Toast.makeText(getActivity().getApplicationContext(), "Select category", Toast.LENGTH_SHORT).show();
                }
                else
                    {
                        ehashMap.put("company",ecompany);
                        ehashMap.put("category",ecategory);
                        ehashMap.put("address",eaddress);
                        ehashMap.put("location",elocation);
                        ehashMap.put("model",emodel);
                        ehashMap.put("rent",erent);
                        ehashMap.put("security",esecurity);
                        ehashMap.put("about",eabout);
                        ehashMap.put("number",currentUserr);
                        sendImage();
                    }
            }
        });
    }

    private void sendImage() {
        Random random=new Random();
        int i=random.nextInt(999999-111111)+111111;
        final StorageReference firebaseStorage= FirebaseStorage.getInstance().getReference().child("imagee").child(""+i);
        UploadTask storageReference=firebaseStorage.putFile(uri2);
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
                    ehashMap.put("imagee",""+downloadUri);
                    addValue();
                } else {

                    Toast.makeText(getActivity().getApplicationContext(), "Faild", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void addValue() {
        db_elect.push().setValue(ehashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    public static void imageViewww(Uri uri){

uri2=uri;
image_i.setVisibility(View.VISIBLE);
image_i.setImageURI(uri);
    }

}
