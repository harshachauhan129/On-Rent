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

import au.edu.jcu.flatOnRent.onrent.Seller.ImageUploadData;
import au.edu.jcu.flatOnRent.onrent.R;

/**
 * A simple {@link Fragment} subclass.
 */
public  class BooksUpload extends Fragment {
    FirebaseAuth mauth;
    static DatabaseReference book_database;
    TextInputEditText book_name, book_author, address, book_rent, book_security, book_location, about_book;
    Spinner category;
    static HashMap<String, String> hash_pg_upload = new HashMap<>();
    Button submit_book;
    static ImageView image1;

static Uri uri1;
    String mobe_number;

    public BooksUpload() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_books_upload, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        book_location = view.findViewById(R.id.location);
        book_name = view.findViewById(R.id.book_name);
        book_author = view.findViewById(R.id.author);
        book_security = view.findViewById(R.id.security_money);
        book_rent = view.findViewById(R.id.rent_book);
        address = view.findViewById(R.id.address);
        about_book = view.findViewById(R.id.about);
        image1=view.findViewById(R.id.image_one);
        category = view.findViewById(R.id.category);


        submit_book = view.findViewById(R.id.book_submit);


        mauth = FirebaseAuth.getInstance();
        FirebaseUser current_user = mauth.getCurrentUser();
        mobe_number = current_user.getPhoneNumber().toString();
        book_database = FirebaseDatabase.getInstance().getReference().child("Upload_book");


        submit_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String location = book_location.getText().toString();
                String name = book_name.getText().toString();
                String author = book_author.getText().toString();
                String security = book_security.getText().toString();
                String rent = book_rent.getText().toString();
                String address_book = address.getText().toString();
                String about = about_book.getText().toString();

                String book_category = category.getSelectedItem().toString();
                if (location.isEmpty()) {
                    book_location.setError("Can't be empty");
                    book_location.setFocusable(true);
                } else if (name.isEmpty()) {
                    book_name.setError("Can't be empty");
                    book_name.setFocusable(true);
                } else if (author.isEmpty()) {
                    book_author.setError("Can't be empty");
                    book_author.setFocusable(true);
                } else if (security.isEmpty()) {
                    book_security.setError("Can't be empty");
                    book_security.setFocusable(true);
                } else if (rent.isEmpty()) {
                    book_rent.setError("Can't be empty");
                    book_rent.setFocusable(true);
                } else if (address_book.isEmpty()) {
                    address.setError("Can't be empty");
                    address.setFocusable(true);
                } else if (about.isEmpty()) {
                    about_book.setError("Can't be empty");
                    about_book.setFocusable(true);
                } else if (book_category.equalsIgnoreCase("Select book category")) {
                    category.setFocusable(true);
                }
                else if(uri1==null)
                {
                    Toast.makeText(getActivity().getApplicationContext(), "Please Select atleast one image", Toast.LENGTH_SHORT).show();

                }
                else {
                    hash_pg_upload.put("location", location);
                    hash_pg_upload.put("book_name", name);
                    hash_pg_upload.put("book_author", author);
                    hash_pg_upload.put("book_security", security);
                    hash_pg_upload.put("book_rent", rent);
                    hash_pg_upload.put("book_address", address_book);
                    hash_pg_upload.put("book_about", about);
                    hash_pg_upload.put("book_category", book_category);
                    hash_pg_upload.put("mobilenumber", mobe_number);
                    sendimage();
                }
            }
        });
    }



    private  void sendimage(){
        Random  random=new Random();
        int i=random.nextInt(999999-111111)+111111;
        final StorageReference firebaseStorage=FirebaseStorage.getInstance().getReference().child("image").child(""+i);
        UploadTask storageReference=firebaseStorage.putFile(uri1);
      /*  storageReference.putFile(uri1).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                if(task.isSuccessful()){
                    String s=task.getResult().getMetadata().getReference().getDownloadUrl().toString();
                    hash_pg_upload.put("image1",s);
                    addValue();
                }
            }
        });
*/

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
                    hash_pg_upload.put("image",""+downloadUri);
                    addValue();
                } else {

                    Toast.makeText(getActivity().getApplicationContext(), "Faild", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void addValue() {

        //piush is use to get the unique key  and to avoid the replacements
        book_database.push().setValue(hash_pg_upload).addOnCompleteListener(new OnCompleteListener<Void>() {
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

public static void image(Uri uri){

        uri1=uri;
    image1.setVisibility(View.VISIBLE);
//    Picasso.get().load(uri).into(image_two);
   // Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageView);
image1.setImageURI(uri);



}

}
