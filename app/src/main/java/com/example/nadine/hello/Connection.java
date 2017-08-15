package com.example.nadine.hello;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.example.nadine.hello.R.id.textView;


public class Connection extends AppCompatActivity {

    private final String URL = "http://192.168.178.31:8080/maps";
    Context context;

    HttpClient client;

    JSONObject json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection);

        context = this;

        fetchMaps();
    }

    public void fetchMaps() {
        HttpParams httpParams = new BasicHttpParams();

        int timeoutConnection = 200000;

        HttpConnectionParams.setConnectionTimeout(httpParams, timeoutConnection);

        int timeoutSocket = 200000;

        HttpConnectionParams.setSoTimeout(httpParams, timeoutSocket);

        client = new DefaultHttpClient(httpParams);

        new ReadJSON().execute("");


    }


    public class ReadJSON extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                json = maps();

//                if (json == null) {
//                    Log.i("BLA", "No data returned");
//                } else {
//                    return "got json data";
//                }

                 return json.toString();


            } catch(Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String results) {
            if (results != null) {
                TextView t = (TextView) findViewById(textView);
                t.setText(results);
            } else {

            }
        }

    }

    public JSONObject maps() throws ClientProtocolException, IOException, JSONException {

        HttpGet get = new HttpGet(URL.toString());

        get.setHeader("Accept", "application/json");
        get.setHeader("content-type", "application/json; charset-UTF-8");

        HttpResponse r = client.execute(get);

        int status = r.getStatusLine().getStatusCode();

        Log.i("Bla", "Response back from the server: " + status);

        if (status == 200) {
            HttpEntity e = r.getEntity();

            String jsondata = EntityUtils.toString(e);

            JSONArray jarrray = new JSONArray(jsondata);

            JSONObject obj = jarrray.getJSONObject(0);

            return obj;
        } else {
            return null;
        }

    }


}
