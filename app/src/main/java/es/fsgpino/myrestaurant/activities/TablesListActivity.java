package es.fsgpino.myrestaurant.activities;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ViewSwitcher;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import es.fsgpino.myrestaurant.R;
import es.fsgpino.myrestaurant.fragments.TablesListFragment;
import es.fsgpino.myrestaurant.models.FoodMenu;
import es.fsgpino.myrestaurant.models.Plate;
import es.fsgpino.myrestaurant.models.Table;

public class TablesListActivity extends AppCompatActivity implements TablesListFragment.OnTableListSelectedListener {

    public static final String TABLE_EXTRA = "TABLE_EXTRA";
    public static final String PLATES_EXTRA = "PLATES_EXTRA";

    public static final int SETTINGS_RESULT_CODE = 1;
    private static final int LOADING_VIEW_INDEX = 0;
    private static FoodMenu plates;
    private ViewSwitcher viewSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables_list);

        viewSwitcher = (ViewSwitcher) findViewById(R.id.view_switcher);

        downloadPlates();
        FragmentManager fm = getFragmentManager();

        if(fm.findFragmentById(R.id.tables_list_fragment) == null){
            TablesListFragment tableListFragment = new TablesListFragment();
            tableListFragment.setOnTableListSelectedListener(this);
            fm.beginTransaction()
                    .add(R.id.tables_list_fragment,tableListFragment)
                    .commit();
        }

    }

    private void downloadPlates() {
        AsyncTask<Void,Void,FoodMenu> plateDownloader = new AsyncTask<Void, Void, FoodMenu>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                viewSwitcher.setDisplayedChild(LOADING_VIEW_INDEX);
            }

            @Override
            protected FoodMenu doInBackground(Void... voids) {

                try {

                    int downloadedBytes;
                    InputStream input;
                    byte data[] = new byte[1024];
                    URL url = new URL("http://www.mocky.io/v2/5858414c120000c418c8af12");

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.connect();

                    input = con.getInputStream();

                    StringBuilder sb = new StringBuilder();
                    while ((downloadedBytes = input.read(data)) != -1 && !isCancelled()) {
                        sb.append(new String(data, 0, downloadedBytes));
                    }

                    JSONObject jsonRoot = new JSONObject(sb.toString());
                    JSONArray platesArray = jsonRoot.getJSONArray("menu");

                    plates = new FoodMenu();

                    for (int i = 0; i < platesArray.length(); i++) {

                        JSONObject plateObject = platesArray.getJSONObject(i);

                        String name = plateObject.getString("name");
                        String image = plateObject.getString("image");
                        JSONArray ingredientsArray = plateObject.getJSONArray("ingredients");

                        String[] ingredients = new String[ingredientsArray.length()];
                        for (int j = 0; j < ingredientsArray.length(); j++) {
                            ingredients[j] = ingredientsArray.getString(j);
                        }

                        float price = (float) plateObject.getDouble("price");

                        plates.addPlate(new Plate(name,image, ingredients, price));
                    }

                    return plates;

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onProgressUpdate (Void...voids){
                super.onProgressUpdate(voids);
            }

            @Override
            protected void onPostExecute (FoodMenu plates) {
                super.onPostExecute(plates);

                if (plates != null) {
                    viewSwitcher.setDisplayedChild(LOADING_VIEW_INDEX + 1);
                    TablesListActivity.plates = plates;
                }else {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(TablesListActivity.this);
                    alertDialog.setTitle(R.string.error);
                    alertDialog.setMessage(R.string.plates_download_error);
                    alertDialog.setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            downloadPlates();
                        }
                    });
                    alertDialog.show();

                }
            }
        };

        plateDownloader.execute();
    }

    @Override
    public void onTableListSelected(Table table) {
        Intent intent = new Intent(getApplicationContext(),PlatesListActivity.class);
        intent.putExtra(TABLE_EXTRA,table);
        intent.putExtra(PLATES_EXTRA,plates);
        startActivity(intent);
    }
}
