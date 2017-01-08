package es.fsgpino.myrestaurant.activities;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import es.fsgpino.myrestaurant.R;
import es.fsgpino.myrestaurant.fragments.PlatesListFragment;
import es.fsgpino.myrestaurant.models.FoodMenu;
import es.fsgpino.myrestaurant.models.Plate;
import es.fsgpino.myrestaurant.models.Table;

public class PlatesListActivity extends AppCompatActivity implements PlatesListFragment.OnPlateSelectedListener,PlatesListFragment.OnMenuItemSelectedListener {

    public static final String PLATE_EXTRA = "PLATE_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plates_list);

        Table table = (Table) getIntent().getSerializableExtra(TablesListActivity.TABLE_EXTRA);
        FoodMenu plates = (FoodMenu) getIntent().getSerializableExtra(TablesListActivity.PLATES_EXTRA);

        FragmentManager fm = getFragmentManager();

        if(fm.findFragmentById(R.id.plate_list_fragment) == null){
            PlatesListFragment platesListFragment = PlatesListFragment.newInstance(table,plates);
            platesListFragment.setOnPlateSelectedListener(this);
            platesListFragment.setOnMenuItemSelectedListener(this);
            fm.beginTransaction()
                    .add(R.id.plate_list_fragment,platesListFragment)
                    .commit();
        }

        setTitle(String.format(getString(R.string.table_format),table.getIdentifier()));

    }

    @Override
    public void onPlateSelected(Plate plate, int position) {
        Intent detail = new Intent(getApplicationContext(),PlateInfoActivity.class);
        detail.putExtra(PLATE_EXTRA,plate);
        startActivity(detail);
    }

    @Override
    public void onBillSelected(Table table) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(R.string.total_to_pay);
        alertDialog.setMessage(String.format(getString(R.string.dialog_to_info_pay),table.getBill()));
        alertDialog.setNeutralButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onCleanSelected(Table table) {
        table.clear();
        finish();
    }

}
