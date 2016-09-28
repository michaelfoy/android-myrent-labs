package org.wit.myrent.activities;

import org.wit.myrent.R;
import org.wit.myrent.app.MyRentApp;
import org.wit.myrent.models.Portfolio;
import org.wit.myrent.models.Residence;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class ResidenceListActivity extends Activity
{
    private ListView listView;
    private Portfolio portfolio;
    private ResidenceAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.activity_residencelist);

        listView = (ListView) findViewById(R.id.residenceList);

        MyRentApp app = (MyRentApp) getApplication();
        portfolio = app.portfolio;

        adapter = new ResidenceAdapter(this, portfolio.residences);
        listView.setAdapter(adapter);
    }
}

class ResidenceAdapter extends ArrayAdapter<Residence>
{
    private Context context;

    public ResidenceAdapter(Context context, ArrayList<Residence> residences)
    {
        super(context, 0, residences);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.list_item_residence, null);
        }
        Residence res = getItem(position);

        TextView geolocation = (TextView) convertView.findViewById(R.id.residenceListItemGeolocation);
        geolocation.setText(res.geolocation);

        TextView dateTextView = (TextView) convertView.findViewById(R.id.residenceListItemDateTextView);
        dateTextView.setText(res.getDateString());

        CheckBox rentedCheckBox = (CheckBox) convertView.findViewById(R.id.residenceListItemIsRented);
        rentedCheckBox.setChecked(res.rented);

        return convertView;
    }
}