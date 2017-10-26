package com.example.kishan.bankingapp3.FrontEnd.Services.client;

import android.content.Context;

import com.example.kishan.bankingapp3.FrontEnd.Domain.client.Client;


public interface ClientService {
    void addClient(Context context, Client client);
    void updateClient(Context context, Client client);
    void deleteClient(Context context, Client client);
}
