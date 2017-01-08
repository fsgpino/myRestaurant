package es.fsgpino.myrestaurant.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import es.fsgpino.myrestaurant.R;
import es.fsgpino.myrestaurant.activities.AddPlateListActivity;
import es.fsgpino.myrestaurant.activities.TablesListActivity;
import es.fsgpino.myrestaurant.adapters.PlatesListAdapter;
import es.fsgpino.myrestaurant.models.FoodMenu;
import es.fsgpino.myrestaurant.models.Interactor;
import es.fsgpino.myrestaurant.models.Plate;
import es.fsgpino.myrestaurant.models.Table;

public class PlatesListFragment extends Fragment {

    private static final String ARG_TABLE = "ARG_TABLE";
    private static final String ARG_PLATES = "ARG_PLATES";

    private OnPlateSelectedListener onPlateSelectedListener;
    private OnMenuItemSelectedListener onMenuItemSelectedListener;

    private ListView plateList;

    public static PlatesListFragment newInstance(Table table, FoodMenu platesAvaliable) {

        PlatesListFragment fragment = new PlatesListFragment();

        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_TABLE, table);
        arguments.putSerializable(ARG_PLATES, platesAvaliable);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_plates_list,container,false);
        plateList = (ListView) root.findViewById(android.R.id.list);

        Button addPlateButton = (Button) root.findViewById(R.id.add_plate_button);

        addPlateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent addPlate = new Intent(getActivity().getApplicationContext(), AddPlateListActivity.class);
            addPlate.putExtra(TablesListActivity.PLATES_EXTRA,PlatesListFragment.this.getArguments().getSerializable(ARG_PLATES));
            startActivityForResult(addPlate,2);
            }
        });

        Table table = (Table) getArguments().getSerializable(ARG_TABLE);

        PlatesListAdapter adapter = new PlatesListAdapter(getActivity(),R.layout.view_plate_list_item,table.getPlates());

        plateList.setAdapter(adapter);

        plateList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            if (PlatesListFragment.this.onPlateSelectedListener != null){
                Table table = (Table) PlatesListFragment.this.getArguments().getSerializable(ARG_TABLE);
                PlatesListFragment.this.onPlateSelectedListener.onPlateSelected(table.getPlates().get(position),position);
            }
            }
        });

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_table,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.bill) {
            onMenuItemSelectedListener.onBillSelected((Table) this.getArguments().getSerializable(ARG_TABLE));
            return true;
        }

        if (item.getItemId() == R.id.clean) {
            onMenuItemSelectedListener.onCleanSelected((Table) this.getArguments().getSerializable(ARG_TABLE));
            return true;
        }

        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == 2) {

            Plate plate = (Plate) data.getSerializableExtra(AddPlateListActivity.PLATE_EXTRA);
            Table table = (Table) this.getArguments().getSerializable(ARG_TABLE);

            Interactor.addPlate(plate,table.getIdentifier() - 1);
            getArguments().putSerializable(ARG_TABLE,Interactor.getTables().get(table.getIdentifier() - 1));

            table = (Table) this.getArguments().getSerializable(ARG_TABLE);

            PlatesListAdapter adapter = new PlatesListAdapter(getActivity(),R.layout.view_plate_list_item,table.getPlates());
            plateList.setAdapter(adapter);

        }
    }

    public void setOnMenuItemSelectedListener(OnMenuItemSelectedListener onMenuItemSelectedListener){
        this.onMenuItemSelectedListener = onMenuItemSelectedListener;
    }

    public void setOnPlateSelectedListener(OnPlateSelectedListener onPlateSelectedListener){
        this.onPlateSelectedListener = onPlateSelectedListener;
    }

    public interface OnMenuItemSelectedListener{
        void onBillSelected(Table table);
        void onCleanSelected(Table table);
    }

    public interface OnPlateSelectedListener {
        void onPlateSelected(Plate plate, int position);
    }

}
