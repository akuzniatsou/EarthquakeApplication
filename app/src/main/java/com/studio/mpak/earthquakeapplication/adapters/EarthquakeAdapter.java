package com.studio.mpak.earthquakeapplication.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.studio.mpak.earthquakeapplication.R;
import com.studio.mpak.earthquakeapplication.domain.Earthquake;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static Pattern LOCATION_PATTERN = Pattern.compile("^(.*of)\\s(.*)$");
    private String location;
    private String range;

    public EarthquakeAdapter(Context context, List<Earthquake> values) {
        super(context, 0, values);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View earthquakeView, @NonNull ViewGroup parent) {

        Earthquake earthquake = getItem(position);

        if (earthquakeView == null) {
            earthquakeView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_item, parent, false);
        }

        TextView magnitudeView = earthquakeView.findViewById(R.id.magnitude);
        magnitudeView.setText(formatMagnitude(earthquake.getMagnitude()));

        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();
        int magnitudeColor = getMagnitudeColor(earthquake.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);

        preparePlace(earthquake.getLocation());
        TextView rangeView = earthquakeView.findViewById(R.id.range);
        if (range == null) {
            rangeView.setVisibility(View.GONE);
        } else {
            rangeView.setText(range);
        }

        TextView locationView = earthquakeView.findViewById(R.id.location);
        locationView.setText(location);

        TextView dateView = earthquakeView.findViewById(R.id.date);
        dateView.setText(formatDate(earthquake.getDate()));

        TextView timeView = earthquakeView.findViewById(R.id.time);
        timeView.setText(formatTime(earthquake.getDate()));

        return earthquakeView;
    }



    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy", Locale.ROOT);
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.ROOT);
        return timeFormat.format(dateObject);
    }


    private void preparePlace(String location) {
        if (location.contains("of")) {
            Matcher matcher = LOCATION_PATTERN.matcher(location);
            while (matcher.find()) {
                this.range = matcher.group(1);
                this.location = matcher.group(2);
            }
        } else {
            this.range = null;
            this.location = location;
        }
    }

    private String formatMagnitude(double mag) {
        DecimalFormat formatter = new DecimalFormat("0.0");
        return formatter.format(mag);
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
