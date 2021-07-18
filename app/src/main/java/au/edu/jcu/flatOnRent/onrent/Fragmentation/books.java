package au.edu.jcu.flatOnRent.onrent.Fragmentation;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import au.edu.jcu.flatOnRent.onrent.R;
import au.edu.jcu.flatOnRent.onrent.Seller.DetailsMain;
import au.edu.jcu.flatOnRent.onrent.list.ListAdapterClass;
import au.edu.jcu.flatOnRent.onrent.list.ModelClassBooks;

/**
 * A simple {@link Fragment} subclass.
 */
public class books extends Fragment {


    DatabaseReference db;

    ListView book_list;
    List list = new ArrayList();
    public books() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_books, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db= FirebaseDatabase.getInstance().getReference().child("Upload_book");
        book_list=view.findViewById(R.id.book_list);
        book_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String s= (String) list.get(position);
                Intent intent=new Intent(getActivity().getApplicationContext(), DetailsMain.class);
                intent.putExtra("key",s);
                intent.putExtra("key1","book");
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
list.clear();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ListAdapterClass listAdapterClass=new ListAdapterClass(getActivity(),R.layout.list_view_style);

               // for(DataSnapshot dataSnapshot2:dataSnapshot.getChildren()){

                for(DataSnapshot data:dataSnapshot.getChildren())
                {
                    ModelClassBooks modelClassBooks =data.getValue(ModelClassBooks.class);

                   list.add(data.getKey().toString());
                    listAdapterClass.add(modelClassBooks);

                }
             //   }

                book_list.setAdapter(listAdapterClass);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
