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

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import au.edu.jcu.flatOnRent.onrent.R;
import au.edu.jcu.flatOnRent.onrent.Seller.DetailsMain;

/**
 * A simple {@link Fragment} subclass.
 */
public class electronics extends Fragment {

    DatabaseReference db_elect;
    FirebaseUser elect_user;
    ListView elect_list;
    List elect_liste=new ArrayList();


    public electronics() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_electronics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db_elect = FirebaseDatabase.getInstance().getReference().child("Upload_pg");
        elect_list = view.findViewById(R.id.pg_list);

    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
