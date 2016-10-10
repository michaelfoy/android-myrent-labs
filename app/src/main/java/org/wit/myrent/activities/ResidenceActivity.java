package org.wit.myrent.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.text.Editable;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.DatePickerDialog;
import android.view.View;
import static org.wit.android.helpers.IntentHelper.navigateUp;

import org.wit.android.helpers.ContactHelper;
import static org.wit.android.helpers.IntentHelper.selectContact;
import static org.wit.android.helpers.ContactHelper.sendEmail;

import org.wit.myrent.R;
import org.wit.myrent.app.MyRentApp;
import org.wit.myrent.models.Portfolio;
import org.wit.myrent.models.Residence;

public class ResidenceActivity extends AppCompatActivity implements TextWatcher, CompoundButton.OnCheckedChangeListener, View.OnClickListener, DatePickerDialog.OnDateSetListener {

  private EditText geolocation;
  private Residence residence;
  private CheckBox rented;
  private Button dateButton;
  private Portfolio portfolio;
  private Button tenantButton;
  private String emailAddress = "";
  private Button reportButton;
  private static final int REQUEST_CONTACT = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_residence);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    geolocation = (EditText) findViewById(R.id.geolocation);
    residence = new Residence();
    dateButton = (Button) findViewById(R.id.registrationDate);
    rented = (CheckBox) findViewById(R.id.isRented);
    tenantButton = (Button) findViewById(R.id.tenant);
    reportButton = (Button) findViewById(R.id.residence_reportButton);

    rented.setOnCheckedChangeListener(this);
    dateButton.setOnClickListener(this);
    reportButton.setOnClickListener(this);

    // Register a TextWatcher in the EditText geolocation object
    geolocation.addTextChangedListener(this);
    tenantButton.setOnClickListener(this);

    MyRentApp app = (MyRentApp) getApplication();
    portfolio = app.portfolio;

    updateControls(residence);

    Long resId = (Long) getIntent().getExtras().getSerializable("RESIDENCE_ID");
    residence = portfolio.getResidence(resId);

    if (residence != null) {
      updateControls(residence);
    }
  }

  public void updateControls(Residence residence) {
    geolocation.setText(residence.geolocation);
    rented.setOnCheckedChangeListener(this);
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
  public boolean onOptionsItemSelected(MenuItem item)
  {
    switch (item.getItemId())
    {
      case android.R.id.home:  navigateUp(this);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
    Log.i(this.getClass().getSimpleName(), "rented Checked");
    residence.rented = isChecked;
  }

  @Override
  public void afterTextChanged(Editable editable) {
    residence.setGeolocation(editable.toString());
  }

  @Override
  public void beforeTextChanged(CharSequence c, int start, int count, int after) {
  }

  @Override
  public void onTextChanged(CharSequence c, int start, int count, int after) {
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.registrationDate:
        Calendar c = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(this, this, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dpd.show();
        break;
      case R.id.tenant : selectContact(this, REQUEST_CONTACT);
        break;
      case R.id.residence_reportButton :
        sendEmail(this, emailAddress,
            getString(R.string.residence_report_subject), residence.getResidenceReport(this));
        break;
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data)
  {
    switch (requestCode)
    {
      case REQUEST_CONTACT:
        String name = ContactHelper.getContact(this, data);
        emailAddress = ContactHelper.getEmail(this, data);
        tenantButton.setText(name + " : " + emailAddress);
        residence.tenant = name;
        break;
    }
  }

  @Override
  public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
    Date date = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
    residence.date = date.getTime();
    dateButton.setText(residence.getDateString());
  }

  @Override
  public void onPause() {
    super.onPause();
    portfolio.saveResidences();
  }
}
