package com.example.kishan.bankingapp3.FrontEnd.activities.account;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.example.kishan.bankingapp3.FrontEnd.Domain.Login.Login;
import com.example.kishan.bankingapp3.FrontEnd.Domain.account.Account;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import com.example.kishan.bankingapp3.FrontEnd.R;

public class BalanceEnquiryActivity extends AppCompatActivity {

    Long client_id;
    int cnt = 0;
    Account account[] = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_enquiry);

        Bundle extras = getIntent().getExtras();
        client_id = extras.getLong("CLIENT_ID");

        HttpRequestTask httpRequestTask = new HttpRequestTask();
        httpRequestTask.execute();

    }

    public void backBal(View view)
    {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPostExecute(String user) {
            runOnUiThread(new Runnable() {
                public void run() {
                    TableLayout table = (TableLayout) findViewById(R.id.tbl);
                    TableRow row = new TableRow(getBaseContext());
                    //row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                    TextView t = new TextView(getBaseContext());
                    t.setText("Account Number      ");
                    t.setTextColor(Color.BLACK);
                    t.setTextSize(14);
                    row.addView(t);

                    TextView idNum = new TextView(getBaseContext());
                    idNum.setText("Balance        ");
                    idNum.setTextColor(Color.BLACK);
                    idNum.setTextSize(14);
                    row.addView(idNum);

                 /*   TextView email = new TextView(getBaseContext());
                    email.setText("Account Type         ");
                    email.setTextColor(Color.BLACK);
                    email.setTextSize(14);
                    row.addView(email);*/

                    table.addView(row, 0);

                    if(account != null) {
                        for (int i = 0; i < account.length; i++) {
                            //  if(account[i].getClient().getId().equals(client_id)) {

                            TableRow row2 = new TableRow(getBaseContext());
                            //row2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                            TextView t2 = new TextView(getBaseContext());
                            t2.setText(account[i].getAccountNumber() + "         ");
                            row2.addView(t2);

                            TextView idNum2 = new TextView(getBaseContext());
                            idNum2.setText(account[i].getBalance() + "         ");
                            row2.addView(idNum2);

                        /*    TextView email2 = new TextView(getBaseContext());
                            email2.setText(account[i].getAccountType() + "         ");
                            row2.addView(email2);
*/
                            table.addView(row2, (i + 1));

                            cnt++;
                            //  }
                        }
                    }

                    else
                    {
                        Toast.makeText(getBaseContext(), "You have no accounts!", Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }


        @Override
        protected String doInBackground(Void... params) {
            String uri = "http://148.100.5.6:8080/account-requests/";
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
            HttpEntity<Login> requestHttpEntity = new HttpEntity<>(requestHeaders);
            account = restTemplate.exchange(uri, HttpMethod.GET, requestHttpEntity, Account[].class).getBody();

            return null;
        }


    }
}
