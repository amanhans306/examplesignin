package com.miet.examplesignin;

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
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.R.attr.data;

public class Signup extends AppCompatActivity {
    Button signup;
    EditText name,username,password,confirmpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name=(EditText)findViewById(R.id.name);
        username=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        confirmpassword=(EditText)findViewById(R.id.confirmpassword);
        signup =(Button)findViewById(R.id.button);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            SignupTask signupTask= new SignupTask(name.getText().toString(),username.getText().toString(),password.getText().toString());
             signupTask.execute();
            }
        });
    }
    class SignupTask extends AsyncTask<Void,Void,String>{

        String namestr, usernamestr, passwordstr;
        public SignupTask(String s, String s1, String s2) {
            namestr = s;
            usernamestr = s1;
            passwordstr = s2;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://www.errajatsharma.com/2017/registerUser.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream()));
                String datatosend = String.format("name=%s&username=%s&password=%s", namestr, usernamestr, passwordstr);

                Log.i("data", datatosend);

                bufferedWriter.write(datatosend);
                bufferedWriter.flush();
                bufferedWriter.close();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                String row;
                StringBuilder stringBuilder = new StringBuilder();

                while((row = bufferedReader.readLine())!= null)
                {
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
            Log.i("data",s);

            Toast.makeText(Signup.this, s , Toast.LENGTH_LONG).show();
        }
    }
}
