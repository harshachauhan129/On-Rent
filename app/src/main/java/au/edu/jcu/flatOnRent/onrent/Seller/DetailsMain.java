package au.edu.jcu.flatOnRent.onrent.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import au.edu.jcu.flatOnRent.onrent.R;
import au.edu.jcu.flatOnRent.onrent.list.ModelClassBooks;

public class DetailsMain extends AppCompatActivity {


    String key;
    String key1;

    ImageView pgimage;
    //books elements:
    LinearLayout detail_books;
    TextView b_name, b_rent, b_security, b_author, b_about, b_address, b_category;
    //pg elements:
    LinearLayout details_pg;
    TextView security_pg, room_pg, type_pg, rent_pg, address_pg, avail_for_pg, furnished_pg, water_pg, timing_pg, meal_pg, pg_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_main);
//pg.......................................................................................................................................
        details_pg = findViewById(R.id.detail_pg);
        pgimage=findViewById(R.id.img_pg);

        rent_pg = findViewById(R.id.rent_pg);
        security_pg = findViewById(R.id.security_pg);
        address_pg = findViewById(R.id.address_pg);
        avail_for_pg = findViewById(R.id.available_pg);
        furnished_pg = findViewById(R.id.furnished_pg);
        type_pg = findViewById(R.id.accomo_type);
        water_pg = findViewById(R.id.water_pg);
        timing_pg = findViewById(R.id.timing_pg);
        room_pg = findViewById(R.id.room_pg);
        meal_pg = findViewById(R.id.meal_pg);
        pg_description = findViewById(R.id.about_pg);

//books ....................................................................................................................................
        detail_books = findViewById(R.id.book_details);
        b_name = findViewById(R.id.name_book);
        b_rent = findViewById(R.id.rent_book);
        b_security = findViewById(R.id.security_money_books);
        b_address = findViewById(R.id.address_book);
        b_author = findViewById(R.id.book_author);
        b_category = findViewById(R.id.book_type);
        b_about = findViewById(R.id.about_book);


        Intent intent = getIntent();
        key1 = intent.getStringExtra("key1");
        key = intent.getStringExtra("key");

        if (key1.equals("book")) {

            detail_books.setVisibility(View.VISIBLE);

        } else if (key1.equals("pg")) {
            details_pg.setVisibility(View.VISIBLE);

        } else if (key1.equals("electronics")) {

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (key1.equals("book")) {
            DatabaseReference db_detail_book;
            db_detail_book = FirebaseDatabase.getInstance().getReference().child("Upload_book").child(key);
            getDataBook(db_detail_book);


        } else if (key1.equals("pg")) {
            DatabaseReference db_detail_pg;
            db_detail_pg = FirebaseDatabase.getInstance().getReference().child("Upload_pg").child(key);
            getDataPG(db_detail_pg);
        } else if (key1.equals("electronics")) {
            DatabaseReference db_detail_electronics;
            /*db_detail_electronics=FirebaseDatabase.getInstance().getReference().child("Upload_electronics").child(key);
            getData(db_detail_electronics);




             */
        }

    }

    private void getDataPG(final DatabaseReference databaseReference) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String pg_type, pg_rent, pg_security,imgpg, pg_address, pg_avail, pg_furnished, pg_water, pg_timing, pg_room, pg_meal, pg_descp;

                pg_rent = dataSnapshot.child("rent").getValue().toString();
                pg_security = dataSnapshot.child("security_fee").getValue().toString();
                pg_address = dataSnapshot.child("address").getValue().toString();
                pg_avail = dataSnapshot.child("pg_for").getValue().toString();
                pg_furnished = dataSnapshot.child("furnished").getValue().toString();
                pg_water = dataSnapshot.child("water").getValue().toString();
                pg_room = dataSnapshot.child("room").getValue().toString();
                pg_meal = dataSnapshot.child("meat").getValue().toString();
                pg_descp = dataSnapshot.child("about").getValue().toString();
                pg_timing=dataSnapshot.child("time").getValue().toString();
                pg_type = dataSnapshot.child("accomodation").getValue().toString();


                //TextView , , , , , , , , , meal_pg, pg_description;

                security_pg.setText(pg_security);
                room_pg.setText(pg_room);
                type_pg.setText(pg_type);
                rent_pg.setText(pg_rent);
                address_pg.setText(pg_address);
                avail_for_pg.setText(pg_avail);
                furnished_pg.setText(pg_furnished);
                water_pg.setText(pg_water);
                timing_pg.setText(pg_timing);
                meal_pg.setText(pg_meal);
                pg_description.setText(pg_descp);
//                imgpg;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void getDataBook(DatabaseReference databaseReference) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String rent_book, type_book, author_book, about_book, title_book, address_book, book_security;

                rent_book = dataSnapshot.child("book_rent").getValue().toString();
                type_book = dataSnapshot.child("book_category").getValue().toString();
                author_book = dataSnapshot.child("book_author").getValue().toString();
                about_book = dataSnapshot.child("book_about").getValue().toString();
                address_book = dataSnapshot.child("book_address").getValue().toString();
                title_book = dataSnapshot.child("book_name").getValue().toString();
                book_security = dataSnapshot.child("book_security").getValue().toString();

                b_rent.setText(rent_book);
                b_category.setText(type_book);
                b_author.setText(author_book);
                b_about.setText(about_book);
                b_address.setText(address_book);
                b_name.setText(title_book);
                b_security.setText(book_security);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
