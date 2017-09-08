package com.studio.mpak.earthquakeapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.studio.mpak.earthquakeapplication.adapters.EarthquakeAdapter;
import com.studio.mpak.earthquakeapplication.domain.Earthquake;
import com.studio.mpak.earthquakeapplication.utils.QueryUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = (ListView) findViewById(R.id.list);

        EarthquakeAdapter arrayView = new EarthquakeAdapter(this, QueryUtils.extractEarthquakes());
        listView.setAdapter(arrayView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Earthquake item = (Earthquake) listView.getItemAtPosition(position);
                Uri webPage = Uri.parse(item.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }
}

