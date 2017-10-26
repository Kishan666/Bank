package com.example.kishan.bankingapp3.FrontEnd.services;

import android.content.Context;
import android.test.AndroidTestCase;


import com.example.kishan.bankingapp3.FrontEnd.Domain.client.Client;
import com.example.kishan.bankingapp3.FrontEnd.Repositories.client.ClientRepository;
import com.example.kishan.bankingapp3.FrontEnd.Repositories.client.impl.ClientRepositoryImpl;
import com.example.kishan.bankingapp3.FrontEnd.Services.client.impl.ClientServiceImpl;

import junit.framework.Assert;

import java.util.Set;


public class ClientServiceTest extends AndroidTestCase {

    public void testInsert() throws Exception {

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

        Thread.sleep(5000);
        // READ ALL
        Set<Client> businessSet1 = clientRepository.findAll();
        Assert.assertTrue(businessSet1.size() > 0);
    }
}
