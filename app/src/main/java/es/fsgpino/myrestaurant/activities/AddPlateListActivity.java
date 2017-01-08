package es.fsgpino.myrestaurant.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import es.fsgpino.myrestaurant.R;
import es.fsgpino.myrestaurant.fragments.AddPlateListFragment;
import es.fsgpino.myrestaurant.fragments.PlatesListFragment;
import es.fsgpino.myrestaurant.models.FoodMenu;
import es.fsgpino.myrestaurant.models.Plate;

public class AddPlateListActivity extends AppCompatActivity implements PlatesListFragment.OnPlateSelectedListener {

    public static final String PLATE_EXTRA = "PLATE_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plate_list);

        FragmentManager fm = getFragmentManager();

        if(fm.findFragmentById(R.id.add_plate_list_fragment) == null){
            AddPlateListFragment addPlateListFragment = AddPlateListFragment.newInstance((FoodMenu) getIntent().getSerializableExtra(TablesListActivity.PLATES_EXTRA));
            addPlateListFragment.setOnPlateSelectedListener(this);
            fm.beginTransaction()
                    .add(R.id.add_plate_list_fragment,addPlateListFragment)
                    .commit();
        }

        setTitle(getString(R.string.select_plate));
    }

    @Override
    public void onPlateSelected(Plate plate, int position) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(PLATE_EXTRA,plate);
        setResult(RESULT_OK,returnIntent);
        finish();
    }

}
