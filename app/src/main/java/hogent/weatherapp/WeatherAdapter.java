package hogent.weatherapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.squareup.picasso.Picasso;

/**
 * Created by Christof on 30/12/2014.
 */
public class WeatherAdapter extends ArrayAdapter<Weather> {

    Context context;
    int layoutResourceId;
    ArrayList<Weather> data = null;

    public WeatherAdapter(Context context, int layoutResourceId, ArrayList<Weather> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        WeatherHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new WeatherHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtLocation = (TextView)row.findViewById(R.id.txtLocation);
            holder.txtMinTemp = (TextView)row.findViewById(R.id.txtMinTemp);
            holder.txtMaxTemp = (TextView)row.findViewById(R.id.txtMaxTemp);

            row.setTag(holder);
        }
        else
        {
            holder = (WeatherHolder)row.getTag();
        }

        Weather weather = data.get(position);
        holder.txtLocation.setText(weather.getLocation());
        Picasso.with(context).load(weather.getIcon()).resize(100,100).centerCrop().into(holder.imgIcon);
        holder.txtMinTemp.setText(Double.toString(weather.getMinTemp()) + "°");
        holder.txtMaxTemp.setText(Double.toString(weather.getMaxTemp()) + "°");

        return row;
    }

    static class WeatherHolder
    {
        ImageView imgIcon;
        TextView txtLocation;
        TextView txtMinTemp;
        TextView txtMaxTemp;
    }
}