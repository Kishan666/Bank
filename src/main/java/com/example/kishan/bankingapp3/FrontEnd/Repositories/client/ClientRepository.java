package com.example.kishan.bankingapp3.FrontEnd.Repositories.client;


import com.example.kishan.bankingapp3.FrontEnd.Domain.client.Client;
import com.example.kishan.bankingapp3.FrontEnd.Repositories.Repository;

/**
 * Created by Ferin on 2016-08-28.
 */
public interface ClientRepository extends Repository<Client, Long> {
    public boolean validateUser(String email, String idnumber);
}
