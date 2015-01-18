package hogent.weatherapp;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Christof on 18/01/2015.
 */
public class HandleJSON implements ResponseHandler<Weather>{

    private static final String TAG = "HandleJson";
    @Override
    public Weather handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
        Weather weather = null;
        String JSONResp = new BasicResponseHandler().handleResponse(httpResponse);
        try {
            JSONObject responseObject = (JSONObject)new JSONTokener(JSONResp).nextValue();
            JSONArray weatherJsonArray = responseObject.getJSONArray("weather");
            JSONObject weatherJson = (JSONObject)weatherJsonArray.get(0);
            String icon = weatherJson.getString("icon");
            JSONObject mainJson = responseObject.getJSONObject("main");
            long minTemp = mainJson.getLong("temp_min");
            long maxTemp = mainJson.getLong("temp_max");
            String location = responseObject.getString("name");
            weather = new Weather(icon,location,minTemp,maxTemp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weather;
    }
}
