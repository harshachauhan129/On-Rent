package au.edu.jcu.flatOnRent.onrent.Fragmentation;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import au.edu.jcu.flatOnRent.onrent.R;

public class DetailBooks extends Fragment {


    public DetailBooks() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_books, container, false);
    }

}
