package com.example.firebase;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class View_Events extends AppCompatActivity {

    TextView txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__events);

        //txt=findViewById(R.id.txt1);
        new GetUrlContentTask(getApplicationContext()).execute("http://192.168.7.122/list_event.php");
    }

    private class GetUrlContentTask extends AsyncTask<String, Integer, String> {
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


            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();


            JSONObject jso = null;
            try {
                ListView listView = findViewById(R.id.list_view);

                List<String> items = new ArrayList<>();

                JSONArray jsonarray = new JSONArray(result);

                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    //txt.setText(txt.getText().toString()+jsonobject.getString("name"));
                    items.add(jsonobject.getString("name"));
                    Toast.makeText(context,jsonobject.getString("name"),Toast.LENGTH_LONG).show();
                    //e2.setText(jsonobject.getString("location"));
                }
                Log.e("items",items.toString());

                ArrayAdapter<String> adapter = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,items);

                listView.setAdapter(adapter);


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent appInfo = new Intent(View_Events.this, MainActivity.class);
                        startActivity(appInfo);
                    }
                });



            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
    }

}
