package com.example.kishan.bankingapp3.FrontEnd.services.login;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.test.AndroidTestCase;

import com.example.kishan.bankingapp3.FrontEnd.Domain.client.Client;
import com.example.kishan.bankingapp3.FrontEnd.Repositories.client.ClientRepository;
import com.example.kishan.bankingapp3.FrontEnd.Repositories.client.impl.ClientRepositoryImpl;
import com.example.kishan.bankingapp3.FrontEnd.Services.client.impl.ClientServiceImpl;
import com.example.kishan.bankingapp3.FrontEnd.Services.login.ValidateUser;
import com.example.kishan.bankingapp3.FrontEnd.Services.login.impl.ValidateUserImpl;


import junit.framework.Assert;

/**
 * Created by Ferin on 2016-08-31.
 */
public class ValidateUserTest extends AndroidTestCase {

    private ValidateUser validateUser;
    private boolean isBound;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent(this.getContext(), ValidateUserImpl.class);
        this.getContext().bindService(intent, connection, Context.BIND_AUTO_CREATE);

    }

    public ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ValidateUserImpl.RetrieveAccountInfoLocalBinder binder
                    = (ValidateUserImpl.RetrieveAccountInfoLocalBinder) service;
            validateUser = binder.getService();

            isBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            isBound = false;

        }
    };

    public void testName() throws Exception {
        ClientServiceImpl clientService = ClientServiceImpl.getInstance();
        Context context = getContext();
        ClientRepository clientRepository = new ClientRepositoryImpl(context);

        Client client = new Client.Builder()

                .name("1")
                .surname("1")
                .idNumber("1")
                .email("1")
                .build();

        clientService.addClient(this.mContext, client);

        boolean isValid = validateUser.isValidUser("1", "1");
        Assert.assertTrue(isValid);
    }
}
