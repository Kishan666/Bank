package com.example.kishan.bankingapp3.FrontEnd.repository.client;

import android.content.Context;
import android.test.AndroidTestCase;


import com.example.kishan.bankingapp3.FrontEnd.Domain.client.Client;
import com.example.kishan.bankingapp3.FrontEnd.Repositories.client.ClientRepository;
import com.example.kishan.bankingapp3.FrontEnd.Repositories.client.impl.ClientRepositoryImpl;

import junit.framework.Assert;

import java.util.Set;


public class ClientRepositoryTest extends AndroidTestCase{
    private Long id;

    public void testCreateReadUpdateDelete() throws Exception {
        Context context = getContext();
        ClientRepository clientRepository = new ClientRepositoryImpl(context);

        // CREATE
        Client client = new Client.Builder()
                .name("kishan")
                .surname("parsotam")
                .email("123@gmail")
                .idNumber("456")

                .build();

        Client insertedEntity = clientRepository.save(client);
        id = insertedEntity.getId();
        Assert.assertNotNull(insertedEntity);

        // READ ALL
        Set<Client> businessSet = clientRepository.findAll();
        Assert.assertTrue(businessSet.size() > 0);


        // READ ENTITY
        Client entity = clientRepository.findById(id);
        Assert.assertNotNull(entity);

        // UPDATE ENTITY
        Client updateEntity = new Client.Builder()
                .copy(entity)
                .name("kishan")
                .build();
        clientRepository.update(updateEntity);
        Client newEntity = clientRepository.findById(id);
        Assert.assertEquals("kishan", newEntity.getName());

        // DELETE ENTITY
        clientRepository.delete(updateEntity);
        Client deletedEntity = clientRepository.findById(id);
        Assert.assertNull(deletedEntity);


        // DELETE ALL
        clientRepository.deleteAll();
        Set<Client> deletedUsers = clientRepository.findAll();
        Assert.assertTrue(deletedUsers.size() == 0);


    }
}
