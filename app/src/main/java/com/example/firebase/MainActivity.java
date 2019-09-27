package com.example.firebase;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText txt1,txt2,txt3;
    Button b1;
    int i=0;


    private FirebaseDatabase fref;
    private DatabaseReference dref;
    Members mbr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt1= (EditText)findViewById(R.id.t1);
        txt2= (EditText)findViewById(R.id.t2);
        txt3= (EditText)findViewById(R.id.t3);
        b1 = (Button)findViewById(R.id.b1);
        mbr = new Members();

        fref = FirebaseDatabase.getInstance();
        dref = fref.getInstance().getReference().child("User");

        Button b2 = findViewById(R.id.b2);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),View_Events.class);
                startActivity(i);
            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // int age = Integer.parseInt(txt2.getText().toString().trim());
                //mbr.setAge(age);
                mbr.setName(txt1.getText().toString().trim());
                mbr.setLocation(txt2.getText().toString().trim());
                mbr.setDate_time(txt3.getText().toString().trim());

                new GetUrlContentTask(getApplicationContext()).execute("http://192.168.43.175/add_event.php?name="+txt1.getText().toString()+"&location="+txt2.getText().toString());

                //dref.push().setValue(mbr);
                dref.child("member"+i).setValue(mbr);
                Toast.makeText(MainActivity.this,"data inserted",Toast.LENGTH_LONG).show();
                i=i+1;
            }
        });
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


            JSONObject jso = null;
            try {
                jso = new JSONObject(result);
                String status = jso.getString("status");
                Toast.makeText(this.context,"Status: "+status,Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
    }
}
