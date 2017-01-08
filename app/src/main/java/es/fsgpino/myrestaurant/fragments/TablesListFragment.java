package es.fsgpino.myrestaurant.fragments;

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
import android.widget.GridView;

import java.util.LinkedList;

import es.fsgpino.myrestaurant.R;
import es.fsgpino.myrestaurant.activities.SettingsActivity;
import es.fsgpino.myrestaurant.adapters.TablesListAdapter;
import es.fsgpino.myrestaurant.models.Interactor;
import es.fsgpino.myrestaurant.models.Plate;
import es.fsgpino.myrestaurant.models.Table;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static es.fsgpino.myrestaurant.activities.TablesListActivity.SETTINGS_RESULT_CODE;

public class TablesListFragment extends Fragment {

    private OnTableListSelectedListener onTableListSelectedListener;
    private TablesListAdapter adapter;
    private GridView gridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_tables_list,container,false);

        gridView = (GridView) root.findViewById(R.id.tables_list_fragment);

        if (Interactor.getTables().size() == 0){
            Interactor.addTable(new Table(1,new LinkedList<Plate>()));
        }

        adapter = new TablesListAdapter(getActivity(),R.layout.view_table_list_item,Interactor.getTables());

        gridView.setAdapter(adapter);
        onTableListSelectedListener = (OnTableListSelectedListener) getActivity();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (onTableListSelectedListener != null){
                    onTableListSelectedListener.onTableListSelected(Interactor.getTables().get(position));
                }
            }
        });

        return root;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SETTINGS_RESULT_CODE) {
            int number = getActivity().getSharedPreferences("Preferences",MODE_PRIVATE).getInt(SettingsActivity.NUMBER_OF_TABLES,1);
            updateTable(number);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.menu_settings,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.settings) {
            Intent settings = new Intent(getActivity().getApplicationContext(),SettingsActivity.class);
            startActivityForResult(settings,SETTINGS_RESULT_CODE);
            return true;
        }

        return false;
    }

    private void updateTable(int number) {
        Interactor.removeAllTables();
        for (int i = 1; i<= number;i++){
            Interactor.addTable(new Table(i,new LinkedList<Plate>()));
        }
        adapter = new TablesListAdapter(getActivity(),R.layout.view_table_list_item,Interactor.getTables());
        gridView.setAdapter(adapter);
    }

    public void setOnTableListSelectedListener(OnTableListSelectedListener onTableListSelectedListener){
        this.onTableListSelectedListener = onTableListSelectedListener;
    }

    public interface OnTableListSelectedListener {
        void onTableListSelected(Table table);
    }

}
