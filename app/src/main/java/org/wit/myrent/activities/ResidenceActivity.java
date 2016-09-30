package org.wit.myrent.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.text.Editable;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import org.wit.myrent.R;
import org.wit.myrent.app.MyRentApp;
import org.wit.myrent.models.Portfolio;
import org.wit.myrent.models.Residence;

public class ResidenceActivity extends AppCompatActivity implements TextWatcher, CompoundButton.OnCheckedChangeListener {

    private EditText  geolocation;
    private Residence residence;
    private CheckBox rented;
    private Button dateButton;
    private Portfolio portfolio;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_residence);

        geolocation = (EditText) findViewById(R.id.geolocation);
        residence = new Residence();
        dateButton  = (Button)   findViewById(R.id.registrationDate);
        rented  = (CheckBox) findViewById(R.id.isRented);
        dateButton .setEnabled(false);
        rented.setOnCheckedChangeListener(this);

        // Register a TextWatcher in the EditText geolocation object
        geolocation.addTextChangedListener(this);

        MyRentApp app = (MyRentApp) getApplication();
        portfolio = app.portfolio;

        Long resId = (Long) getIntent().getExtras().getSerializable("RESIDENCE_ID");
        residence = portfolio.getResidence(resId);

        if (residence != null)
        {
            updateControls(residence);
        }
    }

    public void updateControls(Residence residence)
    {
        geolocation.setText(residence.geolocation);
        rented.setChecked(residence.rented);
        dateButton.setText(residence.getDateString());
    }

    /**
     * Creates the menu
     *
     * @param menu Menu object
     * @return True if menu successfully created
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_myrent, menu);
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton arg0, boolean isChecked)
    {
        Log.i(this.getClass().getSimpleName(), "rented Checked");
        residence.rented = isChecked;
    }

    @Override
    public void afterTextChanged(Editable editable)
    {
        residence.setGeolocation(editable.toString());
    }

    @Override
    public void beforeTextChanged(CharSequence c, int start, int count, int after)
    {
    }

    @Override
    public void onTextChanged(CharSequence c, int start, int count, int after)
    {
    }
}
