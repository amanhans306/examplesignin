package com.miet.examplesignin;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class spiderman extends AppCompatActivity {


    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spiderman);

        button =(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Studatadownload studatadownload =new Studatadownload();
                studatadownload.execute();
            }
        });
    }
    class Studatadownload extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {

            URL url = null;
            try {
                url = new URL("https://hansaman27.000webhostapp.com/getstudata.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                String row;
                StringBuilder stringBuilder = new StringBuilder();
                while ((row = bufferedReader.readLine()) != null) ;
                {
                    stringBuilder.append(row);
                }
                return stringBuilder.toString().toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ArrayList<studata> studataArrayList= new ArrayList<>();
            Intent intent =new Intent(spiderman.this,studata.class);
            intent.putExtra("studata",studataArrayList);
            startActivity(intent);


        }
    }
}
