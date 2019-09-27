package com.example.firebase;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

class GetUrlContentTask extends AsyncTask<String, Integer, String> {
    private Context context;

    public GetUrlContentTask(Context context)
    {
        this.context = context;
    }

    protected String doInBackground(String... urls) {
        URL url = null;
        String content = "";
        try {
            url = new URL(urls[0]);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.connect();

        BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String line;
        while ((line = rd.readLine()) != null) {
            content += line + "\n";
        }

        return content;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;

    }

    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(String result) {
        // this is executed on the main thread after the process is over
        // update your UI here
        //displayMessage(result);
        Log.e("RESULT",result);

        try {
            JSONObject jso = new JSONObject(result);
            String status = jso.getString("status");
            Toast.makeText(this.context,"Status: "+status,Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}