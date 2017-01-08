package es.fsgpino.myrestaurant.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

import es.fsgpino.myrestaurant.R;
import es.fsgpino.myrestaurant.models.Plate;

public class PlatesListAdapter extends ArrayAdapter<Plate> {

    private Context context;
    private int resource;

    public PlatesListAdapter(Context context, int resource, LinkedList<Plate> plates) {
        super(context, resource, plates);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(resource,null);
        Plate plate = getItem(position);

        ImageView plateImage = (ImageView) view.findViewById(R.id.plate_list_image);
        Context context = plateImage.getContext();

        plateImage.setImageResource(context.getResources().getIdentifier(plate.getImage(), "drawable", context.getPackageName()));

        TextView nameTextView = (TextView) view.findViewById(R.id.plate_list_name);
        nameTextView.setText(plate.getName());

        TextView priceTextView = (TextView) view.findViewById(R.id.plate_list_price);
        priceTextView.setText(String.format(context.getString(R.string.price_format),plate.getPrice()));

        return view;

    }

}
