package com.example.kishan.bankingapp3.FrontEnd.activities.client;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.kishan.bankingapp3.FrontEnd.Domain.Login.Login;
import com.example.kishan.bankingapp3.FrontEnd.Domain.client.Client;
import com.example.kishan.bankingapp3.FrontEnd.Services.client.impl.ClientServiceImpl;
import com.example.kishan.bankingapp3.R;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class AddClientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);
    }

    public boolean isFilledFields()
    {
        String name = ((EditText)findViewById(R.id.txtName)).getText().toString();
        String surname = ((EditText)findViewById(R.id.txtSurname)).getText().toString();
        String email = ((EditText)findViewById(R.id.txtEmail)).getText().toString();
        String idNumber = ((EditText)findViewById(R.id.txtIDNumber)).getText().toString();
        String password = ((EditText)findViewById(R.id.txtPassword)).getText().toString();

        return (name != "" && surname != "" && email != "" && idNumber != "" && password != "");
    }

    public void save(View v) {

        if(isFilledFields()) {
            String name = ((EditText) findViewById(R.id.txtName)).getText().toString();
            String surname = ((EditText) findViewById(R.id.txtSurname)).getText().toString();
            String email = ((EditText) findViewById(R.id.txtEmail)).getText().toString();
            String idNumber = ((EditText) findViewById(R.id.txtIDNumber)).getText().toString();
            String password = ((EditText) findViewById(R.id.txtPassword)).getText().toString();
            ClientServiceImpl clientService = ClientServiceImpl.getInstance();

            Client client = new Client.Builder()
                    .name(name)
                    .surname(surname)
                    .email(email)

                    .idNumber(idNumber)
                    .build();

            //clientService.addClient(getBaseContext(), client);

            ClientRegisterTask clientRegisterTask = new ClientRegisterTask(client);
            clientRegisterTask.execute();

            UserRegisterTask userRegisterTask = new UserRegisterTask(email, password);
            userRegisterTask.execute();

        }
        else{
            Toast.makeText(getBaseContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }



    public class ClientRegisterTask extends AsyncTask<Void, Void, Client> {

        private final Client client;

        ClientRegisterTask(Client client) {
          this.client = client;
        }

        @Override
        protected Client doInBackground(Void... params) {

            final String uri = "http://148.100.5.6:8080/client-request/";

            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
            HttpEntity<Client> requestEntity = new HttpEntity<>(client, requestHeaders);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
            ResponseEntity<Client> result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, Client.class);
            return result.getBody();
        }

        @Override
        protected void onPostExecute(final Client success) {
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getBaseContext(), "User successfully added!", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }


    public class UserRegisterTask extends AsyncTask<Void, Void, Login> {

        private final String mEmail;
        private final String mPassword;


        UserRegisterTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected com.example.kishan.bankingapp3.FrontEnd.Domain.Login.Login doInBackground(Void... params) {

            final String uri = "http://148.100.5.6:8080/login-request/";

            com.example.kishan.bankingapp3.FrontEnd.Domain.Login.Login user = new com.example.kishan.bankingapp3.FrontEnd.Domain.Login.Login.Builder()

                    .username(mEmail)
                    .password(mPassword)
                    .build();


            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
            HttpEntity<Login> requestEntity = new HttpEntity<>(user, requestHeaders);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
            ResponseEntity<Login> result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, com.example.kishan.bankingapp3.FrontEnd.Domain.Login.Login.class);
            return result.getBody();
        }

        @Override
        protected void onPostExecute(final com.example.kishan.bankingapp3.FrontEnd.Domain.Login.Login success) {
            runOnUiThread(new Runnable() {
                public void run() {
                    //Toast.makeText(AddClientActivity.this, "User successfully added!", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

}
