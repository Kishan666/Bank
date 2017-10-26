package com.example.kishan.bankingapp3.FrontEnd.activities.client;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.example.kishan.bankingapp3.FrontEnd.Domain.Login.Login;
import com.example.kishan.bankingapp3.FrontEnd.Domain.client.Client;
import com.example.kishan.bankingapp3.FrontEnd.activities.account.AddAccount;
import com.example.kishan.bankingapp3.FrontEnd.activities.account.BalanceEnquiryActivity;
import com.example.kishan.bankingapp3.FrontEnd.activities.account.WithdrawActivity;
import com.example.kishan.bankingapp3.R;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class Menu extends AppCompatActivity {

    Client client[];
    String client_email;
    Long client_id;
    boolean isValid = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Bundle extras = getIntent().getExtras();
        client_email = extras.getString("CLIENT_EMAIL");

        /* allows me to get clientID */
        HttpRequestTask httpRequestTask = new HttpRequestTask();
        httpRequestTask.execute();
    }

    public void addAccount(View view)
    {
        Intent intent = new Intent(this, AddAccount.class);
        intent.putExtra("CLIENT_ID", client_id);
        startActivity(intent);
    }
    public void balanceEnq(View view)
    {
        Intent intent = new Intent(this, BalanceEnquiryActivity.class);
        intent.putExtra("CLIENT_ID", client_id);
        startActivity(intent);
    }

    public void withdraw(View view){

        Intent intent = new Intent(this, WithdrawActivity.class);
        intent.putExtra("CLIENT_ID", client_id);
        startActivity(intent);
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPostExecute(String user) {
            runOnUiThread(new Runnable() {
                public void run() {
                    for(int i = 0; i < client.length && !isValid; i++)
                    {
                        if(client[i].getEmail().equalsIgnoreCase(client_email)) {
                            isValid = true;
                            client_id = client[i].getId();
                        }
                    }

                }
            });

        }


        @Override
        protected String doInBackground(Void... params) {
            String uri = "http://148.100.5.6:8080/client-requests/";
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
            HttpEntity<Login> requestHttpEntity = new HttpEntity<>(requestHeaders);
            client = restTemplate.exchange(uri, HttpMethod.GET, requestHttpEntity, Client[].class).getBody();

            return null;
        }


    }


}
