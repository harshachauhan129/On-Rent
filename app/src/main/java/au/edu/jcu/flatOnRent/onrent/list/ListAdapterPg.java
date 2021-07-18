package au.edu.jcu.flatOnRent.onrent.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import au.edu.jcu.flatOnRent.onrent.R;

public class ListAdapterPg extends ArrayAdapter {

    List listPg=new ArrayList();
    public ListAdapterPg(@NonNull Context context, int resource) {super(context, resource);}

    @Override
    public int getCount() {
        return listPg.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) { return listPg.get(position);}

    public void add(@Nullable ModelClassPg modelClassPg) {
        super.add(modelClassPg);
        listPg.add(modelClassPg);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row;
        row = convertView;
        HandlerPg pg_h;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = layoutInflater.inflate(R.layout.list_view_style, parent, false);
            pg_h = new ListAdapterPg.HandlerPg();
            pg_h.locationpg = (TextView) row.findViewById(R.id.location_list);
            pg_h.rentpg = (TextView) row.findViewById(R.id.price_list);
            row.setTag(pg_h);


        } else {
            pg_h=(ListAdapterPg.HandlerPg) row.getTag();

        }
        ModelClassPg m=(ModelClassPg)this.getItem(position);
        pg_h.rentpg.setText(m.getRent());
        pg_h.locationpg.setText(m.getLocation());
        return row;
    }

    static class HandlerPg
    {
        TextView rentpg, locationpg;
    }

}


