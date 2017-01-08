package es.fsgpino.myrestaurant.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import es.fsgpino.myrestaurant.R;
import es.fsgpino.myrestaurant.adapters.PlatesListAdapter;
import es.fsgpino.myrestaurant.models.FoodMenu;

public class AddPlateListFragment extends Fragment {

    private static final String ARGS_PLATES = "ARGS_PLATES";

    private PlatesListFragment.OnPlateSelectedListener onPlateSelectedListener;

    public static AddPlateListFragment newInstance(FoodMenu p) {
        AddPlateListFragment fragment = new AddPlateListFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARGS_PLATES,p);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_add_plate_list, container, false);
        ListView listView = (ListView) root.findViewById(android.R.id.list);

        FoodMenu plates = (FoodMenu) getArguments().getSerializable(ARGS_PLATES);

        PlatesListAdapter adapter = new PlatesListAdapter(getActivity(),R.layout.view_plate_list_item,plates.getPlates());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (AddPlateListFragment.this.onPlateSelectedListener != null){
                    FoodMenu p = (FoodMenu) AddPlateListFragment.this.getArguments().getSerializable(ARGS_PLATES);
                    AddPlateListFragment.this.onPlateSelectedListener.onPlateSelected(p.getPlates().get(position),position);
                }
            }
        });

        return root;
    }

    public void setOnPlateSelectedListener(PlatesListFragment.OnPlateSelectedListener onPlateSelectedListener){
        this.onPlateSelectedListener = onPlateSelectedListener;
    }

}
