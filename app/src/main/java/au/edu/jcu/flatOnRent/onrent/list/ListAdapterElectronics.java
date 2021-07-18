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

public class ListAdapterElectronics extends ArrayAdapter
{



    List listE=new ArrayList();
    public ListAdapterElectronics(@NonNull Context context, int resource) {super(context, resource);}

    @Override
    public int getCount() {
        return listE.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) { return listE.get(position);}

    public void add(@Nullable ModelClassElectronics modelClassE) {
        super.add(modelClassE);
        listE.add(modelClassE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row;
        row = convertView;
        ListAdapterElectronics.HandlerE e_h;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = layoutInflater.inflate(R.layout.list_view_style, parent, false);
            e_h = new ListAdapterElectronics.HandlerE();
            e_h.locatione = (TextView) row.findViewById(R.id.location_list);
            e_h.rente = (TextView) row.findViewById(R.id.price_list);
            row.setTag(e_h);


        } else {
            e_h=(ListAdapterElectronics.HandlerE) row.getTag();

        }
        ModelClassPg m=(ModelClassPg)this.getItem(position);
        e_h.rente.setText(m.getRent());
       e_h.locatione.setText(m.getLocation());
        return row;
    }

    static class HandlerE
    {
        TextView rente, locatione;
    }

}
