package com.example.kishan.bankingapp3.FrontEnd.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.kishan.bankingapp3.FrontEnd.activities.client.AddClientActivity;
import com.example.kishan.bankingapp3.FrontEnd.activities.client.Menu;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import com.example.kishan.bankingapp3.FrontEnd.R;

public class Login extends AppCompatActivity {

    boolean isValid = false;
    String email = "";
    String idNumber = "";
    com.example.kishan.bankingapp3.FrontEnd.Domain.Login.Login login[] = null;
    Intent intent;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intent = new Intent(this, Menu.class);


    }

    public void validateUser(View view) {

        HttpRequestTask getMovieRequests = new HttpRequestTask();
        getMovieRequests.execute();

    }

    public void register(View view) {
        Intent intent = new Intent(this, AddClientActivity.class);
        startActivity(intent);
    }


    private class HttpRequestTask extends AsyncTask<Void, Integer, String> {


        @Override
        protected void onPreExecute() {
            //textView.setText("Hello !!!");
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(String user) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if(login != null) {
                        if (login.length > 0) {
                            email = ((EditText) findViewById(R.id.txtEmailAddress)).getText().toString();
                            idNumber = ((EditText) findViewById(R.id.txtPassword)).getText().toString();

                            for (int i = 0; i < login.length && !isValid; i++) {
                                if (login[i].getUsername().equalsIgnoreCase(email) && login[i].getUser_password().equals(idNumber))
                                    isValid = true;
                            }

                            if (isValid) {
                                Toast.makeText(getBaseContext(), "Login successful", Toast.LENGTH_SHORT).show();
                                intent.putExtra("CLIENT_EMAIL", email);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getBaseContext(), "Invalid email and password combination", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else
                        Toast.makeText(getBaseContext(), "Invalid email and password combination", Toast.LENGTH_SHORT).show();
                }
            });

        }


        @Override
        protected String doInBackground(Void... params) {
            String uri = "http://148.100.5.6:8080/login-requests/";
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
            HttpEntity<com.example.kishan.bankingapp3.FrontEnd.Domain.Login.Login> requestHttpEntity = new HttpEntity<>(requestHeaders);
            login = restTemplate.exchange(uri, HttpMethod.GET, requestHttpEntity, com.example.kishan.bankingapp3.FrontEnd.Domain.Login.Login[].class).getBody();

            return null;
        }

    }

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
