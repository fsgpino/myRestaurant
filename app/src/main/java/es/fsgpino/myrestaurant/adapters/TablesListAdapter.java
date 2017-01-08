package es.fsgpino.myrestaurant.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.LinkedList;

import es.fsgpino.myrestaurant.R;
import es.fsgpino.myrestaurant.models.Table;

public class TablesListAdapter extends ArrayAdapter<Table> {

    private Context context;
    private int resource;

    public TablesListAdapter(Context context, int resource, LinkedList<Table> tables) {
        super(context, resource, tables);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(resource,null);
        Table table = getItem(position);

        TextView numberTextView = (TextView) view.findViewById(R.id.number_table);
        numberTextView.setText(String.format(context.getString(R.string.table_format), table.getIdentifier()));

        return view;

    }

}
