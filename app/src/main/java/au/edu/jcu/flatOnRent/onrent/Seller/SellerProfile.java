package au.edu.jcu.flatOnRent.onrent.Seller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import com.squareup.picasso.Picasso;

import au.edu.jcu.flatOnRent.onrent.R;
import au.edu.jcu.flatOnRent.onrent.upload_data.BooksUpload;
import de.hdodenhof.circleimageview.CircleImageView;

public class SellerProfile extends AppCompatActivity {
    TextView name_u,email_u,contact;
    Button wishlist,uploads,order;
    FirebaseAuth mAuth;
    private  static final int GALLERY_PIC=1;
    FirebaseUser user;
    ProgressDialog progressDialog;
    FloatingActionButton camera;
    DatabaseReference db_uer;
    String mob_number;
    CircleImageView circleImageView;


    //Processing dialoug box 1. show 2 hide 3 dismiss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_profile);

        progressDialog=ProgressDialog.show(SellerProfile.this,"Progress","Please Wait",false,false);
        mAuth=FirebaseAuth.getInstance();

        camera=findViewById(R.id.for_camra);
        name_u=findViewById(R.id.user_name);
        email_u=findViewById(R.id.user_email);
        contact=findViewById(R.id.user_contact);
        circleImageView=findViewById(R.id.profile_image);
        user=mAuth.getCurrentUser();


        mob_number=user.getPhoneNumber().toString();


        db_uer= FirebaseDatabase.getInstance().getReference().child("Register").child(mob_number);


        wishlist=findViewById(R.id.wishlist);
        uploads=findViewById(R.id.my_uploads);
        order=findViewById(R.id.my_orders);

        db_uer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name,ph_number,email;

                name=dataSnapshot.child("name").getValue().toString();
                ph_number=dataSnapshot.child("number").getValue().toString();
                email=dataSnapshot.child("email").getValue().toString();

                Picasso.get().load(dataSnapshot.child("profilepic").getValue().toString()).into(circleImageView);

                name_u.setText(name);
                email_u.setText(email);
                contact.setText(ph_number);

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(SellerProfile.this, "error ", Toast.LENGTH_SHORT).show();
            }
        });



        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Intent i=new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i,"Select an image"),GALLERY_PIC);


            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK && requestCode==GALLERY_PIC){

            Uri uri;
            uri=data.getData();
            progressDialog.show();
            imgUpload(uri);
        }
    }

    private void imgUpload(Uri uri)
    {
        final StorageReference img;
        img= FirebaseStorage.getInstance().getReference().child("profilepic").child(mob_number);
        UploadTask uploadTask=img.putFile(uri);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return img.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();

                            db_uer.child("profilepic").setValue(""+downloadUri).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                     progressDialog.dismiss();
                                     Toast.makeText(SellerProfile.this, "ook", Toast.LENGTH_SHORT).show();


                                    }else {
                                        progressDialog.dismiss();
                                        Toast.makeText(SellerProfile.this, "oo no", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Faild", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
