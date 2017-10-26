package com.example.kishan.bankingapp3.FrontEnd.activities.account;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.kishan.bankingapp3.FrontEnd.Domain.account.Account;
import com.example.kishan.bankingapp3.FrontEnd.Domain.client.Client;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import com.example.kishan.bankingapp3.FrontEnd.R;
public class AddAccount extends AppCompatActivity {

    Long clinet_id;
    Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);


        Bundle extras = getIntent().getExtras();
        clinet_id = extras.getLong("CLIENT_ID");

         client = new Client.Builder()
                .id(clinet_id)
                .build();


    }

    public void back(View view){
        Intent intent = new Intent(this, com.example.kishan.bankingapp3.FrontEnd.Domain.Login.Login.class);
        startActivity(intent);
    }

    public boolean isFilledFields()
    {
        String accountNumber = ((EditText) findViewById(R.id.txtAccountNumber)).getText().toString();
        String balance = ((EditText) findViewById(R.id.txtBalance)).getText().toString();


        return (accountNumber != "" && balance != "" );
    }

    public void saveAccount(View view){
        if(isFilledFields()) {
            if (isInternetConnected(getBaseContext())) {
                String accountNumber = ((EditText) findViewById(R.id.txtAccountNumber)).getText().toString();
                String balance = ((EditText) findViewById(R.id.txtBalance)).getText().toString();

                Double limit = Double.parseDouble(balance) / 2;

                Account account = new Account.Builder()
                        .accountNumber(accountNumber)
                        .accountType("generic")
                        .balance(balance)
                        .limit(limit.toString())

                        .client(client)
                        .build();

                AccountRegisterTask accountRegisterTask = new AccountRegisterTask(account);
                accountRegisterTask.execute();
            } else {
                Toast.makeText(getBaseContext(), "unable to connect to the internet", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getBaseContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
        }
    }


    public class AccountRegisterTask extends AsyncTask<Void, Void, Account> {

        private final Account account;

        AccountRegisterTask(Account account) {
            this.account = account;
        }

        @Override
        protected Account doInBackground(Void... params) {

            final String uri = "http://148.100.5.6:8080/account-request/";

            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
            HttpEntity<Account> requestEntity = new HttpEntity<>(account, requestHeaders);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
            ResponseEntity<Account> result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, Account.class);
            return result.getBody();
        }

        @Override
        protected void onPostExecute(final Account success) {
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getBaseContext(), "Account successfully added!", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }


    public static boolean isInternetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
