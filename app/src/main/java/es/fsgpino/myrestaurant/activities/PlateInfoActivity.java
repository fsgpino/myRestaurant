package es.fsgpino.myrestaurant.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

import es.fsgpino.myrestaurant.R;
import es.fsgpino.myrestaurant.models.Plate;

public class PlateInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plate_info);

        Plate plate = (Plate) getIntent().getSerializableExtra(PlatesListActivity.PLATE_EXTRA);

        ImageView plateImage = (ImageView) findViewById(R.id.plate_image);
        TextView nameTextView = (TextView) findViewById(R.id.plate_name);
        TextView ingrendientsTextView = (TextView) findViewById(R.id.plate_ingredients);
        TextView priceTextView = (TextView) findViewById(R.id.plate_price);

        Context context = plateImage.getContext();

        nameTextView.setText(plate.getName());
        plateImage.setImageResource(context.getResources().getIdentifier(plate.getImage(), "drawable", context.getPackageName()));
        ingrendientsTextView.setText(Arrays.toString(plate.getIngredients()));
        priceTextView.setText(String.format(getString(R.string.price_format),plate.getPrice()));

        setTitle(getString(R.string.detail_title_plate));

    }

}
