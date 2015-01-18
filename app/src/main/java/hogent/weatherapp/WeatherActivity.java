package hogent.weatherapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.util.ArrayList;


public class WeatherActivity extends ActionBarActivity {

    private ListView listV;
    private String url = "http://api.openweathermap.org/data/2.5/weather?q=";
    private HandleJSON obj;
    private Button btnAdd;
    private EditText txtSearch;
    private ArrayList<Weather> weathers;
    private WeatherAdapter adapter;
    private static final String TAG = "WheaterActivity";
    private String location;
    private String minTemp;
    private String maxTemp;
    private String icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);


        listV = (ListView)findViewById(R.id.listView1);

        View header = (View)getLayoutInflater().inflate(R.layout.listview_header_row, null);
        listV.addHeaderView(header);

        txtSearch = (EditText)findViewById(R.id.txtSearch);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtSearch.getText().toString().isEmpty())
                {
                    Log.d(TAG, "txtSearch = " + txtSearch.getText().toString());
                    new HTTPGetTask(txtSearch.getText().toString().replaceAll("\\s",""),WeatherActivity.this).execute();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void endProcess(Weather weather) {
        if(weathers == null)
        {
            weathers = new ArrayList<Weather>();

        }

        weathers.add(weather);
        if(adapter == null)
        {
            adapter = new WeatherAdapter(this,R.layout.listview_item_row,weathers);
            listV.setAdapter(adapter);
        }
        else
        {
            adapter.notifyDataSetChanged();
        }
    }

    private class HTTPGetTask extends AsyncTask<Void,Void,Weather>
    {   private static final String TAG = "HTTPGetTask";
        private static final String URL = "http://api.openweathermap.org/data/2.5/weather?q=";
        private String searchUrl;
        AndroidHttpClient mClient = AndroidHttpClient.newInstance("");
        private Context context;
        public HTTPGetTask(String search,Context context)
        {
            searchUrl = URL+search+"&units=metric";
            this.context = context;
        }

        private ProgressDialog dialog;


        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(context);
            dialog.setTitle("Loading ... ");
            dialog.show();
        }

        @Override
        protected Weather doInBackground(Void... voids) {
            HttpGet request = new HttpGet(searchUrl);

            HandleJSON handleJson = new HandleJSON();
            try {
                return mClient.execute(request,handleJson);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            if(mClient != null)
            {
                mClient.close();
            }
            dialog.dismiss();
            if (weather != null)
            {
                Log.d(TAG,"weather: "+weather.getLocation());
                endProcess(weather);
            }
        }
    }
}
