package es.fsgpino.myrestaurant.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import es.fsgpino.myrestaurant.R;

public class SettingsActivity extends AppCompatActivity {

    public static final String NUMBER_OF_TABLES = "NUMBER_OF_TABLES";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setTitle(getString(R.string.settings));

        final NumberPicker numberSelectorPicker = (NumberPicker) findViewById(R.id.numberPicker);
        final Button cancelButton = (Button) findViewById(R.id.button_cancel);
        final Button okButton = (Button) findViewById(R.id.button_ok);

        numberSelectorPicker.setMaxValue(12);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent finish = new Intent();
                getSharedPreferences("Preferences",MODE_PRIVATE).edit().putInt(NUMBER_OF_TABLES,numberSelectorPicker.getValue()).apply();
                setResult(RESULT_OK,finish);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

    }
}
