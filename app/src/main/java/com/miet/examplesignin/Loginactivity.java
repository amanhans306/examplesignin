package com.miet.examplesignin;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import static android.R.attr.data;

public class Loginactivity extends AppCompatActivity {

    EditText username,password;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);

        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginTask logintask =new LoginTask(username.getText().toString(),password.getText().toString());
                logintask.execute();
            }
        });

    }
    class LoginTask extends AsyncTask<Void,Void,String> {

        String usernamestr, passwordstr;
        public LoginTask( String s1, String s2) {
            usernamestr = s1;
            passwordstr = s2;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://www.errajatsharma.com/2017/login.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream()));
                String datatosend = String.format("username=%s&password=%s", usernamestr, passwordstr);

                Log.i("data", datatosend);

                bufferedWriter.write(datatosend);
                bufferedWriter.flush();
                bufferedWriter.close();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                String row;
                StringBuilder stringBuilder = new StringBuilder();

                while ((row = bufferedReader.readLine()) != null) {
                    stringBuilder.append(row);
                }
                Log.i("data1",stringBuilder.toString());
                return stringBuilder.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Toast.makeText(Loginactivity.this, s , Toast.LENGTH_SHORT).show();
            if (s.contains("success")){
                Intent intent =new Intent(Loginactivity.this,Main2Activity.class);
                startActivity(intent);
            }else {
                Toast.makeText(Loginactivity.this, "Account Doesn't Exist", Toast.LENGTH_LONG).show();
            }
        }
    }

}
