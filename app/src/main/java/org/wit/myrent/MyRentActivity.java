package org.wit.myrent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.Menu;
import android.text.Editable;
import android.widget.EditText;

public class MyRentActivity extends AppCompatActivity implements TextWatcher {

    private EditText  geolocation;
    private Residence residence;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myrent);

        geolocation = (EditText) findViewById(R.id.geolocation);
        residence = new Residence();
        // Register a TextWatcher in the EditText geolocation object
        geolocation.addTextChangedListener(this);
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
