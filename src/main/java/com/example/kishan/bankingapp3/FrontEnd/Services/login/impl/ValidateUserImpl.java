package com.example.kishan.bankingapp3.FrontEnd.Services.login.impl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.kishan.bankingapp3.FrontEnd.Repositories.client.ClientRepository;
import com.example.kishan.bankingapp3.FrontEnd.Repositories.client.impl.ClientRepositoryImpl;
import com.example.kishan.bankingapp3.FrontEnd.Services.login.ValidateUser;


public class ValidateUserImpl extends Service implements ValidateUser {

    private IBinder localBinder = new RetrieveAccountInfoLocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return localBinder;
    }


    public class RetrieveAccountInfoLocalBinder extends Binder {
        public ValidateUserImpl getService()
        {
            return  ValidateUserImpl.this;
        }
    }
    public ValidateUserImpl() {
    }

    @Override
    public boolean isValidUser(String email, String idNumber) {
        ClientRepository clientRepository = new ClientRepositoryImpl(getBaseContext());
        return clientRepository.validateUser(email, idNumber);
    }


}
