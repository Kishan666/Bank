package com.example.kishan.bankingapp3.FrontEnd.Services.client.impl;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.example.kishan.bankingapp3.FrontEnd.Domain.client.Client;
import com.example.kishan.bankingapp3.FrontEnd.Repositories.client.ClientRepository;
import com.example.kishan.bankingapp3.FrontEnd.Repositories.client.impl.ClientRepositoryImpl;
import com.example.kishan.bankingapp3.FrontEnd.Services.client.ClientService;


public class ClientServiceImpl extends IntentService implements ClientService {

    private static final String ACTION_ADD = "com.example.kishan.bankingapp3.FrontEnd.Services.client.impl.action.ADD";
    private static final String ACTION_UPDATE = "com.example.kishan.bankingapp3.FrontEnd.Services.client.impl.action.UPDATE";


    private static final String EXTRA_ADD = "com.example.kishan.bankingapp3.FrontEnd.Services.client.impl.extra.ADD";
    private static final String EXTRA_UPDATE = "com.example.kishan.bankingapp3.FrontEnd.Services.client.impl.extra.UPDATE";


    private static ClientServiceImpl service = null;

    public static ClientServiceImpl getInstance() {
        if (service == null)
            service = new ClientServiceImpl();
        return service;
    }


    public ClientServiceImpl() {
        super("ClientServiceImpl");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_ADD.equals(action)) {
                final Client client = (Client)intent.getSerializableExtra(EXTRA_ADD);
                saveClient(client);
            } else if (ACTION_UPDATE.equals(action)) {
                final Client client = (Client)intent.getSerializableExtra(EXTRA_UPDATE);
                updateClient(client);
            }
        }
    }


    private void saveClient(Client client) {
        ClientRepository clientRepository = new ClientRepositoryImpl(getBaseContext());
        clientRepository.save(client);
    }


    private void updateClient(Client client) {
       ClientRepository clientRepository = new ClientRepositoryImpl(getBaseContext());
       clientRepository.update(client);
    }


    @Override
    public void addClient(Context context, Client client) {
        Intent intent = new Intent(context, ClientServiceImpl.class);
        intent.setAction(ACTION_ADD);
        intent.putExtra(EXTRA_ADD, client);
        context.startService(intent);
    }

    @Override
    public void updateClient(Context context, Client client) {
        Intent intent = new Intent(context, ClientServiceImpl.class);
        intent.setAction(ACTION_UPDATE);
        intent.putExtra(EXTRA_UPDATE, client);
        context.startService(intent);
    }

    @Override
    public void deleteClient(Context context, Client client) {

    }
}
