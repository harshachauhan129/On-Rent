package au.edu.jcu.flatOnRent.onrent.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import au.edu.jcu.flatOnRent.onrent.R;

public class ListAdapterClass extends ArrayAdapter {
    List list = new ArrayList();

    public ListAdapterClass(@NonNull Context context, int resource) {
        super(context, resource);
    }

    //ctl+o
    @Override
    public int getCount() {

        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    public void add(ModelClassBooks modelClassBooks) {
        // we are calling add method only. we are adding hem in list and we are returning the size into the size method
        super.add(modelClassBooks);
        list.add(modelClassBooks);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row;
        row = convertView;
        Handler h;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = layoutInflater.inflate(R.layout.list_view_style, parent, false);
            h = new Handler();
            h.location = (TextView) row.findViewById(R.id.location_list);
            h.rent = (TextView) row.findViewById(R.id.price_list);
            h.imageView=(ImageView)row.findViewById(R.id.image);
            row.setTag(h);


        } else {
            h=(Handler) row.getTag();

        }
        ModelClassBooks m=(ModelClassBooks)this.getItem(position);
        h.rent.setText(m.getBook_rent());
        h.location.setText(m.getLocation());
        Picasso.get().load(m.getImage()).into(h.imageView);
        return row;
    }

    static class Handler {
        TextView rent, location;
        ImageView imageView;


    }
}
