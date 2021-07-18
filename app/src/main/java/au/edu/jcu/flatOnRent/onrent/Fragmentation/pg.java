package au.edu.jcu.flatOnRent.onrent.Fragmentation;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

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
import au.edu.jcu.flatOnRent.onrent.list.ListAdapterPg;
import au.edu.jcu.flatOnRent.onrent.list.ModelClassBooks;
import au.edu.jcu.flatOnRent.onrent.list.ModelClassPg;

/**
 * A simple {@link Fragment} subclass.
 */
public class pg extends Fragment {

    DatabaseReference dbPG;
    ListView pg_list;
    List pg_listL=new ArrayList();


    public pg() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pg, container, false);
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbPG = FirebaseDatabase.getInstance().getReference().child("Upload_pg");
        pg_list = view.findViewById(R.id.pg_list);
        pg_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String pg_s= (String) pg_listL.get(position);
                Intent intent=new Intent(getActivity().getApplicationContext(), DetailsMain.class);
                intent.putExtra("key",pg_s);
                intent.putExtra("key1","pg");
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
pg_listL.clear();
        dbPG.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshotpg) {

                ListAdapterPg listAdapterPg = new ListAdapterPg(getActivity(), R.layout.list_view_style);

                for (DataSnapshot data : dataSnapshotpg.getChildren()) {
                    ModelClassPg modelClassPg = data.getValue(ModelClassPg.class);
                    listAdapterPg.add(modelClassPg);

                    pg_listL.add(data.getKey().toString());
                    listAdapterPg.add(modelClassPg);
                }

                pg_list.setAdapter(listAdapterPg);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
