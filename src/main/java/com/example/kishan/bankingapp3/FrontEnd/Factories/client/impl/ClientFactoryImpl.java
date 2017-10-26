package com.example.kishan.bankingapp3.FrontEnd.Factories.client.impl;



import com.example.kishan.bankingapp3.FrontEnd.Domain.client.Client;
import com.example.kishan.bankingapp3.FrontEnd.Factories.client.ClientFactory;

import java.util.Map;

/**
 * Created by Ferin on 2016-08-28.
 */
public class ClientFactoryImpl implements ClientFactory {

    public Client createClient(Map<String, String> clientData) {
        return new Client.Builder()
                .idNumber(clientData.get("idNumber"))
                .name(clientData.get("name"))
                .surname(clientData.get("surname"))
                .email(clientData.get("email"))
                .id(Long.parseLong(clientData.get("id")))
                .build();
    }
}
