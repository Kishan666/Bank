package com.example.kishan.bankingapp3.FrontEnd.Factories.client;



import com.example.kishan.bankingapp3.FrontEnd.Domain.client.Client;

import java.util.Map;

/**
 * Created by Kishan on 2017-10-26.
 */
public interface ClientFactory {

    public Client createClient(Map<String, String> clientData);
}
